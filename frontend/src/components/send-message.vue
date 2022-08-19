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

    <div style="height: 600px">
      <table
        class="table"
        v-if="state.messageList.length != 0"
        style="width: 100%"
      >
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

  <el-dialog top="250px" v-model="state.alertDialog" width="30%" center>
    <el-row style="top: 12px; font-size: 16.5px">{{ state.alertMsg }}</el-row>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="danger" round @click="state.alertDialog = false"
          >확인</el-button
        >
      </span>
    </template>
  </el-dialog>
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
      alertDialog: false,
      alertMsg: "",
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
          state.messageList = result.data.body.messages;
          state.msgcnt = result.data.body.pagingResponse.totalcount;
        });
    });

    const onSearch = function () {
      if (key.value == "") {
        alertOpen("검색키를 선택하세요");
      } else if (search.value == "") {
        alertOpen("검색어를 입력하세요");
      } else {
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
            state.messageList = result.data.body.messages;
            state.msgcnt = result.data.body.pagingResponse.totalcount;
          });
      }
    };

    const onSelect = function () {
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
      store
        .dispatch("messages/deleteSendList", {
          list: state.selected,
        })
        .then(function () {
          store
            .dispatch("messages/getSendList", {
              email: state.memberinfo.email,
              page: 0,
              key: key.value,
              word: search.value,
              size: 7,
            })
            .then(function (result) {
              state.messageList = result.data.body.messages;
              state.msgcnt = result.data.body.pagingResponse.totalcount;
              state.currentPage = 1;
            });
        })
        .catch(function (error) {
          alertOpen(error);
        });
    };

    const onMsg = async function (i) {
      state.messageId = i.id;
      await store
        .dispatch("messages/getMessage", { id: i.id, type: "from" })
        .then(function (result) {
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
              state.messageList = result.data.body.messages;
              state.msgcnt = result.data.body.pagingResponse.totalcount;
              state.friendFlag = state.message.fromfriendflag;
            });
        });
      state.messageDialog = true;
    };

    const handleClose = function () {
      state.messageDialog = false;
    };

    const handleCurrentChange = function (val) {
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
          state.messageList = result.data.body.messages;
          state.msgcnt = result.data.body.pagingResponse.totalcount;
        });
    };

    const alertOpen = function (msg) {
      state.alertMsg = msg;
      state.alertDialog = true;
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
      alertOpen,
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
