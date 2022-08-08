<template>
  <el-container style="background-color: #fff4b8">
    <el-header>
      <img class="logo" src="@/assets/logo.png" />
    </el-header>
    <el-card style="padding: 0px">
      <div class="question">
        갑자기 휴가가 생겼습니다. 당신은 무엇을 하고 싶습니까?
      </div>

      <el-row style="flex-direction: row; justify-content: space-evenly">
        <el-button type="success" size="large">Yes</el-button>
        <el-button type="danger" size="large">No</el-button>
      </el-row>
      <el-row
        class="row"
        style="flex-direction: row; margin: 20px; margin-top: 50px"
      >
        <el-col :span="4" />
        <el-col :span="16"
          ><el-progress :percentage="10">{{ cnt }}/12</el-progress></el-col
        >
        <el-col :span="4" />
      </el-row>
    </el-card>
  </el-container>
</template>

<script>
import { ArrowLeftBold, ArrowRightBold } from "@element-plus/icons-vue";
// import { reactive } from "vue";
export default {
  setup() {
    const cnt = 1;

    const quiz = [
      {
        type: 0,
        Q: "소개팅에서 첫 만남에 어색할 때 나는?",
        U: "준비해온 멘트들을 건네며 대화에 시동을 건다.",
        D: "미소만 지으며 상대가 말할 때까지 기다린다.",
      },
    ];
    return { cnt, quiz, ArrowLeftBold, ArrowRightBold };
  },
};
</script>

<style>
.question {
  text-align: center;
  font-weight: bold;
  font-size: large;
  height: 275px;
  line-height: 275px;
  margin: 50px;
  border: 20px solid #e3842d;
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.12);
}
.logo {
  width: 250px;
}
</style>
