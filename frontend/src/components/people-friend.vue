<template>
  <el-container>
    <el-header>
      <el-row style="flex-direction: row; margin-top: 15px">
        <el-col :span="6"
          ><el-select v-model="key" placeholder="검색키">
            <el-option value="nickname" label="닉네임" />
            <el-option value="mbti" label="MBTI" /> </el-select
        ></el-col>
        <el-col :span="12">
          <el-input v-model="search" />
        </el-col>
        <el-col :span="6">
          <el-button @click="onSearch">검색</el-button>
        </el-col>
      </el-row>
    </el-header>
    <el-row
      v-if="state.friends.length != 0"
      style="flex-direction: row; justify-content: flex-start"
    >
      <el-col :span="8" v-for="friend in state.friends" :key="friend">
        <el-card @click="onFriendProfile(friend)">
          <el-popconfirm
            confirm-button-text="삭제"
            cancel-button-text="취소"
            title="친구를 삭제하시겠습니까?"
            @confirm="deleteFriend(friend)"
          >
            <template #reference>
              <el-button
                @click.stop
                class="delete"
                :icon="CircleCloseFilled"
              ></el-button>
            </template>
          </el-popconfirm>
          <img class="friendProfile" :src="friend.profileUrl" />
          <div style="font-weight: bold">
            <p>{{ friend.nickname }}</p>
            <p>{{ friend.mbti }}</p>
          </div>
          <el-button @click.stop="messageOpen(friend)">쪽지 보내기</el-button>
        </el-card>
      </el-col>
    </el-row>

    <el-row v-else-if="state.searchFlag">검색한 친구가 없습니다!</el-row>
    <el-row v-else> 친구를 추가해보세요! </el-row>

    <el-dialog v-model="messageDialog" @close="messageClose">
      <el-header style="text-align: left; padding-top: 10px">
        <span class="to"> TO. </span>
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
        <el-button type="success" @click="clickSend">전송</el-button>
        <el-button @click="messageClose">취소</el-button>
      </div>
    </el-dialog>

    <el-dialog v-model="state.friendProfileDialog" @close="friendProfileClose">
      <div style="text-align: center">
        <el-row>
          <img class="profile" :src="state.friend.profileUrl" />
        </el-row>
        <el-row>
          <el-form
            :model="state.friend"
            :label-position="right"
            label-width="100px"
            style="margin-top: 30px; margin-bottom: 30px; align-items: center"
          >
            <el-form-item label="MBTI">
              <el-input
                style="width: 200px"
                v-model="state.friend.mbti"
                readonly
              />
            </el-form-item>
            <el-form-item label="닉네임">
              <el-input
                style="width: 200px"
                v-model="state.friend.nickname"
                readonly
              />
            </el-form-item>
            <el-form-item label="성별">
              <el-radio-group v-model="state.friend.gender">
                <el-radio :label="true" disabled>남자</el-radio>
                <el-radio :label="false" disabled>여자</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="생년월일">
              <el-input
                style="width: 200px"
                v-model="state.friend.birth"
                readonly
              />
            </el-form-item>
            <el-form-item label="사는지역">
              <el-input
                style="width: 200px"
                v-model="state.friend.sido"
                readonly
              />
            </el-form-item>
          </el-form>
        </el-row>
      </div>
    </el-dialog>
  </el-container>
</template>

<script>
import { useStore } from "vuex";
import { ref, reactive, onMounted, computed } from "vue";
import { CircleCloseFilled, UserFilled } from "@element-plus/icons-vue";
export default {
  setup() {
    const key = ref("");
    const search = ref("");
    const store = useStore();
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

    const onFriendProfile = function (friend) {
      console.log(friend);
      state.friend = friend;
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

    const deleteFriend = function (friend) {
      // 친구 삭제 기능
      console.log(friend);
      store
        .dispatch("friends/deleteFriend", {
          from: state.memberinfo.email,
          to: friend.email,
        })
        .then(function (result) {
          console.log("deleteResult", result);
          store
            .dispatch("friends/getFriendsList", state.memberinfo.email)
            .then(function (result) {
              console.log("result", result);
              state.friends = result.data.body.friends;
              console.log("friends", state.friends);
              console.log("friend", state.friends[0]);
            });
        });
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
      messageDialog,
      message,
      CircleCloseFilled,
      UserFilled,
      onFriendProfile,
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

<style>
.el-header {
  background-color: #9dd098;
}
.friendProfile {
  width: 100px;
  height: 100px;
  margin-left: 40px;
  border-radius: 100%;
  background-color: white;
}
.delete {
  float: right;
  border: 0;
}
.el-card {
  width: 250px;
  margin: 20px;
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
.profile {
  border-radius: 50%;
  width: 200px;
  height: 200px;
}
</style>
