<template>
  <el-container style="background-color: #fff4b8">
    <el-header>
      <img @click="goHome" class="logo" src="@/assets/logo.png" />
      <el-button
        style="float: right; vertical-align: middle"
        type="danger"
        round
        >소개팅종료</el-button
      >
    </el-header>
    <el-card
      style="
        text-align: center;
        background-color: #fff4b8;
        box-shadow: none;
        border: none;
      "
    >
      <div>
        <img class="small" src="@/assets/smallpink.png" />
        <div class="infoBox">
          <div style="font-weight: bold; font-size: xx-large">
            알아두면 좋은 MBTI 정보
          </div>
          <div style="font-size: x-large; margin-top: 50px">
            {{ Info[0].info }}
          </div>
        </div>
        <img class="small" src="@/assets/smallgreen.png" />
      </div>
      <el-footer style="margin-top: 50px">
        <el-row
          v-loading="loading"
          element-loading-text="MBTI 짝궁을 찾고 있어요..!"
          :element-loading-spinner="svg"
          element-loading-svg-view-box="-10, -10, 50, 50"
        ></el-row>
      </el-footer>
    </el-card>
    <button id="send" @click="send">test</button>
  </el-container>
</template>

<script>
import { useStore } from "vuex";
import { ref, reactive, computed, onMounted, watch } from "vue";
import { useRouter } from "vue-router";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
export default {
  setup() {
    const loading = ref(true);
    const router = useRouter();
    const store = useStore();
    const state = reactive({
      memberinfo: computed(() => store.getters["accounts/getMember"]),
      proposal: null,
      stompClient: null,
    });
    onMounted(() => {
      connect();
    });
    const svg = `
        <path class="path" d="
          M 30 15
          L 28 17
          M 25.61 25.61
          A 15 15, 0, 0, 1, 15 30
          A 15 15, 0, 1, 1, 27.99 7.5
          L 15 15
        " style="stroke-width: 4px; fill: rgba(0, 0, 0, 0)"/>
      `;
    const Info = [
      {
        info: "INFJ는 문제가 생겼을 때 본인이 적극적으로 해결하려고 하는 성향이 강합니다.",
      },
    ];
    const goHome = function () {
      router.push({ name: "HomeView" });
    };

    const connect = function () {
      const serverURL = "http://localhost:8080/ws/connect";
      let socket = new SockJS(serverURL);
      state.stompClient = Stomp.over(socket);
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`);
      state.stompClient.connect(
        {
          accessToken: sessionStorage.getItem("access-token"),
          email: state.memberinfo.email,
        },
        (frame) => {
          // 소켓 연결 성공
          state.stompClient.connected = true;
          console.log("소켓 연결 성공", frame);
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          console.log(state.memberinfo.email);
          state.stompClient.subscribe(
            `/ws/sub/indi/${state.memberinfo.email}`,
            (res) => {
              //prop
              console.log("받은 메시지", res.body);
              state.proposal = res.body.proposal;
            },
            {
              gender: state.memberinfo.gender,
              sido: state.memberinfo.sido,
              interests: state.memberinfo.interests,
            }
          );
        },
        (error) => {
          // 소켓 연결 실패
          console.log("소켓 연결 실패", error);
          this.connected = false;
        }
      );
    };

    watch(
      () => state.proposal,
      (data) => {
        console.log(data);
        send();
      }
    );
    const send = function () {
      console.log("send 실행");
      const data = {
        email: state.memberinfo.email,
        //jwt 추가로 줘야함
      };
      console.log(data);
      state.stompClient.send(
        `/ws/msg/indi/${state.memberinfo.email}`,
        { email: `${state.memberinfo.email}` },
        JSON.stringify({ msg: "" })
      );
    };
    return { loading, svg, Info, goHome, connect, send };
  },
};
</script>

<style>
.small {
  width: 200px;
  height: 250px;
  vertical-align: bottom;
}

.infoBox {
  display: inline-block;
  text-align: center;
  padding: 10px;
  height: 275px;
  width: 600px;
  border: 20px solid #e3842d;
  background-color: white;
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.12);
}

.el-loading-text {
  font-weight: bold;
}

.el-loading-spinner {
  color: black;
}
</style>
