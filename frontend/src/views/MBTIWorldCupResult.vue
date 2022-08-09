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
        <img
          class="small"
          style="width: 250px; height: 250px"
          src="@/assets/smallpurple.png"
        />
        <span class="result"
          >MBTI 월드컵 우승자는
          <span style="color: deeppink"> {{ $route.params.mbti }} </span>
          입니다!</span
        >
        <img class="small" src="@/assets/smallorange.png" />
      </div>
      <el-footer style="margin-top: 50px">
        <el-button type="warning" plain size="large" @click="goHome"
          >홈으로</el-button
        >
      </el-footer>
    </el-card>
  </el-container>
</template>

<script>
import { useRouter } from "vue-router";
export default {
  setup() {
    const router = useRouter();
    const goHome = function () {
      router.push({ name: "HomeView" });
    };
    return { goHome };
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
  border: 20px solid deeppink;
  background-color: white;
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.12);
}
</style>
