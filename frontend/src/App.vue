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
  height: 864px;
  width: 100%;
  background-image: url("@/assets/BG_Heart_Bar.gif");
}
</style>
