<template>
  <el-container style="text-align: center">
    <el-header style="font-size: xx-large; margin-top: 50px"
      >자신의 MBTI를 선택해주세요.</el-header
    >
    <el-form :model="form" style="margin-bottom: 20px">
      <el-button-group>
        <el-space direction="vertical">
          <el-button
            id="00"
            @click="clickValue(0, 'I', 0)"
            style="background: #d6c415"
            >I</el-button
          >
          <el-button
            id="01"
            @click="clickValue(0, 'E', 1)"
            style="background: #632de3"
            >E</el-button
          >
        </el-space>
      </el-button-group>
      <el-button-group>
        <el-space direction="vertical">
          <el-button
            id="10"
            @click="clickValue(1, 'N', 0)"
            style="background: #cc3366"
            >N</el-button
          >
          <el-button
            id="11"
            @click="clickValue(1, 'S', 1)"
            style="background: #1612db"
            >S</el-button
          >
        </el-space>
      </el-button-group>
      <el-button-group>
        <el-space direction="vertical">
          <el-button
            id="20"
            @click="clickValue(2, 'F', 0)"
            style="background: #12a9db"
            >F</el-button
          >
          <el-button
            id="21"
            @click="clickValue(2, 'T', 1)"
            style="background: #e3842d"
            >T</el-button
          >
        </el-space>
      </el-button-group>
      <el-button-group>
        <el-space direction="vertical">
          <el-button
            id="30"
            @click="clickValue(3, 'J', 0)"
            style="background: #33c3cc"
            >J</el-button
          >
          <el-button
            id="31"
            @click="clickValue(3, 'P', 1)"
            style="background: #1cc240"
            >P</el-button
          >
        </el-space>
      </el-button-group>
    </el-form>
    <div>자신의 MBTI를 모르시나요?</div>
    <router-link to="/MBTITest">간단검사 하러가기</router-link>
    <el-footer>
      <el-button
        type="primary"
        plain
        size="large"
        @click="clickSetting"
        style="margin-top: 50px"
        >확인</el-button
      >
    </el-footer>
  </el-container>
</template>

<script>
import { reactive, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
export default {
  setup() {
    const router = useRouter();
    const store = useStore();
    const form = reactive({
      mbti: {},
      color: {},
    });

    const image = "@/assets/BG_Heart.gif";

    const clickSetting = function () {
      if (
        form.mbti[0] != null &&
        form.mbti[1] != null &&
        form.mbti[2] != null &&
        form.mbti[3] != null
      ) {
        const str = form.mbti[0] + form.mbti[1] + form.mbti[2] + form.mbti[3];
        console.log("선택 완료", str);
        router.push({ name: "ProfileSetting", params: { mbti: str } });
      } else {
        alert("MBTI를 선택해주세요!");
      }
    };

    const clickValue = function (idx, value, sel) {
      form.mbti[idx] = value;
      const i = Math.abs(sel - 1);
      const inactive = "" + idx + i;
      const active = "" + idx + sel;
      console.log(active);
      console.log(inactive);
      document.getElementById(active).style.color = "white";
      document.getElementById(inactive).style.color = "black";
      console.log(form.mbti[idx]);
    };

    onMounted(() => {
      const code = router.currentRoute._value.query.code;
      console.log(useStore());
      console.log(store);
      console.log(code);
      store
        .dispatch("accounts/checkCode", { code })
        .then(function (result) {
          console.log(result.data);
        })
        .catch(function (err) {
          alert(err);
        });
    });

    return { form, image, clickSetting, clickValue };
  },
};
</script>

<style>
.el-button-group .el-button {
  font-size: xx-large;
  width: 100px;
  height: 100px;
  margin: 10px;
  color: black;
}

.el-container {
  background-color: white;
  margin-right: 100px;
  margin-left: 100px;
  margin-top: 100px;
  padding-bottom: 100px;
}
</style>
