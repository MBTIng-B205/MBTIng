<template>
  <!-- navbar -->
  <div class="navbar">
    <div class="logo">
      <img src="@/assets/logo.png" alt="logo" />
    </div>

    <el-dropdown v-if="state.memberinfo">
      <img
        class="el-dropdown-link"
        id="my_img"
        :src="state.memberinfo.profileUrl"
      />
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

  <mypage-dialog :open="mypageDialog" @closeMyPage="handleClose" />

  <!-- background -->
  <div class="bg">
    <div class="bg0"></div>
    <img
      @click="meetingStart"
      class="startbtn"
      src="@/assets/heart.png"
      alt="example"
    />
    <img class="mainimg" src="@/assets/main.png" alt="" />
    <div class="bg1"></div>
    <div class="bg2"></div>
  </div>

  <!-- menu -->
  <el-carousel
    :interval="4000"
    type="card"
    height="300px"
    style="margin-top: 5rem; margin-bottom: 5rem"
  >
    <el-carousel-item v-for="item in 6" :key="item">
      <h3 class="medium">{{ item }}</h3>
    </el-carousel-item>
  </el-carousel>
</template>

<script>
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { Avatar, Comment, Right } from "@element-plus/icons-vue";
import { computed, reactive } from "@vue/runtime-core";
import { ref } from "vue";
// import axios from "axios";
import MypageDialog from "@/components/mypage-dialog";

export default {
  components: {
    MypageDialog,
  },

  setup() {
    const mypageDialog = ref(false);
    const router = useRouter();
    const store = useStore();
    const state = reactive({
      memberinfo: computed(() => store.getters["accounts/getMember"]),
    });
    console.log(state.memberinfo);
    if (state.memberinfo != null) {
      console.log(state.memberinfo.profileUrl);
    }
    const meetingStart = function () {
      router.push({ name: "MeetingWait" });
    };
    const goPeople = function () {
      router.push({ name: "friend" });
    };
    const goMyPage = function () {
      console.log(mypageDialog.value);
      mypageDialog.value = true;
      console.log(mypageDialog.value);
    };

    const handleClose = function () {
      mypageDialog.value = false;
    };

    // const submit = () => {
    //   axios.post(url= "/api/", state.form).then(res) => {

    //   res.data
    //   };
    // console.log(res)
    // }

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
      login,
      logout,
      meetingStart,
      goPeople,
      goMyPage,
      handleClose,
      mypageDialog,
      Avatar,
      Comment,
      Right,
      state,
    };
  },
};
</script>

<style scoped>
.loginbtn {
  height: 40px;
  width: 100px;
  cursor: pointer;
}

.startbtn {
  height: 100px;
  width: 100px;
  cursor: pointer;
  position: absolute;
  left: 48%;
  top: 50%;
  z-index: 2;
}

.mainimg {
  height: 500px;
  width: 800px;
  position: absolute;
  left: 25%;
  bottom: -5%;
  z-index: 1;
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

.bg {
  position: relative;
}
.bg0 {
  height: 400px;
  width: 100%;
  background-color: #fff4b8;
}
.bg1 {
  height: 60px;
  width: 100%;
  background-color: white;
}
.bg2 {
  height: 30px;
  width: 100%;
  background-color: #e8e8e8;
}

.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}

.profile {
  border-radius: 50%;
  width: 100px;
  height: 100px;
}
</style>
