<template>
  <el-row style="margin-bottom: 30px">
    <el-col :span="4" style="text-align: center; margin: auto">
      <el-button :icon="ArrowLeftBold" circle></el-button>
    </el-col>
    <el-col :span="16">
      <el-card>
        <el-header style="background: #632de3"></el-header>
        <div style="text-align: center; height: 200px; line-height: 200px">
          갑자기 휴가가 생겼습니다. 당신은 무엇을 하고 싶습니까?
        </div>
      </el-card>
    </el-col>
    <el-col :span="4" style="text-align: center; margin: auto">
      <el-button :icon="ArrowRightBold" circle></el-button>
    </el-col>
  </el-row>
  <div style="text-align: center">
    <el-button type="success">Yes</el-button>
    <el-button type="danger">No</el-button>
  </div>
  <div style="margin-top: 50px">
    <el-row>
      <el-col :span="4" />
      <el-col :span="16"
        ><el-progress :percentage="10">{{ cnt }}/10</el-progress></el-col
      >
      <el-col :span="4" />
    </el-row>
  </div>
</template>

<script>
import { ArrowLeftBold, ArrowRightBold } from "@element-plus/icons-vue";
// import { reactive } from "vue";
export default {
  setup() {
    const cnt = 1;
    return { cnt, ArrowLeftBold, ArrowRightBold };
  },
};
</script>

<style></style>
