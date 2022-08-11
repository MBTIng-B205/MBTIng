<template>
  <main-header />

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
import { useStore } from "vuex";
import { computed, reactive } from "@vue/runtime-core";
import MainHeader from "@/components/main-header.vue";
export default {
  components: { MainHeader },
  setup() {
    const store = useStore();
    const state = reactive({
      memberinfo: computed(() => store.getters["accounts/getMember"]),
    });

    return {
      state,
    };
  },
};
</script>

<style scoped>
/* 공통으로 적용되어야할 css */
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

.link {
  text-decoration: none;
}
</style>
