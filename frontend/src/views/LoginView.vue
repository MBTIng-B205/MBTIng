<template>
  <MBTISetting :member="member" />
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
import { useStore } from "vuex";
import { onMounted, reactive } from "vue";
import { useRouter } from "vue-router";
export default {
  setup() {
    const store = useStore();
    const router = useRouter();
    const state = reactive({
      alertMsg: "",
      alertDialogVisible: false,
    });
    onMounted(() => {
      const code = router.currentRoute._value.query.code;

      store
        .dispatch("accounts/checkCode", { code })
        .then(function (result) {
          if (result.data.body == "no email") {
            alertDialog("email 동의를 해주셔야합니다.");
            location.href = `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.VUE_APP_KAKAO_CLIENT_ID}&redirect_uri=${process.env.VUE_APP_KAKAO_LOGIN_REDIRECT_URI}&response_type=code&scope=account_email`;
            return;
          }
          sessionStorage.setItem("access-token", result.data.body.jwt);
          store.commit("accounts/SET_MEMBER_INFO", result.data.body.member);
          if (result.data.body.visited === true) {
            store.dispatch("accounts/getMemberinfo").then(function (res) {
              store.commit("accounts/SET_MEMBER_INFO", res.data.body);
            });
            router.push({ path: "/" });
          } else {
            router.push({ path: "/mbtisetting" });
          }
        })
        .catch(function (err) {
          alertDialog(err);
        });
    });
    const alertDialog = function (message) {
      state.alertMsg = message;
      state.alertDialogVisible = true;
    };
    return {
      state,
      alertDialog,
    };
  },
};
</script>

<style></style>
