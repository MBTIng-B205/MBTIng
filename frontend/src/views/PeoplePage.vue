<template>
  <main-header />

  <el-container style="height: 654px">
    <el-aside width="240px" height="100%"
      ><el-menu background-color="white">
        <el-menu-item>
          <router-link
            :to="{ name: 'friend' }"
            class="link"
            :class="{ linkActive: state.friendActive }"
            @click="
              state.friendActive = true;
              state.messageActive = 0;
            "
            >친구 리스트</router-link
          ></el-menu-item
        >
        <el-sub-menu
          ><template #title
            ><span
              class="link"
              :class="{ linkActive: !state.friendActive }"
              @click="state.friendActive = false"
              >쪽지 리스트</span
            ></template
          >
          <el-menu-item-group>
            <el-menu-item>
              <router-link
                :to="{ name: 'receiveMessage' }"
                class="link"
                :class="{ linkActive: state.messageActive == 1 }"
                @click="
                  state.friendActive = false;
                  state.messageActive = 1;
                "
                >받은 쪽지함</router-link
              ></el-menu-item
            >
            <el-menu-item>
              <router-link
                :to="{ name: 'sendMessage' }"
                class="link"
                :class="{ linkActive: state.messageActive == 2 }"
                @click="
                  state.friendActive = false;
                  state.messageActive = 2;
                "
                >보낸 쪽지함</router-link
              ></el-menu-item
            ></el-menu-item-group
          ></el-sub-menu
        ></el-menu
      ></el-aside
    >
    <el-main style="padding: 0; height: 620px"><router-view /></el-main>
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
      friendActive: true,
      messageActive: 0,
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
  width: 200px;
  color: black;
  font-size: medium;
  padding: 10px;
}

.link:hover {
  color: palevioletred;
}

.link:active {
  color: palevioletred;
}

.linkActive {
  color: palevioletred;
}

.el-select-dropdown__item.selected {
  color: palevioletred;
}

.el-menu-item:hover {
  background-color: white;
}

.el-main {
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
