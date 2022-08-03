<template>
  <div class="headerbar">
    <img class="logo" src="@/assets/logo.png" />
    <img class="profile" src="@/assets/profile.png" />
  </div>
</template>

<script>
export default {};
</script>

<style>
.headerbar {
  background-color: #fff4b8;
}

.logo {
  width: 200px;
  height: 100px;
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
</style>
