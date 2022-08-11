<template>
  <div class="background">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "App",

  components: {},
};
</script>

<style>
/* .background {
  height: 864px;
  width: 100%;
  background-image: url("@/assets/BG_Heart_Bar.gif");
} */
@font-face {
  font-family: "Dalseo";
  src: url("./font/DalseoHealingMedium.woff") format("woff");
}
@font-face {
  font-family: "DalseoBold";
  src: url("./font/DalseoHealingBold.woff") format("woff");
}

* {
  font-family: "DalseoBold";
}
body {
  height: 100vh;
}
.el-container {
  height: 100vh;
}
.logo {
  width: 200px;
  margin-left: 20px;
}
</style>
