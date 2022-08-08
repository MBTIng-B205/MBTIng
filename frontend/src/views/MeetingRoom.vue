<template>
  <el-container>
    <div class="cam">
      <div class="mbtiinfo">
        <div class="mbtic">
          <img src="@/assets/main-green.png" alt="main-green" />
        </div>
        <div class="mbti">
          <h1 style="font-size: xxx-large; color: white">ESTP</h1>
        </div>
      </div>
    </div>
  </el-container>
  <!-- controller -->
  <div class="controller">
    <div class="left">
      <el-button type="success" :icon="BellFilled" circle />
      <el-button type="danger" :icon="BellFilled" circle />
      <el-button type="info" :icon="QuestionFilled" circle />
      <span>라이트를 눌러 화상여부를 선택하세요</span>
    </div>
    <div class="timer"></div>
    <div class="right">
      <el-button type="danger" :icon="WarningFilled" round>신고하기</el-button>
      <el-button type="info" :icon="ChatDotSquare" round>채팅</el-button>
      <el-button type="danger" :icon="Close" round>나가기</el-button>
    </div>
  </div>
</template>

<script>
import {
  Check,
  QuestionFilled,
  BellFilled,
  WarningFilled,
  ChatDotSquare,
  Close,
} from "@element-plus/icons-vue";

export default {
  setup() {
    return {
      Check,
      QuestionFilled,
      BellFilled,
      WarningFilled,
      ChatDotSquare,
      Close,
    };
  },
};
</script>

<style scoped>
.el-container {
  margin-top: 40px;
  display: flex;
  justify-content: center;
}
.cam {
  display: flex;
  background-color: #7d7d7d;
  width: 80%;
  height: 600px;
  margin-bottom: 34px;
  justify-content: center;
  align-items: center;
}
.mbtiinfo {
  background-color: #908d8d;
  border-radius: 50%;
  width: 500px;
  height: 500px;
  align-items: center;
  display: flex;
  position: relative;
}
.mbtic {
  display: flex;
}
.mbti {
  left: 39%;
  position: absolute;
}

.controller {
  background-color: #fff4b8;
  height: 100px;
  display: flex;
  justify-content: center;
}
.timer {
  background-color: deeppink;
  height: 100px;
  width: 300px;
  margin-right: 10px;
  margin-left: 10px;
}
.right {
  display: flex;
  justify-content: center;
  align-items: center;
}
.left {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
