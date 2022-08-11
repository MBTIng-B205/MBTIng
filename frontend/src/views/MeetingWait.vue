<template>
  <el-container style="background-color: #fff4b8">
    <el-header>
      <img @click="goHome" class="logo" src="@/assets/logo.png" />
      <el-button
        style="float: right; margin-top: 25px"
        type="danger"
        round
        @click="goHome"
        >소개팅종료</el-button
      >
    </el-header>
    <el-card
      style="
        text-align: center;
        background-color: #fff4b8;
        box-shadow: none;
        border: none;
      "
    >
      <div>
        <img class="small" src="@/assets/smallpink.png" />
        <div class="infoBox">
          <div style="font-weight: bold; font-size: xx-large">
            알아두면 좋은 MBTI 정보
          </div>
          <div style="font-size: x-large; margin-top: 50px">
            {{ Info[Math.floor(Math.random() * 15)].info }}
          </div>
        </div>
        <img class="small" src="@/assets/smallgreen.png" />
      </div>
      <el-footer style="margin-top: 50px">
        <el-row
          v-loading="loading"
          element-loading-text="MBTI 짝궁을 찾고 있어요..!"
          :element-loading-spinner="svg"
          element-loading-svg-view-box="-10, -10, 50, 50"
        ></el-row>
      </el-footer>
    </el-card>
  </el-container>
</template>

<script>
import { ref } from "vue";
import { useRouter } from "vue-router";
export default {
  setup() {
    const loading = ref(true);
    const router = useRouter();
    const svg = `
        <path class="path" d="
          M 30 15
          L 28 17
          M 25.61 25.61
          A 15 15, 0, 0, 1, 15 30
          A 15 15, 0, 1, 1, 27.99 7.5
          L 15 15
        " style="stroke-width: 4px; fill: rgba(0, 0, 0, 0)"/>
      `;
    const Info = [
      {
        info: "ENFJ 특징은 따뜻하고 적극적이며 책임감이 강하고 사교성이 풍부하고 동정심이 많습니다.",
      },
      {
        info: "ENTJ 유형 특징은 천성적으로 타고난 리더입니다.",
      },
      {
        info: "ENFP는 종종 분위기 메이커 역할을 하기도 하고 타인과 사회적, 정서적으로 깊은 유대 관계를 맺음으로써 행복을 느낍니다.",
      },
      {
        info: "ENTP 특징은 본인이 구상하는 바를 실현시키고 싶어 하는 기질이 강하며, 여기에 특유의 아웃사이더적인 성격까지 겹쳐 말 그대로 혁명가의 기질을 띠고 있습니다.",
      },
      {
        info: "ESFP는 순간의 흥분되는 감정이나 상황에 쉽게 빠져들며, 주위 사람들 역시 그런 느낌을 만끽하기를 원합니다.",
      },
      {
        info: "ESFJ 유형인 사교형 사람을 한마디로 정의 내리기는 어렵지만, 간단히 표현하자면 이들은 ‘인기쟁이’입니다.",
      },
      {
        info: "ESTP는 직설적이면서도 친근한 농담으로 주변 사람을 웃게 만드는 이들은 주변의 이목을 끄는 것을 좋아합니다.",
      },
      {
        info: "ESTJ는 정직하고 헌신적이며 위풍당당한 이들은 비록 험난한 가시밭길이라도 조언을 통하여 그들이 옳다고 생각하는 길로 사람들을 인도합니다.",
      },
      {
        info: "INFP는 최악의 상황이나 악한 사람에게서도 좋은 면만을 바라보며 긍정적이고 더 나은 상황을 만들고자 노력하는 진정한 이상주의자입니다.",
      },
      {
        info: "INFJ는 문제가 생겼을 때 본인이 적극적으로 해결하려고 하는 성향이 강합니다.",
      },
      {
        info: "INTP 특징은 조용하고 과묵하며 논리와 분석으로 문제를 해결하기를 좋아합니다.",
      },
      {
        info: "ISTP는 타인을 잘 도우며 그들의 경험을 다른 이들과 공유하는 것을 좋아합니다.",
      },
      {
        info: "ISFP는 말없이 다정하고 온화하며 사람들에게 친절하고 상대방을 잘 알게 될 때까지 내면의 모습이 잘 보이지 않습니다.",
      },
      {
        info: "ISFJ 특징 유형 성격의 소유자는 조용하고 차분하며 따뜻하고 친근합니다.",
      },
      {
        info: "ISTJ는 목표를 달성하기 위해 시간과 에너지를 허투루 쓰지 않으며, 이에 필요한 업무를 정확하고 신중하게 처리합니다.",
      },
      {
        info: "INTJ의 지식을 향한 갈증은 어릴 적부터 두드러지게 나타나는데, 때문에 어릴 때 ‘책벌레’라는 소리를 자주 듣습니다.",
      },
    ];
    const goHome = function () {
      router.push({ name: "HomeView" });
    };
    return { loading, svg, Info, goHome };
  },
};
</script>

<style>
.small {
  width: 200px;
  height: 250px;
  vertical-align: bottom;
}

.infoBox {
  display: inline-block;
  text-align: center;
  padding: 10px;
  height: 275px;
  width: 600px;
  border: 20px solid rgb(255, 91, 136);
  background-color: white;
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.12);
  border-radius: 1rem;
}

.el-loading-text {
  font-weight: bold;
}

.el-loading-spinner {
  color: black;
}
</style>
