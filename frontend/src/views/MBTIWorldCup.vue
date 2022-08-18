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
    <el-row
      ><div class="title" style="margin-top: 35px">
        내가 힘들 때 필요한 말은?
      </div></el-row
    >
    <el-row style="flex-direction: row; justify-content: center">
      <el-card
        :class="{ activeCard: active == 'left' }"
        style="padding: 0px; border-radius: 1rem"
        @click="game(left)"
        @mouseover="active = 'left'"
        @mouseout="active = ''"
      >
        <div class="card">
          <img :src="card[left].img" />
        </div>
      </el-card>
      <div class="vs">VS</div>
      <el-card
        :class="{ activeCard: active == 'right' }"
        style="padding: 0px; border-radius: 1rem"
        @click="game(right)"
        @mouseover="active = 'right'"
        @mouseout="active = ''"
      >
        <div class="card">
          <img :src="card[right].img" />
        </div>
      </el-card>
    </el-row>
  </el-container>
  <el-dialog v-model="state.alertDialogVisible" width="30%" center top="250px">
    <el-row style="top: 12px; font-size: 16.5px">{{ state.alertMsg }}</el-row>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="danger" round @click="state.alertDialogVisible = false"
          >확인</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { useRouter } from "vue-router";
import { onMounted, reactive, ref } from "vue";

export default {
  setup() {
    const router = useRouter();
    let size = 8;
    const left = ref(0);
    const right = ref(0);
    const active = ref("");
    let list = [];
    const card = [
      { mbti: "INFJ", img: require("@/assets/infj.png") },
      { mbti: "INFP", img: require("@/assets/infp.png") },
      { mbti: "ENFP", img: require("@/assets/enfp.png") },
      { mbti: "ENFJ", img: require("@/assets/enfj.png") },
      { mbti: "INTJ", img: require("@/assets/intj.png") },
      { mbti: "INTP", img: require("@/assets/intp.png") },
      { mbti: "ENTP", img: require("@/assets/entp.png") },
      { mbti: "ENTJ", img: require("@/assets/entj.png") },
      { mbti: "ISFP", img: require("@/assets/isfp.png") },
      { mbti: "ESFP", img: require("@/assets/esfp.png") },
      { mbti: "ISTP", img: require("@/assets/istp.png") },
      { mbti: "ESTP", img: require("@/assets/estp.png") },
      { mbti: "ISTJ", img: require("@/assets/istj.png") },
      { mbti: "ESTJ", img: require("@/assets/estj.png") },
      { mbti: "ISFJ", img: require("@/assets/isfj.png") },
      { mbti: "ESFJ", img: require("@/assets/esfj.png") },
    ];

    onMounted(() => {
      for (let index = 0; index < 16; index++) {
        list.push(index);
      }
      list.sort(() => Math.random() - 0.5);
      left.value = list.shift();
      right.value = list.shift();
    });
    const state = reactive({
      alertMsg: "",
      alertDialogVisible: false,
    });

    const alertDialog = function (message) {
      state.alertMsg = message;
      state.alertDialogVisible = true;
    };

    const game = function (i) {
      list.push(i);
      if (list.length == 1) {
        // 최종 결과
        router.push({
          name: "MBTIWorldCupResult",
          params: { mbti: card[i].mbti },
        });
      } else if (list.length == size) {
        // 다음 라운드 넘어갈 때 섞기
        if (size === 2) {
          alertDialog("결승전 시작!");
        } else {
          alertDialog(size + "강전 시작!");
        }
        list.sort(() => Math.random() - 0.5);
        size = size / 2;
        left.value = list.shift();
        right.value = list.shift();
      } else {
        left.value = list.shift();
        right.value = list.shift();
      }
    };

    const goHome = function () {
      router.push({ name: "HomeView" });
    };

    return {
      state,
      alertDialog,
      left,
      right,
      active,
      card,
      game,
      goHome,
    };
  },
};
</script>

<style>
.el-container {
  height: 100vh;
}
.title {
  font-size: xx-large;
  background-color: white;
  width: 1100px;
  text-align: center;
  color: rgb(255, 91, 136);
  border-radius: 30px;
  border: solid rgb(255, 91, 136);
}
.card {
  cursor: pointer;
  text-align: center;
  font-weight: bold;
  font-size: large;
  width: 400px;
  height: 450px;
  line-height: 350px;
  border: 10px solid rgb(255, 91, 136);
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.12);
}
.activeCard .el-card__body {
  background-color: rgb(255, 91, 136);
  border-radius: 1rem;
}
.vs {
  font-weight: bold;
  font-size: xx-large;
  color: rgb(255, 91, 136);
}
</style>
