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
      v-if="state.friends.length != 0 && !state.searchFlag"
      style="flex-direction: row; justify-content: space-between"
    >
      <el-col :span="8" v-for="friend in state.friends" :key="friend">
        <el-card @click="onfriendsProfile(friend)">
          <el-popconfirm
            confirm-button-text="삭제"
            cancel-button-text="취소"
            title="친구를 삭제하시겠습니까?"
            @confirm="deleteFriend(friend)"
          >
            <template #reference>
              <el-button class="delete" :icon="CircleCloseFilled"></el-button>
            </template>
          </el-popconfirm>
          <img class="friendProfile" src="@/assets/profile.png" />
          <div style="font-weight: bold">
            <p>{{ friend.nickname }}</p>
            <p>{{ friend.mbti }}</p>
          </div>
          <el-button
            @click.stop="
              dialogVisible = true;
              check(friend);
            "
            >쪽지 보내기</el-button
          >
          <el-dialog v-model="dialogVisible" @close="messageClose">
            <el-header style="text-align: left; padding-top: 10px">
              <span class="to"> TO. </span>
              <span class="toFriend"> {{ toFriend }}</span>
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
              <el-button @click="handleClose">취소</el-button>
            </div>
          </el-dialog>
        </el-card>
      </el-col>
    </el-row>

    <el-row v-else-if="state.search">검색한 친구가 없습니다!</el-row>
    <el-row v-else> 친구를 추가해보세요! </el-row>

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
            <el-form-item label="Nickname">
              <el-input
                style="width: 200px"
                v-model="state.friend.nickname"
                readonly
              />
            </el-form-item>
            <el-form-item label="Gender">
              <el-input
                style="width: 200px"
                v-model="state.friend.gender"
                readonly
              />
            </el-form-item>
            <el-form-item label="Birthday">
              <el-input
                style="width: 200px"
                v-model="state.friend.birth"
                readonly
              />
            </el-form-item>
            <el-form-item label="Place">
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

    const toFriend = ref("");
    const dialogVisible = ref(false);
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
      if (message.value == "") {
        alert("보낼 내용을 입력하세요!");
      } else {
        // 쪽지 보내기
        alert(message.value);
        messageClose();
      }
    };

    const messageClose = function () {
      dialogVisible.value = false;
      message.value = "";
      toFriend.value = "";
    };

    const check = function (friend) {
      //console.log(friend);
      toFriend.value = friend.nickname;
    };

    return {
      state,
      search,
      key,
      dialogVisible,
      message,
      toFriend,
      CircleCloseFilled,
      UserFilled,
      onFriendProfile,
      friendProfileClose,
      onSearch,
      deleteFriend,
      clickSend,
      messageClose,
      check,
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
  border: 1px solid black;
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
</style>
