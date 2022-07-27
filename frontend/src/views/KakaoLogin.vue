<template>
  <div>
    <div class="btn-wrap">
      <img
        class="loginbtn"
        src="@/assets/ButtonY_off.png"
        alt=""
        style="height: 100px; width: auto"
      />
      <div>
        <a
          class="logintext"
          href="https://kauth.kakao.com/oauth/authorize?client_id=ebb8bb50d4cb227cf989335c827681e5&redirect_uri=http://localhost:80/mbtisetting&response_type=code"
          >카카오로그인</a
        >
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {},
  setup() {
    const Login = () => {
      // 카카오톡 로그인 화면 전환
      window.location.replace(
        "https://kauth.kakao.com/oauth/authorize?client_id=ebb8bb50d4cb227cf989335c827681e5&redirect_uri=http://localhost:80/mbtisetting&response_type=code"
      );
    };

    return {
      Login,
    };
  },
};
</script>

<style>
.btn-wrap {
  width: 100%;
  margin: 10px auto;
  position: relative;
}
.login img {
  width: 100%;
  vertical-align: middle;
}
.logintext {
  position: absolute;
  top: 20%;
  left: 55%;
  width: 100%;
  transform: translate(-50%, -50%);
}
</style>
