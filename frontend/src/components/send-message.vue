<template>
  <el-container>
    <el-header>
      <el-row class="row">
        <el-col :span="6"
          ><el-select v-model="key" placeholder="검색키">
            <el-option value="receiveFriend" label="받은사람" />
            <el-option value="message" label="내용" /> </el-select
        ></el-col>
        <el-col :span="12">
          <el-input v-model="search" />
        </el-col>
        <el-col :span="6">
          <el-button @click="onSearch">검색</el-button>
        </el-col>
      </el-row>
    </el-header>
    <el-row class="row select">
      <el-col :span="16"></el-col>
      <el-col :span="6"
        ><el-button style="margin-right: 30px" @click="onDelete"
          >삭제</el-button
        ></el-col
      >
    </el-row>
    <table class="table">
      <thead>
        <tr>
          <th>
            <label class="form-checkbox"
              ><input
                type="checkbox"
                v-model="state.selectAll"
                @click="onSelect"
            /></label>
          </th>
          <th>받은사람</th>
          <th>내용</th>
          <th>날짜</th>
        </tr>
      </thead>
      <tbody>
        <tr
          class="cell"
          v-for="message in state.messageList"
          :key="message"
          @click="onMsg(message)"
        >
          <td>
            <label class="form-checkbox">
              <input
                type="checkbox"
                :value="message.id"
                v-model="state.selected"
                @click.stop
              />
              <message class="form-icon"></message>
            </label>
          </td>
          <td class="tableName">{{ message.receiver.nickname }}</td>
          <td class="tableMsg">{{ message.content }}</td>
          <td>{{ message.sendTime }}</td>
        </tr>
      </tbody>
    </table>
    <el-dialog v-model="state.messageDialog" @close="handleClose">
      <el-header style="text-align: left; padding-top: 10px">
        <span class="to"> TO. </span>
        <span class="toFriend"> {{ toFriend }}</span>
        <img class="friendIcon" src="@/assets/friends.png" />
        <span class="toDate"> {{ toDate }} </span>
      </el-header>
      <el-input v-model="message" type="textarea" rows="10" readonly />
      <div style="margin-top: 20px">
        <el-button @click="handleClose">닫기</el-button>
      </div>
    </el-dialog>
    <div style="margin: 0 auto; margin-top: 20px">
      <el-pagination
        background
        layout="prev, pager, next"
        @current-change="handleCurrentChange"
        :page-size="8"
        :total="state.msgcnt"
      />
    </div>
  </el-container>
</template>

<script>
import { useStore } from "vuex";
import { ref, reactive, computed, onMounted } from "vue";

export default {
  setup() {
    const key = ref("");
    const search = ref("");
    const store = useStore();
    const state = reactive({
      memberinfo: computed(() => store.getters["accounts/getMember"]),
      searchFlag: false,
      messageList: [],
      msgcnt: computed(() => state.messageList.length),
      messageId: "",
      messageDialog: false,
      selected: [],
      selectAll: computed(
        () => state.selected.length === state.messageList.length
      ),
    });

    onMounted(() => {
      store
        .dispatch("messages/getSendList", {
          email: state.memberinfo.email,
          page: 0,
          key: "",
          word: "",
          size: 10,
        })
        .then(function (result) {
          console.log("result", result);
          state.messageList = result.data.body.messages;
          console.log("messageList", state.messageList);
        });
    });

    const onSearch = function () {
      if (key.value == "") {
        alert("검색키를 선택하세요");
      } else if (search.value == "") {
        alert("검색어를 입력하세요");
      } else {
        console.log("search", key.value + " " + search.value);
      }
    };

    const onSelect = function () {
      console.log(state.selectAll.value);
      if (!state.selectAll.value) {
        state.selected.value = [];
        for (let index in state.messageList) {
          state.selected.value.push(state.messageList[index].id);
        }
      } else {
        state.selected.value = [];
      }
    };

    const onDelete = function () {
      console.log("selected", state.selected);
      store
        .dispatch("messages/deleteSendList", {
          list: state.selected,
        })
        .then(function (result) {
          console.log("result", result);

          store
            .dispatch("messages/getSendList", {
              email: state.memberinfo.email,
              page: 0,
              key: key.value,
              word: search.value,
              size: 10,
            })
            .then(function (result) {
              console.log("result", result);
              state.messageList = result.data.body.messages;
              console.log("delete-messageList", state.messageList);
            });
        });
    };

    const onMsg = function (i) {
      console.log(i);
      state.messageId = i.id;
      state.messageDialog.value = true;
    };

    const handleClose = function () {
      state.messageDialog.value = false;
    };

    const handleCurrentChange = function (val) {
      console.log("page", val);
    };

    return {
      key,
      search,
      state,
      onSearch,
      onSelect,
      onDelete,
      onMsg,
      handleClose,
      handleCurrentChange,
    };
  },
};
</script>

<style>
.el-header {
  background-color: #9dd098;
}
.row {
  flex-direction: row;
  padding-top: 15px;
}
.select {
  background-color: #ececec;
  padding-bottom: 15px;
  justify-content: space-between;
}
table {
  border-top: 1px solid #ebeef5;
  border-collapse: collapse;
  table-layout: fixed;
}
th,
td {
  border-bottom: 1px solid #ebeef5;
  padding: 10px;
  text-align: left;
}
.tableName {
  max-width: 100px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.tableMsg {
  max-width: 250px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  text-align: left;
}
.cell:hover {
  background-color: #ebeef5;
}
.to {
  font-size: 30px;
  color: #cc3366;
  font-weight: bolder;
  text-shadow: 2px 4px 2px gray;
}
.toFriend {
  font-size: 20px;
  font-weight: bold;
}
.friendIcon {
  width: 25px;
  height: 25px;
  margin-left: 15px;
}
.toDate {
  float: right;
  font-size: 15px;
  padding-top: 10px;
}
.form-checkbox {
  zoom: 1.5;
}
</style>
