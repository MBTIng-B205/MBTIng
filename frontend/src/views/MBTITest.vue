<template>
  <el-container style="background-color: #fadce1">
    <el-header>
      <img
        class="logo"
        src="@/assets/logo.png"
        alt="logo"
        style="cursor: pointer"
      />
    </el-header>
    <el-card style="padding: 0px">
      <div class="question">
        {{ quiz[cnt].Q }}
      </div>

      <el-row>
        <button
          class="answerBtn"
          type="info"
          size="large"
          plain
          @click="upAnswer"
        >
          {{ quiz[cnt].U }}
        </button>
        <button
          style="margin-left: 0; margin-top: 50px"
          class="answerBtn"
          type="success"
          size="large"
          plain
          @click="downAnswer"
        >
          {{ quiz[cnt].D }}
        </button>
      </el-row>
      <el-row
        class="row"
        style="flex-direction: row; margin: 20px; margin-top: 100px"
      >
        <el-col :span="4" />
        <el-col :span="16"
          ><el-progress :percentage="percent" status="exception"
            >{{ cnt + 1 }}/12</el-progress
          ></el-col
        >
        <el-col :span="4" />
      </el-row>
    </el-card>
  </el-container>
</template>

<script>
import { ArrowLeftBold, ArrowRightBold } from "@element-plus/icons-vue";
import { useRouter } from "vue-router";
import { ref, computed } from "vue";

export default {
  setup() {
    const router = useRouter();
    const cnt = ref(0);
    const percent = computed(() => (100 / 12) * (cnt.value + 1));
    const result = [0, 0, 0, 0];
    const mbti = ref("");
    const quiz = [
      {
        type: 0,
        Q: "소개팅에서 첫 만남에 어색할 때 나는?",
        U: "준비해온 멘트들을 건네며 대화에 시동을 건다",
        D: "미소만 지으며 상대가 말할 때까지 기다린다",
      },
      {
        type: 3,
        Q: "연인과의 여행을 갈 때 나는?",
        U: "일정을 세세하게 정리하고 계획을 세워 여행한다",
        D: "비행기 표만 끊고 그날의 기분에 따라 자유롭게 여행한다",
      },
      {
        type: 0,
        Q: "데이트가 없는 주말에 나는?",
        U: "친구들과 약속을 잡는다",
        D: "혼자 집에서 쉬면서 논다",
      },
      {
        type: 1,
        Q: "연인이 친구들과 노는데 연락이 없을 때 나는?",
        U: "왜 갑자기 연락이 안되지...? 하며 불안해한다",
        D: "재밌게 놀고 있어 연락이 안되나? 하며 내 할일을 한다",
      },
      {
        type: 3,
        Q: "연인과의 데이트룩을 고를 때 나는?",
        U: "전 날에 머리부터 발 끝까지 뭐 입을지 미리 준비한다",
        D: "나가기 직전 마음에 드는 옷을 입는다",
      },
      {
        type: 0,
        Q: "데이트 중 길에서 연인의 친구를 만났다면 나는?",
        U: "자연스럽게 인사하며 같이 대화한다",
        D: "어색하게 웃으면서 대화가 끝나기를 기다린다",
      },
      {
        type: 2,
        Q: "연인이 힘든 상황에 처했을 때의 나는?",
        U: "연인의 감정에 공감하고 정신적으로 지지한다",
        D: "상황을 해결하기 위해 현실적인 해결책을 제시한다",
      },
      {
        type: 1,
        Q: "연인과 함께 여행을 가기 전 날 밤 나는?",
        U: "여행지에서 일어날 사건들에 대해 온갖 상상을 하다가 잠든다",
        D: "짐을 꾸린 후 바로 잠에 든다",
      },
      {
        type: 2,
        Q: "연인이 예상치 못한 깜짝 선물을 한다면 나는?",
        U: "예상치 못한 선물이라 더 기쁘다",
        D: "오늘이 무슨 기념일이었나? 하고 생각해본다",
      },
      {
        type: 1,
        Q: "연인과 영화를 보고 나서 대화할 때 나는?",
        U: "내가 주인공이라면 어떻게 할지 얘기한다",
        D: "영화의 줄거리에 대한 평을 한다",
      },
      {
        type: 3,
        Q: "연인과 놀이동산을 갔을 때 나는?",
        U: "지도를 보며 무엇을 탈지 계획을 세운다",
        D: "재밌어 보이는 것부터 탄다",
      },
      {
        type: 2,
        Q: "연인이 다퉜을 때의 나는?",
        U: "속상해서 눈물부터 난다",
        D: "논리적으로 조목조목 따지며 화 낸다",
      },
    ];
    const upAnswer = function () {
      console.log(percent.value);
      result[quiz[cnt.value].type] += 1;
      if (cnt.value != 11) {
        cnt.value++;
      } else {
        getMbti();
      }
    };

    const downAnswer = function () {
      console.log(percent.value);
      result[quiz[cnt.value].type] -= 1;
      if (cnt.value != 11) {
        cnt.value++;
      } else {
        getMbti();
      }
    };

    const getMbti = function () {
      if (result[0] > 0) {
        mbti.value = "E";
      } else {
        mbti.value = "I";
      }

      if (result[1] > 0) {
        mbti.value += "N";
      } else {
        mbti.value += "S";
      }

      if (result[2] > 0) {
        mbti.value += "F";
      } else {
        mbti.value += "T";
      }

      if (result[3] > 0) {
        mbti.value += "J";
      } else {
        mbti.value += "P";
      }

      console.log("mbti : ", mbti.value);
      router.push({ name: "MBTIResult", params: { mbti: mbti.value } });
    };

    return {
      cnt,
      percent,
      quiz,
      result,
      upAnswer,
      downAnswer,
      ArrowLeftBold,
      ArrowRightBold,
    };
  },
};
</script>

<style scoped>
.el-container {
  height: 100vh;
}

.question {
  text-align: center;
  font-weight: bold;
  font-size: 30px;
  height: 150px;
  line-height: 150px;
  margin: 50px;
  border: 10px solid rgb(255, 91, 136);
  border-radius: 1rem;
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.12);
  font-family: "Dalseo";
}

.answerBtn {
  width: 700px;
  font-size: 25px;
  background-color: white;
  color: rgb(255, 91, 136);
  padding: 5px;
  border: 1px solid rgb(255, 91, 136);
  border-radius: 1rem;
  font-family: "Dalseo";
}

.answerBtn:active {
  background-color: rgb(255, 91, 136);
  color: white;
}

.answerBtn:hover {
  background-color: rgb(255, 91, 136);
  color: white;
}
</style>
