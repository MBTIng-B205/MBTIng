<template>
  <el-container style="background-color: #fff4b8">
    <el-header>
      <img @click="goHome" class="logo" src="@/assets/logo.png" />
      <el-button
        style="float: right; vertical-align: middle"
        type="danger"
        round
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
            {{ Info[0].info }}
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
        info: "INFJ는 문제가 생겼을 때 본인이 적극적으로 해결하려고 하는 성향이 강합니다.",
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
  border: 20px solid #e3842d;
  background-color: white;
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.12);
}

.el-loading-text {
  font-weight: bold;
}

.el-loading-spinner {
  color: black;
}
</style>
