<template>
  <el-header style="background-color: #fff4b8; height: 100px">
    <img @click="goHome" class="logo" src="@/assets/logo.png" />
    <img class="profile" src="@/assets/profile.png"
  /></el-header>
  <el-container>
    <el-aside width="240px" height="100%"
      ><el-menu>
        <el-menu-item>
          <router-link :to="{ name: 'friend' }" class="link"
            >친구 리스트</router-link
          ></el-menu-item
        >
        <el-sub-menu
          ><template #title><span class="link">쪽지 리스트</span></template>
          <el-menu-item-group>
            <el-menu-item>
              <router-link :to="{ name: 'receiveMessage' }" class="link"
                >받은 쪽지함</router-link
              ></el-menu-item
            >
            <el-menu-item>
              <router-link :to="{ name: 'sendMessage' }" class="link"
                >보낸 쪽지함</router-link
              ></el-menu-item
            ></el-menu-item-group
          ></el-sub-menu
        ></el-menu
      ></el-aside
    >
    <el-main style="padding: 0"><router-view /></el-main>
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

<style scoped>
/* 공통으로 적용되어야할 css */
html,
body {
  height: 100vh;
}
.el-container {
  margin: 0;
  height: 100vh;
  width: 100%;
  padding-bottom: 0;
}

.el-aside {
  background-color: #f8f8f8;
  height: 100%;
  display: inline-block;
  vertical-align: top;
}
.el-main {
  height: 100%;
  display: inline-block;
  color: #333;
  text-align: center;
  overflow-x: hidden;
}

.logo {
  width: 80px;
  height: 80px;
}

.profile {
  float: right;
  margin-right: 50px;
  width: 100px;
  height: 100px;
  border: 1px solid black;
  border-radius: 100%;
  background-color: white;
}
.link {
  text-decoration: none;
}
</style>
