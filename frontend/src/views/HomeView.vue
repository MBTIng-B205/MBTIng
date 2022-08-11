<template>
  <main-header />
  <!-- background -->
  <div class="bg">
    <div class="bg0"></div>
    <img
      @click="meetingStart"
      class="startbtn"
      src="@/assets/heart.png"
      alt="example"
    />
    <img
      class="mainimg"
      src="@/assets/main.png"
      alt=""
      style="object-fit: contain; width: 100%; height: 100%"
    />
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
          <span>{{ contents1[index].title }}</span>
          <div class="bottom">
            <el-link :href="contents1[index].site"
              ><el-button class="testbutton" style="background: #e37a80"
                >테스트시작</el-button
              ></el-link
            >
          </div>
        </div>
      </el-card>
    </el-col>
    <el-col>
      <div class="content-name">
        <h1>MBTI Contents Links</h1>
      </div></el-col
    >
    <el-col
      v-for="(o, index) in 4"
      :key="o"
      :span="4"
      :offset="index > 0 ? 2 : 0"
    >
      <el-card
        :body-style="{ padding: '0px' }"
        class="card-body"
        :href="contents2[index].site"
      >
        <img :src="contents2[index].thumb" class="image" fit="cover" />
        <div>
          <el-link :href="contents2[index].site"
            ><span>{{ contents2[index].title }}</span></el-link
          >
          <div class="bottom">
            <el-link :href="contents2[index].site" target="_blank"
              ><el-button class="testbutton" style="background: #afe49a"
                >테스트시작</el-button
              ></el-link
            >
          </div>
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { Avatar, Comment, Right } from "@element-plus/icons-vue";
import { computed, reactive } from "@vue/runtime-core";
import { ref } from "vue";
// import axios from "axios";
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
    });
    console.log(state.memberinfo);
    if (state.memberinfo != null) {
      console.log(state.memberinfo.profileUrl);
    }
    const meetingStart = function () {
      router.push({ name: "MeetingWait" });
    };

    const contents1 = [
      {
        thumb:
          "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkOzMl%2FbtqESXEhYI3%2FgKvKEjw5aioz06wwW0wkP0%2Fimg.png",
        title: "나의 MBTI 테스트",
        site: "/mymbtitest",
      },
      {
        thumb:
          "https://www.simcong.com/thumb/upfiles/quiz/202011/03_f7ab17586c219cfa2eac45aa651f18d3441378_5206.jpg?w=640&h=",
        title: "나의 이상형 MBTI 테스트",
        site: "/yourmbtitest",
      },
      {
        thumb:
          "https://d3d45df40onv5v.cloudfront.net/mbti/assets/img/maincat.gif",
        title: "MBTI 월드컵",
        site: "/mbtiworldcup",
      },
      {
        thumb:
          "https://mbti.theblessedmoon.com/static/media/intro_logo.d0ce37d8.gif",
        title: "썸 추진력 MBTI",
        site: "https://mbti.theblessedmoon.com/",
      },
    ];
    const contents2 = [
      {
        thumb:
          "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkOzMl%2FbtqESXEhYI3%2FgKvKEjw5aioz06wwW0wkP0%2Fimg.png",
        title: "연애 능력치 테스트",
        site: "http://16types.glam.am/intro",
      },
      {
        thumb:
          "https://www.simcong.com/thumb/upfiles/quiz/202011/03_f7ab17586c219cfa2eac45aa651f18d3441378_5206.jpg?w=640&h=",
        title: "연애유형 테스트",
        site: "https://www.simcong.com/quiz/393",
      },
      {
        thumb:
          "https://d3d45df40onv5v.cloudfront.net/mbti/assets/img/maincat.gif",
        title: "연애 유형 테스트",
        site: "https://mbti.amanda.co.kr/",
      },
      {
        thumb:
          "https://mbti.theblessedmoon.com/static/media/intro_logo.d0ce37d8.gif",
        title: "썸 추진력 MBTI",
        site: "https://mbti.theblessedmoon.com/",
      },
    ];
    return {
      meetingStart,
      mypageDialog,
      Avatar,
      Comment,
      Right,
      state,
      contents1,
      contents2,
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

.bg {
  position: relative;
}
.bg0 {
  height: 400px;
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
  background-color: #e8e8e8;
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
/* .el-carousel__item h3 {
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
} */
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
  padding: 0px;
  margin: 0px;
  text-align: center;
}
.content-name {
  text-align: center;
}
.testbutton {
  color: black;
}
</style>
