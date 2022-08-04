<template>
  <div></div>
</template>

<script>
import { useStore } from "vuex";
import { useRouter } from "vue-router";
export default {
  setup() {
    const store = useStore();
    const router = useRouter();
    sessionStorage.removeItem("access-token");
    store.commit("accounts/SET_MEMBER_INFO", null);
    console.log(store.state.member);

    router.push({ path: "/" });
  },
};
</script>

<style></style>
