<template>
  <el-container style="height: 620px">
    <el-header>
      <el-row class="row">
        <el-col :span="6"
          ><el-select v-model="key" placeholder="검색키">
            <el-option value="nickname" label="받은사람" />
            <el-option value="content" label="내용" /> </el-select
        ></el-col>
        <el-col :span="12">
          <el-input v-model="search" placeholder="검색어를 입력하세요" />
        </el-col>
        <el-col :span="6">
          <button class="mainButton" @click="onSearch" size="large">
            검색
          </button>
        </el-col>
      </el-row>
    </el-header>
    <el-row class="row select">
      <el-col :span="16"></el-col>
      <el-col :span="6"
        ><button
          class="mainButton"
          style="margin-right: 30px"
          @click="onDelete"
          size="large"
        >
          삭제
        </button></el-col
      >
    </el-row>

    <div style="width: 100%; height: 500px">
      <table class="table" v-if="state.messageList.length != 0">
        <colgroup>
          <col width="10%" />
          <col width="25%" />
          <col width="40%" />
          <col width="25%" />
        </colgroup>

        <thead>
          <tr>
            <th>
              <label class="form-checkbox"
                ><input type="checkbox" v-model="selectAll" @click="onSelect"
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
            <td>
              {{
                message.sendTime.substring(0, 10) +
                " " +
                message.sendTime.substring(11, 19)
              }}
            </td>
          </tr>
        </tbody>
      </table>
      <el-row class="exceptionMsg" v-else-if="state.searchFlag"
        >검색한 쪽지가 없습니다!</el-row
      >
      <el-row class="exceptionMsg" v-else>친구에게 쪽지를 보내보세요!</el-row>
    </div>

    <div style="margin: 0 auto; margin-top: 20px">
      <el-pagination
        background
        layout="prev, pager, next"
        @current-change="handleCurrentChange"
        :current-page="state.currentPage"
        :page-size="7"
        :total="state.msgcnt"
      />
    </div>

    <el-dialog v-model="state.messageDialog" @close="handleClose" draggable>
      <el-header style="text-align: left; padding-top: 10px">
        <span class="to"> To. </span>
        <span class="toFriend"> {{ state.message.receiver.nickname }}</span>
        <img class="friendIcon" src="@/assets/friends.png" />
        <span class="toDate">
          {{
            state.message.sendTime.substring(0, 10) +
            " " +
            state.message.sendTime.substring(11, 19)
          }}
        </span>
      </el-header>
      <el-input
        v-model="state.message.content"
        type="textarea"
        rows="10"
        readonly
      />
      <div style="margin-top: 20px">
        <button class="mainButton messageCancel" @click="handleClose">
          닫기
        </button>
      </div>
    </el-dialog>
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
    const selectAll = computed(
      () => state.selected.length === state.messageList.length
    );
    const state = reactive({
      memberinfo: computed(() => store.getters["accounts/getMember"]),
      searchFlag: false,
      messageList: [],
      msgcnt: 0,
      message: {},
      friendFlag: true,
      currentPage: 1,
      messageId: "",
      messageDialog: false,
      selected: [],
    });

    onMounted(() => {
      store
        .dispatch("messages/getSendList", {
          email: state.memberinfo.email,
          page: 0,
          key: "",
          word: "",
          size: 7,
        })
        .then(function (result) {
          console.log("result", result);
          state.messageList = result.data.body.messages;
          state.msgcnt = result.data.body.pagingResponse.totalcount;
          console.log("messageList", state.messageList + " " + state.msgcnt);
        });
    });

    const onSearch = function () {
      if (key.value == "") {
        alert("검색키를 선택하세요");
      } else if (search.value == "") {
        alert("검색어를 입력하세요");
      } else {
        console.log("search", key.value + " " + search.value);
        state.searchFlag = true;
        store
          .dispatch("messages/getSendList", {
            email: state.memberinfo.email,
            page: 0,
            key: key.value,
            word: search.value,
            size: 7,
          })
          .then(function (result) {
            console.log("search-result", result);
            state.messageList = result.data.body.messages;
            state.msgcnt = result.data.body.pagingResponse.totalcount;
            console.log("search-messageList", state.messageList);
          });
      }
    };

    const onSelect = function () {
      console.log(selectAll.value);
      if (!selectAll.value) {
        state.selected = [];
        for (let index in state.messageList) {
          state.selected.push(state.messageList[index].id);
        }
      } else {
        state.selected = [];
      }
    };

    const onDelete = function () {
      console.log("delete", state.selected);
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
              size: 7,
            })
            .then(function (result) {
              console.log("result", result);
              state.messageList = result.data.body.messages;
              state.msgcnt = result.data.body.pagingResponse.totalcount;
              state.currentPage = 1;
              console.log(
                "delete-messageList",
                state.messageList + " " + state.msgcnt
              );
            });
        })
        .catch(function (error) {
          alert(error);
        });
    };

    const onMsg = async function (i) {
      console.log(i);
      state.messageId = i.id;
      await store
        .dispatch("messages/getMessage", { id: i.id, type: "from" })
        .then(function (result) {
          console.log("result", result);
          state.message = result.data.body;
          store
            .dispatch("messages/getSendList", {
              email: state.memberinfo.email,
              page: state.currentPage - 1,
              key: key.value,
              word: search.value,
              size: 7,
            })
            .then(function (result) {
              console.log("search-result", result);
              state.messageList = result.data.body.messages;
              state.msgcnt = result.data.body.pagingResponse.totalcount;
              state.friendFlag = state.message.fromfriendflag;
              console.log("search-messageList", state.messageList);
            });
        });
      state.messageDialog = true;
    };

    const handleClose = function () {
      state.messageDialog = false;
    };

    const handleCurrentChange = function (val) {
      console.log("page", val);
      state.currentPage = val;
      store
        .dispatch("messages/getSendList", {
          email: state.memberinfo.email,
          page: val - 1,
          key: key.value,
          word: search.value,
          size: 7,
        })
        .then(function (result) {
          console.log("result", result);
          state.messageList = result.data.body.messages;
          state.msgcnt = result.data.body.pagingResponse.totalcount;
          console.log("messageList", state.messageList + " " + state.msgcnt);
        });
    };

    return {
      key,
      search,
      selectAll,
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

<style scoped>
.el-header {
  background-color: rgb(255, 189, 207);
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
  background-color: #faebee;
}
input {
  accent-color: pink;
}
.to {
  font-size: 30px;
  color: #cc3366;
  font-weight: bolder;
}
.toFriend {
  font-size: 20px;
  font-weight: bold;
}
.friendIcon {
  width: 25px;
  height: 25px;
  margin-left: 15px;
  vertical-align: middle;
  margin-bottom: 10px;
}
.toDate {
  float: right;
  font-size: 15px;
  padding-top: 10px;
}
.form-checkbox {
  zoom: 1.5;
}
.el-select-dropdown__item.selected {
  color: palevioletred;
}
.exceptionMsg {
  padding: 30px;
  border-top: solid 2px rgb(255, 189, 207);
  border-bottom: solid 2px rgb(255, 189, 207);
  font-size: large;
}
.el-pagination.is-background .el-pager li:not(.is-disabled).is-active {
  background-color: rgb(255, 189, 207);
}
.messageCancel {
  margin-left: 18px;
  border-radius: 20px;
}
</style>
