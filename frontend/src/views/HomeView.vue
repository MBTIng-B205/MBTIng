<template>
  <main-header />
  <!-- background -->
  <div
    style="
      width: 480px;
      position: absolute;
      z-index: 2;
      color: #00001;
      left: 10%;
      top: 18%;
    "
  >
    <h3>MBTI를 통해서 마음이 통하는 친구를 만나보세요!</h3>

    <h4 style="line-height: 1px; margin-left: 1rem">
      <span style="color: deeppink">♥</span>
      하트버튼을 클릭하면 매칭이 시작됩니다.
    </h4>
    <h4 style="line-height: 0.25px; margin-left: 1rem">
      <span style="color: deeppink">♥</span>
      블라인드 소개팅 동안 상대방이 마음에 들면 그린라이트를 누르세요.
    </h4>
    <h4 style="line-height: 0.25px; margin-left: 1rem">
      <span style="color: deeppink">♥</span>
      화상소개팅으로 연결됩니다 : )
    </h4>
  </div>
  <div class="bg">
    <div class="bg0"></div>
    <img
      @click="meetingStart"
      class="startbtn"
      src="@/assets/heart.png"
      alt="example"
      style="z-index: 2"
    />
    <img
      v-show="state.memberinfo"
      class="startbtnlogin"
      src="@/assets/heart_login.png"
      alt=""
      style="z-index: 1"
    />
    <img class="mainimg" src="@/assets/main.png" alt="" />

    <div class="bg1"></div>
    <div class="bg2"></div>
  </div>

  <!-- menu -->
  <div class="content-name">
    <h1>MBTI Contents</h1>
  </div>
  <el-row class="card-row">
    <el-col
      v-for="(o, index) in 4"
      :key="o"
      :span="4"
      :offset="index > 0 ? 2 : 0"
    >
      <el-card
        :body-style="{ padding: '0px' }"
        class="card-body"
        :href="contents1[index].site"
      >
        <img :src="contents1[index].thumb" class="image" />
        <div>
          <div>
            <el-link :href="contents1[index].site" :underline="false">
              <el-button type="danger" size="large" round
                >테스트시작</el-button
              ></el-link
            >
          </div>
        </div>
      </el-card>
    </el-col>
  </el-row>
  <el-dialog top="250px" v-model="state.homeDialog" width="30%" center>
    <el-row style="top: 12px; font-size: 16.5px"
      >로그인을 먼저 진행해주세요. 로그인 페이지로 이동합니다</el-row
    >
    <template #footer>
      <span class="dialog-footer">
        <el-button type="danger" round @click="gologin">확인</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { Avatar, Comment, Right } from "@element-plus/icons-vue";
import { computed, reactive } from "@vue/runtime-core";
import { ref } from "vue";
import MainHeader from "@/components/main-header.vue";
export default {
  components: {
    MainHeader,
  },
  setup() {
    const mypageDialog = ref(false);
    const router = useRouter();
    const store = useStore();
    const state = reactive({
      memberinfo: computed(() => store.getters["accounts/getMember"]),
      homeDialog: false,
    });
    console.log(state.memberinfo);
    if (state.memberinfo != null) {
      console.log(state.memberinfo.profileUrl);
    }
    const meetingStart = function () {
      let jwt = null;
      jwt = sessionStorage.getItem("access-token");
      if (jwt != null) router.push({ name: "MeetingWait" });
      else state.homeDialog = true;
    };
    const gologin = function () {
      state.homeDialog = false;
      window.location.replace(
        `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.VUE_APP_KAKAO_CLIENT_ID}&redirect_uri=${process.env.VUE_APP_KAKAO_LOGIN_REDIRECT_URI}&response_type=code`
      );
    };
    const contents1 = [
      {
        thumb: require("@/assets/mbti001.png"),
        title: "나의 MBTI 테스트",
        site: "/yourmbtitest",
      },
      {
        thumb: require("@/assets/mbti002.png"),
        title: "나의 이상형 MBTI 테스트",
        site: "/mymbtitest",
      },
      {
        thumb: require("@/assets/mbti003.png"),
        title: "MBTI 월드컵",
        site: "/mbtiworldcup",
      },
      {
        thumb: require("@/assets/mbti004.png"),
        title: "MBTI 별 매칭 성공률",
        site: "/mbtichart",
      },
    ];
    return {
      meetingStart,
      gologin,
      mypageDialog,
      Avatar,
      Comment,
      Right,
      state,
      contents1,
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
  margin: 200px 350px 200px 350px;
  cursor: pointer;
  position: absolute;
  left: 42%;
  top: -30%;
  z-index: 2;
}
.startbtnlogin {
  height: 100px;
  width: 200px;
  margin: 200px 300px 200px 300px;
  position: absolute;
  left: 42%;
  top: -45%;
  z-index: 2;
}
.mainimg {
  height: 500px;
  width: 800px;
  position: absolute;
  left: 42%;
  bottom: -6%;
  z-index: 1;
}
.bg {
  position: relative;
}
.bg0 {
  height: 250px;
  width: 100%;
  background-color: #fadce1;
}
.bg1 {
  height: 60px;
  width: 100%;
  background-color: white;
}
.bg2 {
  height: 30px;
  width: 100%;
  background-color: white;
}

.el-row {
  display: flex;
  flex-wrap: wrap;
  position: relative;
  align-items: center;
  justify-content: center;
  align-content: stretch;
  flex-direction: row;
}
.el-card__body {
  width: 10px;
}
.time {
  font-size: 12px;
  color: #999;
}

.bottom {
  /* margin-top: 13px;
  line-height: 12px; */
  display: flex;
  justify-content: space-around;
}

.button {
  padding: 0;
  min-height: auto;
}

.image {
  width: 100%;
  height: 20vh;
  object-fit: contain;
}
.profile {
  border-radius: 50%;
  width: 100px;
  height: 100px;
}
.card-row {
  padding-top: 10px;
}
.card-body {
  border: top 100px;
  padding: 5px;
  margin: 0px;
  text-align: center;
}
.content-name {
  text-align: center;
}
.testbutton {
  color: black;
  width: 15vw;
  border-radius: 0.5rem;
  border-color: white;
  box-shadow: 0;
}
</style>
