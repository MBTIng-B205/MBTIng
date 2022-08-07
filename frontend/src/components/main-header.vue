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
          <el-form-item label="닉네임">
            <el-input
              style="width: 200px"
              v-model="state.memberinfo.nickname"
            />
            <el-button @click="nameCheck" style="margin-left: 10px"
              >중복확인</el-button
            >
          </el-form-item>
          <el-form-item label="성별">
            <el-radio-group v-model="state.memberinfo.gender">
              <el-radio :label="true">남자</el-radio>
              <el-radio :label="false">여자</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="생년월일">
            <el-date-picker v-model="state.memberinfo.birth" type="date" />
          </el-form-item>
          <el-form-item label="지역">
            <el-select v-model="state.memberinfo.sido">
              <el-option
                v-for="item in option"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-col style="flex-direction: column">
            <el-form-item label="관심사">
              <el-checkbox-group
                v-model="state.memberinfo.interests"
                style="width: 380px; align-items: center"
              >
                <el-checkbox label="캠핑" name="캠핑" />
                <el-checkbox label="맛집탐방" name="맛집탐방" />
                <el-checkbox label="코딩" name="코딩" />
                <el-checkbox label="TV/영화" name="TV/영화" />
                <el-checkbox label="스포츠" name="스포츠" />
                <el-checkbox label="술" name="술" />
                <el-checkbox label="음악" name="음악" />
                <el-checkbox label="쇼핑" name="쇼핑" />
                <el-checkbox label="자동차" name="자동차" />
                <el-checkbox label="게임" name="게임" />
                <el-checkbox label="동물" name="동물" />
                <el-checkbox label="패션" name="패션" />
                <el-checkbox label="뷰티" name="뷰티" />
                <el-checkbox label="디자인" name="디자인" />
              </el-checkbox-group>
            </el-form-item>
          </el-col>
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
    const option = [
      {
        value: "서울",
        label: "서울",
      },
      {
        value: "인천",
        label: "인천",
      },
      {
        value: "경기",
        label: "경기",
      },
      {
        value: "강원",
        label: "강원",
      },
      {
        value: "부산",
        label: "부산",
      },
      {
        value: "대전",
        label: "대전",
      },
      {
        value: "울산",
        label: "울산",
      },
      {
        value: "충북",
        label: "충북",
      },
      {
        value: "충남",
        label: "충남",
      },
      {
        value: "세종",
        label: "세종",
      },
      {
        value: "대구",
        label: "대구",
      },
      {
        value: "경북",
        label: "경북",
      },
      {
        value: "경남",
        label: "경남",
      },
      {
        value: "광주",
        label: "광주",
      },
      {
        value: "전북",
        label: "전북",
      },
      {
        value: "전남",
        label: "전남",
      },
      {
        value: "제주",
        label: "제주",
      },
    ];
    // let flag = false;
    const nameCheck = function () {
      const nickname = state.memberinfo.nickname;
      console.log("이거는프로필 닉네임", nickname);
      store.dispatch("accounts/getUserName", { nickname }).then(function (res) {
        console.log("res", res);
        if (res.data.body === true) {
          alert("사용가능한 닉네임 입니다.");
          // flag = true;
        } else {
          alert("중복 된 닉네임입니다.");
          // flag = false;
        }
      });
    };
    const goHome = function () {
      router.push({ name: "HomeView" });
    };
    console.log(state.memberinfo);
    const goPeople = function () {
      router.push({ name: "friend" });
    };
    const goMyPage = function () {
      state.mypageDialog = true;
      console.log("mypage", state.memberinfo);
    };

    const handleClose = function () {
      state.mypageDialog = false;
    };
    const updateInfo = function () {
      store
        .dispatch("accounts/updateMemberinfo")
        .then(function (result) {
          console.log(result);
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
        `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.VUE_APP_KAKAO_CLIENT_ID}&redirect_uri=${process.env.VUE_APP_KAKAO_LOGIN_REDIRECT_URI}&response_type=code`
      );
    };

    const logout = () => {
      window.location.replace(
        `https://kauth.kakao.com/oauth/logout?client_id=${process.env.VUE_APP_KAKAO_CLIENT_ID}&logout_redirect_uri=${process.env.VUE_APP_KAKAO_LOGOUT_REDIRECT_URI}`
      );
    };

    return {
      state,
      option,
      nameCheck,
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
  margin: 20px;
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
