<template>
  <el-container style="background-color: #fff4b8">
    <el-header>
      <img class="logo" src="@/assets/logo.png" />
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
        <span class="result"
          >당신의 MBTI는
          <span style="color: #e3842d"> {{ MBTI }} </span> 입니다.</span
        >
        <img class="small" src="@/assets/smallgreen.png" />
      </div>
      <el-footer style="margin-top: 50px">
        <el-button type="warning" plain size="large">확인</el-button>
      </el-footer>
    </el-card>
  </el-container>
</template>

<script>
import { ref } from "vue";
export default {
  setup() {
    const MBTI = ref("ENFP");
    return { MBTI };
  },
};
</script>

<style>
.logo {
  width: 250px;
}
.small {
  width: 200px;
  height: 250px;
  vertical-align: bottom;
}

.result {
  display: inline-block;
  text-align: center;
  font-weight: bold;
  font-size: x-large;
  height: 275px;
  line-height: 275px;
  width: 600px;
  border: 20px solid #e3842d;
  background-color: white;
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.12);
}
</style>
