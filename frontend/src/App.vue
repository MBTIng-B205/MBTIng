<template>
  <el-button @click="onOpenLoginDialog">Login</el-button>
  <login-dialog
    :open="loginDialogOpen"
    @closeLoginDialog="onCloseLoginDialog"
  />
</template>

<script>
import LoginDialog from "./components/login-dialog";

export default {
  components: {
    LoginDialog,
  },
  data() {
    return {
      loginDialogOpen: false,
    };
  },

  methods: {
    onOpenLoginDialog() {
      this.loginDialogOpen = true;
    },
    onCloseLoginDialog() {
      this.loginDialogOpen = false;
    },
  },
};
</script>
