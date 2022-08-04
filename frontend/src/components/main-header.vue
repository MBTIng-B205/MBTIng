<template>
  <div class="navbar">
    <div class="logo">
      <img @click="goHome" src="@/assets/logo.png" alt="logo" />
    </div>

    <el-dropdown v-if="state.memberinfo">
      <img class="el-dropdown-link" :src="state.memberinfo.profileUrl" />
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="goMyPage" :icon="Avatar"
            >마이페이지</el-dropdown-item
          >
          <el-dropdown-item @click="goPeople" :icon="Comment">
            피플
          </el-dropdown-item>
          <el-dropdown-item @click="logout" :icon="Right"
            >로그아웃
          </el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>

    <img
      v-else
      class="loginbtn"
      @click="login"
      src="@/assets/kakao_login.png"
      alt="login"
    />
  </div>

  <el-dialog v-model="state.mypageDialog" @close="handleClose">
    <div style="text-align: center">
      <el-row>
        <img class="profile" :src="state.memberinfo.profileUrl" />
        <el-button style="margin-top: 10px" size="large">프로필 변경</el-button>
      </el-row>
      <el-row>
        <el-form
          :model="state.memberinfo"
          :label-position="right"
          label-width="100px"
          style="margin-top: 30px; margin-bottom: 30px; align-items: center"
        >
          <el-form-item label="MBTI">
            <el-input style="width: 200px" v-model="state.memberinfo.mbti" />
          </el-form-item>
          <el-form-item label="Nickname">
            <el-input
              style="width: 200px"
              v-model="state.memberinfo.nickname"
            />
            <el-button>중복확인</el-button>
          </el-form-item>
          <el-form-item label="Gender">
            <el-radio-group v-model="state.memberinfo.gender">
              <el-radio :label="true">Male</el-radio>
              <el-radio :label="false">Female</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="Birthday">
            <el-date-picker v-model="state.memberinfo.birth" type="date" />
          </el-form-item>
          <el-form-item label="Place">
            <el-select v-model="state.memberinfo.sido">
              <el-option label="서울" value="서울" />
              <el-option label="대전" value="대전" />
            </el-select>
          </el-form-item>
        </el-form>
      </el-row>
      <el-footer>
        <el-button @click="updateInfo">수정</el-button>
        <el-button @click="deleteMember" type="danger">탈퇴</el-button>
      </el-footer>
    </div>
  </el-dialog>
</template>

<script>
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { Avatar, Comment, Right } from "@element-plus/icons-vue";
import { computed, reactive } from "@vue/runtime-core";

export default {
  setup() {
    const router = useRouter();
    const store = useStore();
    const state = reactive({
      memberinfo: computed(() => store.getters["accounts/getMember"]),
      mypageDialog: false,
    });

    const goHome = function () {
      router.push({ name: "HomeView" });
    };
    console.log(state.memberinfo);
    const goPeople = function () {
      router.push({ name: "friend" });
    };
    const goMyPage = function () {
      state.mypageDialog = true;
    };

    const handleClose = function () {
      state.mypageDialog = false;
    };
    const updateInfo = function () {
      store
        .dispatch("accounts/updateMemberinfo")
        .then(function (result) {
          store.commit("accounts/SET_MEMBER_INFO", result.data);
        })
        .catch(function (err) {
          console.log(err);
        });
    };

    const deleteMember = function () {
      // 회원 탈퇴
      store
        .dispatch("accounts/deleteMemberinfo")
        .then(function (result) {
          console.log(result);
          sessionStorage.removeItem("access-token");
          store.commit("accounts/SET_MEMBER_INFO", null);
          console.log(store.state.member);
          state.mypageDialog = false;
          router.push({ name: "HomeView" });
        })
        .catch(function (err) {
          console.log(err);
        });
    };

    const login = () => {
      // 카카오톡 로그인 화면 전환
      window.location.replace(
        "https://kauth.kakao.com/oauth/authorize?client_id=ebb8bb50d4cb227cf989335c827681e5&redirect_uri=http://localhost:80/loginview&response_type=code"
      );
    };

    const logout = () => {
      window.location.replace(
        "https://kauth.kakao.com/oauth/logout?client_id=ebb8bb50d4cb227cf989335c827681e5&logout_redirect_uri=http://localhost:80/logoutview"
      );
    };

    return {
      state,
      goHome,
      goPeople,
      goMyPage,
      handleClose,
      updateInfo,
      deleteMember,
      login,
      logout,
      Avatar,
      Comment,
      Right,
    };
  },
};
</script>

<style>
.loginbtn {
  height: 40px;
  width: 100px;
  cursor: pointer;
}
.logo {
  display: flex;
  height: 80px;
  width: 80px;
}

.el-dropdown {
  display: flex;
  margin-top: 20px;
  margin-right: 30px;
  float: right;
}

.el-dropdown-link {
  border-radius: 50%;
  object-fit: cover;
  width: 50px;
  height: 50px;
}

.navbar {
  background-color: #fff4b8;
  display: flex;
  justify-content: space-between;
}
.profile {
  border-radius: 50%;
  width: 200px;
  height: 200px;
}
</style>
