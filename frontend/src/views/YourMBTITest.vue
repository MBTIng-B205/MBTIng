<template>
  <el-container style="background-color: #fadce1">
    <el-header>
      <img
        class="logo"
        @click="goHome"
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
        Q: "서로 호감이 있는 상태에서 나의 이상형은?",
        U: "나에게 먼저 다가와서 호감을 표현하는 사람",
        D: "너무 다가오기보단 내가 다가가면 표현하는 사람",
      },
      {
        type: 3,
        Q: "놀러 가기로 했을 때 나의 이상형은?",
        U: "하루 전 날 미리 준비하고 짐을 싸두며 필요한 리스트 작성하는 사람",
        D: "미리 챙기지 않아 한 두개 놓고 왔지만 웃으며 사러가자고 하는 사람",
      },
      {
        type: 0,
        Q: "나의 이상형이 친구들과 놀 때 모습은?",
        U: "많은 친구들과 소통하며 활발하게 노는 사람",
        D: "소수의 친구들과 소소하게 즐기는 사람",
      },
      {
        type: 1,
        Q: "내가 원하는 이상형의 사람은?",
        U: "창의적이고 독창성이 있는 사람",
        D: "참을성이 있고 꾸준한 사람",
      },
      {
        type: 3,
        Q: "함께 여행을 가기로 했을 때 나의 이상형은?",
        U: "가고 싶은 곳을 미리 찾아보고 정리하는 사람",
        D: "일단 여행 떠나자! 가서 결정하는 사람",
      },
      {
        type: 0,
        Q: "내 이상형의 술자리 포지션은?",
        U: "분위기를 주도하며 웃음을 주는 타입",
        D: "잘 웃어주며 리액션을 하는 타입",
      },
      {
        type: 2,
        Q: "귀찮은 부탁을 받았을 때 나의 이상형은?",
        U: "거절하지 못하고 본인이 한다",
        D: "할 수 없다고 딱 말하며 거절한다",
      },
      {
        type: 1,
        Q: "내 연인은 이랬으면 좋겠다.",
        U: "미래 발전 가능성이 높은 사람",
        D: "지금, 현재를 열심히 잘 사는 사람",
      },
      {
        type: 2,
        Q: "다툼이 생긴 상황에서 나의 이상형은?",
        U: "일단 감정적으로 벅차 올라 나중에 다시 얘기하자는 사람",
        D: "논리적으로 무엇이 잘못된 건지 말하는 사람",
      },
      {
        type: 1,
        Q: "나의 이상형이 맛집을 발견했을 때의 모습은?",
        U: "맛집스러워 보이는 외관을 보고 들어간다",
        D: "여기가 어떤지 리뷰를 찾아보고 들어간다",
      },
      {
        type: 3,
        Q: "둘 중 한 사람을 만나야 한다면?",
        U: "철저한 집중력과 추진력이 있지만 많이 고집스러움",
        D: "즉흥적이고 행동력이 낮으며 계획이 없어 자유로움",
      },
      {
        type: 2,
        Q: "고민상담을 하는데 나의 잘못이긴 하다. 그때 나의 이상형은?",
        U: "직접적으로 말하면 그럴까봐 돌려서 말해주는 사람",
        D: "나의 잘못된 점을 직접적으로 설명해주는 사람",
      },
    ];
    const upAnswer = function () {
      result[quiz[cnt.value].type] += 1;
      if (cnt.value != 11) {
        cnt.value++;
      } else {
        getMbti();
      }
    };

    const downAnswer = function () {
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
      router.push({ name: "YourMBTIResult", params: { mbti: mbti.value } });
    };
    const goHome = function () {
      router.push({ name: "HomeView" });
    };

    return {
      cnt,
      percent,
      quiz,
      result,
      upAnswer,
      downAnswer,
      goHome,
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
  font-family: "Dalseo";
  border-radius: 1rem;
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
