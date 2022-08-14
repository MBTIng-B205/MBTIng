<template>
  <MBTISetting :member="member" />
</template>

<script>
import { useStore } from "vuex";
import { onMounted } from "vue";
import { useRouter } from "vue-router";
export default {
  setup() {
    const store = useStore();
    const router = useRouter();
    onMounted(() => {
      const code = router.currentRoute._value.query.code;
      // console.log(useStore());
      // console.log(store);
      // console.log(code);

      store
        .dispatch("accounts/checkCode", { code })
        .then(function (result) {
          //store.commit("SET_MEMBER_INFO", result.data.member);
          console.log("member data check", result.data.body);
          if (result.data.body == "no email") {
            alert("email 동의를 해주셔야합니다.");
            location.href = `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.VUE_APP_KAKAO_CLIENT_ID}&redirect_uri=${process.env.VUE_APP_KAKAO_LOGIN_REDIRECT_URI}&response_type=code&scope=account_email`;
            return;
          }
          sessionStorage.setItem("access-token", result.data.body.jwt);
          console.log(result.data.body.member);
          store.commit("accounts/SET_MEMBER_INFO", result.data.body.member);
          if (result.data.body.visited === true) {
            store.dispatch("accounts/getMemberinfo").then(function (res) {
              console.log("res data", res);
              store.commit("accounts/SET_MEMBER_INFO", res.data.body);
              console.log(res.data.body);
            });
            router.push({ path: "/" });
          } else {
            router.push({ path: "/mbtisetting" });
          }
        })
        .catch(function (err) {
          alert(err);
        });
    });
    return {};
  },
};
</script>

<style></style>
