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
.mainButton {
  cursor: pointer;
  padding: 12px 19px;
  height: 40px;
  font-size: 14px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  line-height: 1;
  align-items: center;
  background-color: #ffffff;
  color: #606266;
  vertical-align: middle;
}

.mainButton:hover {
  color: palevioletred;
  background-color: #fbeff1;
  border-color: #fbeff1;
}
</style>
