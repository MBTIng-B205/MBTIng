<template>
  <div class="background">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "App",

  components: {},

  data: () => ({
    //
  }),
};
</script>

<style>
.background {
  height: 1024px;
  width: 1440px;
  background-image: url("@/assets/BG_Heart.gif");
}
</style>
