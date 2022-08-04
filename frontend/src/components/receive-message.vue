<template>
  <el-container>
    <el-header>
      <el-row class="row">
        <el-col :span="6"
          ><el-select v-model="key" placeholder="검색키">
            <el-option value="sendFriend" label="보낸사람" />
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
        ><el-button @click="onRead">읽음</el-button>
        <el-button style="margin-right: 30px" @click="onDelete"
          >삭제</el-button
        ></el-col
      >
    </el-row>
    <table class="table">
      <thead>
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
      <tbody>
        <tr
          class="cell"
          :class="{ read: !i.read }"
          v-for="i in tableData"
          :key="i"
          @click="onMsg(i)"
        >
          <td>
            <label class="form-checkbox">
              <input
                type="checkbox"
                :value="i.id"
                v-model="selected"
                @click.stop
              />
              <i class="form-icon"></i>
            </label>
          </td>
          <td class="tableName">{{ i.nickname }}</td>
          <td class="tableMsg">{{ i.msg }}</td>
          <td>{{ i.date }}</td>
        </tr>
      </tbody>
    </table>
    <el-dialog v-model="receiveDialog" @close="receiveClose">
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
            sirenDialog = true;
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
            sendDialog = true;
            receiveClose();
          "
          >답장</el-button
        >
        <el-button @click="receiveClose">닫기</el-button>
      </div>
    </el-dialog>
    <el-dialog v-model="sendDialog" @close="sendClose">
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
        v-model="sendMsg"
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
        :page-size="10"
        :total="msgcnt"
      />
    </div>
  </el-container>
</template>

<script>
import { ref, computed } from "vue";

export default {
  setup() {
    const key = ref("");
    const search = ref("");
    const msgcnt = ref(50);
    const selected = ref([]);
    const selectAll = computed(
      () => selected.value.length === tableData.length
    );
    const fromFriend = ref("");
    const fromDate = ref("");
    const receiveMsg = ref("");
    const receiveDialog = ref(false);
    const toFriend = ref("");
    const toDate = ref("");
    const sendMsg = ref("");
    const sendDialog = ref(false);
    const sirenDialog = ref(false);
    const sirenMsg = ref("");
    const toSiren = ref("");
    const fromSiren = ref("만두왕"); // 신고하는 사람 = 로그인 한 사람
    const isFriend = ref(false);
    const tableData = [
      {
        id: 1,
        read: false,
        isFriend: false,
        nickname: "aqqqq",
        msg: "111ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd",
        date: "2022-07-31",
      },
      {
        id: 2,
        read: false,
        isFriend: true,
        nickname: "b",
        msg: "111",
        date: "2022-07-31",
      },
      {
        id: 3,
        read: false,
        isFriend: true,
        nickname: "c",
        msg: "111",
        date: "2022-07-31",
      },
      {
        id: 4,
        read: true,
        isFriend: true,
        nickname: "d",
        msg: "111",
        date: "2022-07-31",
      },
      {
        id: 5,
        read: true,
        isFriend: true,
        nickname: "e",
        msg: "111",
        date: "2022-07-31",
      },
      {
        id: 6,
        read: true,
        isFriend: true,
        nickname: "f",
        msg: "111",
        date: "2022-07-31",
      },
      {
        id: 7,
        read: true,
        isFriend: true,
        nickname: "g",
        msg: "111",
        date: "2022-07-31",
      },
      {
        id: 8,
        read: true,
        isFriend: true,
        nickname: "h",
        msg: "111",
        date: "2022-07-31",
      },
      {
        id: 9,
        read: true,
        isFriend: true,
        nickname: "i",
        msg: "111",
        date: "2022-07-31",
      },
    ];
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
      console.log(selectAll.value);
      if (!selectAll.value) {
        selected.value = [];
        for (let index in tableData) {
          selected.value.push(tableData[index].id);
        }
      } else {
        selected.value = [];
      }
    };

    const onRead = function () {
      console.log("read", selected.value);
    };

    const onDelete = function () {
      console.log("delete", selected.value);
    };

    const onMsg = function (i) {
      //console.log(i);
      fromFriend.value = i.nickname;
      fromDate.value = i.date;
      receiveMsg.value = i.msg;
      receiveDialog.value = true;
      toFriend.value = i.nickname;
      isFriend.value = i.isFriend;
      toSiren.value = i.nickname;
      i.read = true;
    };

    const receiveClose = function () {
      receiveDialog.value = false;
      receiveMsg.value = "";
      fromFriend.value = "";
      fromDate.value = "";
    };

    const sendClose = function () {
      sendDialog.value = false;
      sendMsg.value = "";
      toFriend.value = "";
      toDate.value = "";
    };

    const clickSend = function () {
      console.log("clickSend", sendMsg.value + " " + toFriend.value);
      let time = new Date();
      let year = String(time.getFullYear());
      let month = time.getMonth() + 1;
      let day = String(
        time.getDate() < 10 ? "0" + time.getDate() : time.getDate()
      );
      let hour = String(
        time.getHours() < 10 ? "0" + time.getHours() : time.getHours()
      );
      let min = String(
        time.getMinutes() < 10 ? "0" + time.getMinutes() : time.getMinutes()
      );
      console.log(
        time,
        year + "-" + month + "-" + day + " " + hour + ":" + min
      );
      if (sendMsg.value == "") {
        alert("보낼 내용을 입력하세요!");
      } else {
        // 쪽지 보내기
        alert(sendMsg.value);
        sendClose();
      }
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

    return {
      key,
      selected,
      selectAll,
      search,
      msgcnt,
      toFriend,
      toDate,
      receiveMsg,
      sendMsg,
      receiveDialog,
      sendDialog,
      fromFriend,
      fromDate,
      tableData,
      sirenDialog,
      toSiren,
      fromSiren,
      sirenMsg,
      isFriend,
      onSearch,
      onSelect,
      onRead,
      onDelete,
      onMsg,
      receiveClose,
      sendClose,
      clickSend,
      sirenClose,
      clickSiren,
      addFriend,
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
