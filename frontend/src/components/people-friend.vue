<template>
  <el-container style="height: 620px">
    <el-header>
      <el-row style="flex-direction: row; margin-top: 15px">
        <el-col :span="6"
          ><el-select v-model="key" placeholder="검색키">
            <el-option value="nickname" label="닉네임" />
            <el-option value="mbti" label="MBTI" /> </el-select
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
    <el-row
      v-if="state.friends.length != 0"
      style="flex-direction: row; justify-content: flex-start"
    >
      <el-col :span="8" v-for="friend in state.friends" :key="friend">
        <el-card>
          <button
            @click.stop
            class="magnifier"
            size="large"
            @click="friendProfileOpen(friend)"
          >
            <img src="@/assets/magnifier.png" />
          </button>

          <img class="friendProfile" :src="friend.profileUrl" />
          <div style="font-weight: bold">
            <p>{{ friend.nickname }}</p>
            <p>{{ friend.mbti }}</p>
          </div>
          <button class="messageButton" @click.stop="messageOpen(friend)">
            쪽지 보내기
          </button>
        </el-card>
      </el-col>
    </el-row>

    <el-row class="exceptionMsg" v-else-if="state.searchFlag"
      >검색한 친구가 없습니다!</el-row
    >
    <el-row class="exceptionMsg" v-else> 친구를 추가해보세요! </el-row>

    <el-dialog v-model="messageDialog" @close="messageClose">
      <el-header style="text-align: left; padding-top: 10px">
        <span class="to"> To. </span>
        <span class="toFriend"> {{ state.toFriend.nickname }}</span>
        <img class="friendIcon" src="@/assets/friends.png" />
      </el-header>
      <el-input
        v-model="message"
        type="textarea"
        placeholder="내용을 입력해주세요"
        rows="10"
      />
      <div style="margin-top: 20px">
        <button class="mainButton messageSend" @click="clickSend">전송</button>
        <button class="mainButton messageCancel" @click="messageClose">
          취소
        </button>
      </div>
    </el-dialog>

    <el-dialog v-model="state.friendProfileDialog" @close="friendProfileClose">
      <div>
        <img class="profile" :src="state.friend.profileUrl" />
        <table>
          <tbody>
            <tr>
              <td class="label">MBTI</td>
              <td>{{ state.friend.mbti }}</td>
            </tr>
            <tr>
              <td class="label">닉네임</td>
              <td>{{ state.friend.nickname }}</td>
            </tr>
            <tr>
              <td class="label">성별</td>
              <td v-if="state.friend.gender == 'MALE'">남자</td>
              <td v-else>여자</td>
            </tr>
            <tr>
              <td class="label">생년월일</td>
              <td>{{ state.friend.birth }}</td>
            </tr>
            <tr>
              <td class="label">사는지역</td>
              <td>{{ state.friend.sido }}</td>
            </tr>
            <tr>
              <td class="label">관심사</td>
              <td>{{ interests }}</td>
            </tr>
          </tbody>
        </table>
        <el-button
          type="danger"
          round
          style="margin-top: 20px"
          size="large"
          @click="deleteFriend(friend)"
          >친구 삭제</el-button
        >
      </div>
    </el-dialog>
  </el-container>
</template>

<script>
import { useStore } from "vuex";
import { ref, reactive, onMounted, computed } from "vue";
export default {
  setup() {
    const key = ref("");
    const search = ref("");
    const store = useStore();
    const interests = ref("선택한 관심사가 없습니다.");
    const state = reactive({
      memberinfo: computed(() => store.getters["accounts/getMember"]),
      searchFlag: false,
      friendProfileDialog: false,
      friends: [],
      friend: {},
      toFriend: {},
    });

    onMounted(() => {
      store
        .dispatch("friends/getFriendsList", state.memberinfo.email)
        .then(function (result) {
          console.log("result", result);
          state.friends = result.data.body.friends;
          console.log("friends", state.friends);
        });
    });

    const messageDialog = ref(false);
    const message = ref("");

    const friendProfileOpen = function (friend) {
      console.log(friend);
      state.friend = friend;

      if (friend.interests.length != 0) {
        interests.value = "";
        for (let index = 0; index < friend.interests.length; index++) {
          interests.value += friend.interests[index];
          if (index < friend.interests.length - 1) {
            interests.value += ", ";
          }
        }
      } else {
        interests.value = "선택한 관심사가 없습니다.";
      }

      console.log(interests.value);
      state.friendProfileDialog = true;
    };

    const friendProfileClose = function () {
      state.friendProfileDialog = false;
    };

    const onSearch = function () {
      if (key.value == "") {
        alert("검색키를 선택하세요");
      } else if (search.value == "") {
        alert("검색어를 입력하세요");
      } else {
        state.searchFlag = true;
        if (key.value == "nickname") {
          store
            .dispatch("friends/getFriendByName", {
              nickname: search.value,
              email: state.memberinfo.email,
            })
            .then(function (result) {
              console.log("result", result);
              state.friends = result.data.body.friends;
              console.log("friends-nickname", state.friends);
            });
        } else {
          store
            .dispatch("friends/getFriendByMbti", {
              mbti: search.value,
              email: state.memberinfo.email,
            })
            .then(function (result) {
              console.log("result", result);
              state.friends = result.data.body.friends;
              console.log("friends-mbti", state.friends);
            });
        }
      }
    };

    const deleteFriend = function () {
      // 친구 삭제 기능
      console.log(state.friend);
      if (confirm("친구를 삭제하시겠습니까?")) {
        store
          .dispatch("friends/deleteFriend", {
            from: state.memberinfo.email,
            to: state.friend.email,
          })
          .then(function (result) {
            console.log("deleteResult", result);
            state.friendProfileDialog = false;
            store
              .dispatch("friends/getFriendsList", state.memberinfo.email)
              .then(function (result) {
                console.log("result", result);
                state.friends = result.data.body.friends;
                console.log("friends", state.friends);
                console.log("friend", state.friends[0]);
              });
          });
      }
    };

    const clickSend = function () {
      console.log("clickSend", message);
      if (message.value == "") {
        alert("보낼 내용을 입력하세요!");
      } else {
        console.log(
          "send",
          state.memberinfo.email +
            " " +
            state.toFriend.email +
            " " +
            message.value
        );

        store
          .dispatch("messages/sendMsg", {
            senderId: state.memberinfo.email,
            receiverId: state.toFriend.email,
            content: message.value,
          })
          .then(function (result) {
            console.log("sendmsg", result);
            alert("쪽지 전송 완료!");
          });
        messageClose();
      }
    };

    const messageClose = function () {
      messageDialog.value = false;
      message.value = "";
    };

    const messageOpen = function (friend) {
      //console.log(friend);
      state.toFriend = friend;
      messageDialog.value = true;
    };

    return {
      state,
      search,
      key,
      interests,
      messageDialog,
      message,
      friendProfileOpen,
      friendProfileClose,
      onSearch,
      deleteFriend,
      clickSend,
      messageClose,
      messageOpen,
    };
  },
};
</script>

<style scoped>
.el-header {
  background-color: rgb(255, 189, 207);
}
.friendProfile {
  width: 100px;
  height: 100px;
  margin-left: 40px;
  border-radius: 100%;
  background-color: white;
}
.friendInfo {
  font-size: large;
  border: 10px solid rgb(255, 189, 207);
  margin-left: 60px;
  margin-right: 60px;
}
table {
  margin-left: auto;
  margin-right: auto;
  width: 500px;
  font-size: 20px;
  border-spacing: 0 20px;
  border: 10px solid #fadce1;
}
.label {
  width: 130px;
  color: rgb(255, 91, 136);
}
.magnifier {
  cursor: pointer;
  float: right;
  border: 0;
  border-radius: 10px;
  background-color: white;
  padding: 5px;
  padding-bottom: 2px;
}
.magnifier:hover {
  background-color: #fadce1;
}
.el-card {
  width: 250px;
  margin: 20px;
  font-size: 20px;
  padding: 20px;
  margin: auto;
  margin-top: 20px;
  margin-bottom: 20px;
  border: solid 5px white;
}
.el-card:hover {
  border: solid 5px #fadce1;
}
.el-dialog {
  padding: 0;
}
.el-form-item {
  font-size: large;
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
.profile {
  border-radius: 50%;
  width: 200px;
  height: 200px;
  margin-top: 10px;
  margin-bottom: 20px;
}
input:focus {
  border-color: palevioletred;
}

.mainButton {
  cursor: pointer;
  padding: 12px 19px;
  height: 40px;
  font-size: 14px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  line-height: 1;
  align-items: center;
  background-color: #ffffff;
  color: #606266;
}

.mainButton:hover {
  color: palevioletred;
  background-color: #fbeff1;
  border-color: #fbeff1;
}

.messageButton {
  cursor: pointer;
  width: 200px;
  background-color: rgb(255, 189, 207);
  padding: 10px;
  border-radius: 10px;
  border: solid rgb(255, 189, 207);
}
.messageButton:active {
  background-color: rgb(255, 91, 136);
  color: white;
}
.messageButton:hover {
  background-color: rgb(255, 91, 136);
  color: white;
}
.activeCard .el-card__body {
  background-color: rgb(255, 91, 136);
}
.el-select .el-input.is-focus .el-input__wrapper {
  box-shadow: 0 0 0 1px palevioletred;
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
.messageCancel {
  margin-left: 18px;
  border-radius: 20px;
}
.messageSend {
  margin-left: 18px;
  border-radius: 20px;
  background-color: rgb(255, 189, 207);
}
</style>
