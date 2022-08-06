<template>
  <el-container>
    <el-header>
      <el-row class="row">
        <el-col :span="6"
          ><el-select v-model="key" placeholder="검색키">
            <el-option value="nickname" label="보낸사람" />
            <el-option value="content" label="내용" /> </el-select
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
        ><el-button @click="onRead">읽음</el-button>
        <el-button style="margin-right: 30px" @click="onDelete"
          >삭제</el-button
        ></el-col
      >
    </el-row>
    <table class="table">
      <thead v-if="state.messageList.length != 0">
        <tr>
          <th>
            <label class="form-checkbox"
              ><input type="checkbox" v-model="selectAll" @click="onSelect"
            /></label>
          </th>
          <th>보낸사람</th>
          <th>내용</th>
          <th>날짜</th>
        </tr>
      </thead>
      <thead v-else-if="state.searchFlag">
        <tr>
          검색한 쪽지가 없습니다!
        </tr>
      </thead>
      <thead v-else>
        <tr>
          받은 쪽지가 없습니다!
        </tr>
      </thead>
      <tbody>
        <tr
          class="cell"
          v-for="message in state.messageList"
          :key="message"
          @click="onMsg(message)"
          :class="{ read: !message.read }"
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
          <td class="tableName">{{ message.sender.nickname }}</td>
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
    <el-dialog v-model="state.receiveDialog" @close="receiveClose">
      <el-header style="text-align: left; padding-top: 10px">
        <span class="from"> From. </span>
        <span class="fromFriend"> {{ fromFriend }}</span>
        <img v-if="isFriend" class="friendIcon" src="@/assets/friends.png" />
        <img
          v-else
          class="friendIcon"
          @click="addFriend"
          src="@/assets/add-friend.png"
        />
        <img
          class="friendIcon"
          @click="
            sirenOpen();
            receiveClose();
          "
          src="@/assets/siren.png"
        />
        <span class="fromDate"> {{ fromDate }} </span>
      </el-header>
      <el-input v-model="receiveMsg" type="textarea" rows="10" readonly />
      <div style="margin-top: 20px">
        <el-button
          type="success"
          @click="
            state.sendDialog = true;
            receiveClose();
          "
          >답장</el-button
        >
        <el-button @click="receiveClose">닫기</el-button>
      </div>
    </el-dialog>
    <el-dialog v-model="state.sendDialog" @close="sendClose">
      <el-header style="text-align: left; padding-top: 10px">
        <span class="to"> TO. </span>
        <span class="toFriend"> {{ toFriend }}</span>
        <img v-if="isFriend" class="friendIcon" src="@/assets/friends.png" />
        <img
          v-else
          class="friendIcon"
          @click="addFriend"
          src="@/assets/add-friend.png"
        />
      </el-header>
      <el-input
        v-model="state.sendMsg"
        type="textarea"
        placeholder="내용을 입력해주세요"
        rows="10"
      />
      <div style="margin-top: 20px">
        <el-button @click="clickSend" type="success">전송</el-button>
        <el-button @click="sendClose">취소</el-button>
      </div>
    </el-dialog>
    <el-dialog v-model="sirenDialog" @close="sirenClose">
      <div style="font-weight: bold; float: left; margin: 10px">
        신고대상자 : {{ toSiren }}
      </div>
      <el-input
        v-model="sirenMsg"
        type="textarea"
        placeholder="신고사유를 입력해주세요"
        rows="5"
      ></el-input>
      <div style="margin-top: 20px">
        <el-button type="danger" @click="clickSiren">신고하기</el-button>
        <el-button @click="sirenClose">취소</el-button>
      </div>
    </el-dialog>
    <div style="margin: 0 auto; margin-top: 20px">
      <el-pagination
        background
        layout="prev, pager, next"
        @current-change="handleCurrentChange"
        :current-page="state.currentPage"
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
    const selectAll = computed(
      () => state.selected.length === state.messageList.length
    );
    const state = reactive({
      memberinfo: computed(() => store.getters["accounts/getMember"]),
      searchFlag: false,
      messageList: [],
      msgcnt: 0,
      message: {},
      currentPage: 1,
      messageId: "",
      receiveDialog: false,
      selected: [],
      sendDialog: false,
      sendMsg: "",
    });

    const sirenDialog = ref(false);
    const sirenMsg = ref("");
    const toSiren = ref("");
    const fromSiren = ref(state.memberinfo.nickname); // 신고하는 사람 = 로그인 한 사람

    onMounted(() => {
      store
        .dispatch("messages/getReceiveList", {
          email: state.memberinfo.email,
          page: 0,
          key: "",
          word: "",
          size: 8,
        })
        .then(function (result) {
          console.log("result", result);
          state.messageList = result.data.body.messages;
          state.msgcnt = result.data.body.pagingResponse.totalcount;
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
        state.searchFlag = true;
        store
          .dispatch("messages/getReceiveList", {
            email: state.memberinfo.email,
            page: 0,
            key: key.value,
            word: search.value,
            size: 8,
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

    const onRead = function () {
      console.log("read", state.selected.value);
      store
        .dispatch("messages/readList", {
          list: state.selected,
        })
        .then(function (result) {
          console.log("result", result);

          store
            .dispatch("messages/getReceiveList", {
              email: state.memberinfo.email,
              page: state.currentPage - 1,
              key: key.value,
              word: search.value,
              size: 8,
            })
            .then(function (result) {
              console.log("result", result);
              state.messageList = result.data.body.messages;
              state.msgcnt = result.data.body.pagingResponse.totalcount;
              console.log("read-messageList", state.messageList);
              state.selected = [];
            });
        });
    };

    const onDelete = function () {
      console.log("delete", state.selected);
      store
        .dispatch("messages/deleteReceiveList", {
          list: state.selected,
        })
        .then(function (result) {
          console.log("result", result);

          store
            .dispatch("messages/getReceiveList", {
              email: state.memberinfo.email,
              page: 0,
              key: key.value,
              word: search.value,
              size: 8,
            })
            .then(function (result) {
              console.log("result", result);
              state.messageList = result.data.body.messages;
              state.msgcnt = result.data.body.pagingResponse.totalcount;
              state.currentPage = 1;
              console.log("delete-messageList", state.messageList);
            });
        })
        .catch(function (error) {
          alert(error);
        });
    };

    const onMsg = function (i) {
      console.log(i);
      state.messageId = i.id;
      state.receiveDialog = true;
    };

    const receiveClose = function () {
      state.receiveDialog = false;
      state.receiveMsg = "";
    };

    const sendClose = function () {
      state.sendDialog = false;
      state.sendMsg = "";
    };

    const clickSend = function () {
      console.log("clickSend", state.sendMsg);

      if (state.sendMsg == "") {
        alert("보낼 내용을 입력하세요!");
      } else {
        // 쪽지 보내기
        alert(state.sendMsg);
        sendClose();
      }
    };

    const sirenOpen = function () {
      sirenDialog.value = true;
    };

    const sirenClose = function () {
      sirenMsg.value = "";
      toSiren.value = "";
      sirenDialog.value = false;
    };

    const clickSiren = function () {
      console.log(
        "신고",
        sirenMsg.value + " " + toSiren.value + "," + fromSiren.value
      );
      if (sirenMsg.value == "") {
        alert("신고 사유를 입력하세요!");
      } else {
        alert(sirenMsg.value);
        sirenClose();
      }
    };

    const addFriend = function (i) {
      console.log("친구추가", i);
      if (confirm("친구추가 하시겠습니까?")) {
        // 친구추가 하기
        i.isFriend = true;
      }
    };

    const handleCurrentChange = function (val) {
      console.log("page", val);
      state.currentPage = val;
      store
        .dispatch("messages/getReceiveList", {
          email: state.memberinfo.email,
          page: val - 1,
          key: key.value,
          word: search.value,
          size: 8,
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
      sirenDialog,
      toSiren,
      fromSiren,
      sirenMsg,
      onSearch,
      onSelect,
      onRead,
      onDelete,
      onMsg,
      receiveClose,
      sendClose,
      clickSend,
      sirenOpen,
      sirenClose,
      clickSiren,
      addFriend,
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
.from {
  font-size: 30px;
  color: #12a9db;
  font-weight: bolder;
  text-shadow: 2px 4px 2px gray;
}
.fromFriend {
  font-size: 20px;
  font-weight: bold;
}
.friendIcon {
  width: 25px;
  height: 25px;
  margin-left: 15px;
}
.fromDate {
  float: right;
  font-size: 15px;
  padding-top: 10px;
}
.form-checkbox {
  zoom: 1.5;
}
.read {
  font-weight: bold;
}
</style>
