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

    router.push({ path: "/" });
  },
};
</script>

<style></style>
