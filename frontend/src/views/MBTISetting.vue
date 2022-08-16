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
    <el-card style="text-align: center">
      <el-header style="font-size: xx-large"
        >자신의 MBTI를 선택해주세요.</el-header
      >
      <el-form :model="form" style="margin-bottom: 20px">
        <el-button-group>
          <el-space direction="vertical">
            <el-button
              id="00"
              @click="clickValue(0, 'I', 0)"
              style="background: #f1f1b3"
              >I</el-button
            >
            <el-button
              id="01"
              @click="clickValue(0, 'E', 1)"
              style="background: #bde4d7"
              >E</el-button
            >
          </el-space>
        </el-button-group>
        <el-button-group>
          <el-space direction="vertical">
            <el-button
              id="10"
              @click="clickValue(1, 'N', 0)"
              style="background: #facdca"
              >N</el-button
            >
            <el-button
              id="11"
              @click="clickValue(1, 'S', 1)"
              style="background: #c0e8ea"
              >S</el-button
            >
          </el-space>
        </el-button-group>
        <el-button-group>
          <el-space direction="vertical">
            <el-button
              id="20"
              @click="clickValue(2, 'F', 0)"
              style="background: #efc7d6"
              >F</el-button
            >
            <el-button
              id="21"
              @click="clickValue(2, 'T', 1)"
              style="background: #e2d9e7"
              >T</el-button
            >
          </el-space>
        </el-button-group>
        <el-button-group>
          <el-space direction="vertical">
            <el-button
              id="30"
              @click="clickValue(3, 'J', 0)"
              style="background: #d8dceb"
              >J</el-button
            >
            <el-button
              id="31"
              @click="clickValue(3, 'P', 1)"
              style="background: #cce2ee"
              >P</el-button
            >
          </el-space>
        </el-button-group>
      </el-form>
      <div>자신의 MBTI를 모르시나요?</div>
      <router-link to="/mbtitest">간단검사 하러가기</router-link>
      <el-footer>
        <el-button
          type="danger"
          plain
          size="large"
          @click="clickSetting"
          style="margin-top: 50px"
          round
          >확인</el-button
        >
      </el-footer>
    </el-card>
  </el-container>
  <el-dialog v-model="form.alertDialogVisible" width="30%" center top="250px">
    <el-row style="top: 12px; font-size: 16.5px">{{ form.alertMsg }}</el-row>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="danger" round @click="form.alertDialogVisible = false"
          >확인</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { reactive, computed } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
export default {
  setup() {
    const router = useRouter();
    const store = useStore();
    const form = reactive({
      mbti: {},
      color: {},
      memberinfo: computed(() => store.getters["accounts/getMember"]),
      alertMsg: "",
      alertDialogVisible: false,
    });

    const alertDialog = function (message) {
      form.alertMsg = message;
      form.alertDialogVisible = true;
    };
    const image = "@/assets/BG_Heart.gif";

    const clickSetting = function () {
      if (
        form.mbti[0] != null &&
        form.mbti[1] != null &&
        form.mbti[2] != null &&
        form.mbti[3] != null
      ) {
        const str = form.mbti[0] + form.mbti[1] + form.mbti[2] + form.mbti[3];
        // console.log("선택 완료", str);
        // console.log("form", form.memberinfo);
        form.memberinfo.mbti = str;
        store.commit("accounts/SET_MEMBER_INFO", form.memberinfo);
        router.push({ name: "ProfileSetting" });
      } else {
        alertDialog("MBTI를 선택해주세요!");
      }
    };

    const clickValue = function (idx, value, sel) {
      form.mbti[idx] = value;
      const i = Math.abs(sel - 1);
      const inactive = "" + idx + i;
      const active = "" + idx + sel;
      // console.log(active);
      // console.log(inactive);
      document.getElementById(active).style.color = "white";
      document.getElementById(inactive).style.color = "black";
      // console.log(form.mbti[idx]);
    };

    return { form, image, clickSetting, clickValue, alertDialog };
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
.el-card {
  padding: 50px;
  margin: 50px;
  --el-color-primary: #f56c6c;
  --el-color-primary-light-3: #f89898;
  --el-color-primary-light-5: #fab6b6;
  --el-color-primary-light-7: #fcd3d3;
  --el-color-primary-light-8: #fde2e2;
  --el-color-primary-light-9: #fef0f0;
  --el-color-primary-dark-2: #c45656;
  --el-select-input-focus-border-color: #f56c6c;
  --el-menu-hover-text-color: #f56c6c;
}
</style>
