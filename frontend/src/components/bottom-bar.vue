<template>
  <div
    class="controller"
    style="
      display: flex;
      justify-content: space-between;
      background-color: rgb(255, 189, 207);
    "
  >
    <div
      class="leftside"
      style="margin-left: 2rem; display: flex"
      v-if="!state.videoflag"
    >
      <el-button
        type="success"
        :icon="BellFilled"
        style="margin-right: 7px"
        @click="greenlight"
        size="large"
        circle
      />
      <el-button
        type="danger"
        :icon="BellFilled"
        style="margin-right: 15px"
        @click="redlight"
        size="large"
        circle
      />
      <a href="#" class="info">
        <img
          style="width: 40px; height: 40px"
          src="@/assets/ask.png"
          class="question"
        />
        <div
          class="Bubble bubblePosition hoverBubble"
          style="
            border-radius: 1rem;
            width: 350px;
            height: 50px;
            position: absolute;
            top: 1px;
          "
        >
          <div style="margin-top: 0.5rem; margin-left: 0.25rem">
            모두 그린라이트를 누르면 화상모드로 전환됩니다!
          </div>
          <div style="margin-left: 0.25rem">
            상대방이 마음에 들지 않으면 레드라이트를 누르세요!
          </div>
        </div>
      </a>
    </div>
    <div
      class="leftside"
      style="margin-left: 2rem; display: flex; visibility: hidden"
      v-else
    >
      <el-button
        type="success"
        :icon="BellFilled"
        style="margin-right: 7px"
        @click="greenlight"
        size="large"
        circle
      />
      <el-button
        type="danger"
        :icon="BellFilled"
        style="margin-right: 15px"
        @click="redlight"
        size="large"
        circle
      />
      <a href="#" class="info">
        <img
          style="width: 40px; height: 40px"
          src="@/assets/ask.png"
          class="question"
        />
        <div
          class="Bubble bubblePosition hoverBubble"
          style="
            border-radius: 1rem;
            width: 350px;
            height: 50px;
            position: absolute;
            top: 1px;
          "
        >
          <div style="margin-top: 0.5rem; margin-left: 0.25rem">
            모두 그린라이트를 누르면 화상모드로 전환됩니다!
          </div>
          <div style="margin-left: 0.25rem">
            상대방이 마음에 들지 않으면 레드라이트를 누르세요!
          </div>
        </div>
      </a>
    </div>

    <div style="postion: absolute">
      <div style="margin-left: 180px">
        <span style="font-size: 3rem"
          >{{ state.timer.minutes }} : {{ state.timer.seconds }}</span
        >
      </div>
    </div>
    <div class="rightside" style="margin-right: 2rem">
      <el-button
        v-if="!state.friendflag && state.videoflag == true"
        type="primary"
        :icon="WarningFilled"
        size="large"
        round
        @click="addFriend"
        >친구추가</el-button
      >
      <el-button
        v-else-if="state.friendflag && state.videoflag == true"
        type="info"
        :icon="WarningFilled"
        size="large"
        round
        @click="addFriend"
        >친구취소</el-button
      >
      <el-button
        v-else
        type="info"
        :icon="WarningFilled"
        size="large"
        round
        @click="addFriend"
        style="visibility: hidden"
        >친구취소</el-button
      >
      <el-button
        type="danger"
        :icon="WarningFilled"
        size="large"
        round
        @click="reportOnOff"
        >신고하기</el-button
      >
      <el-button
        @click="chatOnOff"
        type="info"
        :icon="ChatDotSquare"
        size="large"
        round
      />
    </div>
  </div>
</template>

<script>
import {
  QuestionFilled,
  BellFilled,
  WarningFilled,
  Close,
  ChatDotSquare,
} from "@element-plus/icons-vue";
import { reactive, watchEffect, computed } from "vue";
import { useStore } from "vuex";
import { useTimer } from "vue-timer-hook";
import { useRouter } from "vue-router";
export default {
  setup(props, { emit }) {
    const router = useRouter();
    const store = useStore();
    const state = reactive({
      chatflag: false,
      reportflag: false,
      friendflag: false,
      time: null,
      timer: null,
      partner: computed(() => store.getters["meetings/getPartner"]),
      memberinfo: computed(() => store.getters["accounts/getMember"]),
      videoflag: computed(() => store.getters["meetings/getVideoflag"]),
      ovsocket: computed(() => store.getters["meetings/getOvsocket"]),
      mtsocket: computed(() => store.getters["meetings/getSocket"]),
    });

    state.time = new Date();
    state.time.setSeconds(state.time.getSeconds() + 10); // 10 minutes timer
    state.timer = useTimer(state.time);
    state.timer.start();
    const restartFive = () => {
      // Restarts to 10 minutes timer
      state.time = new Date();
      state.time.setSeconds(state.time.getSeconds() + 600);
      state.timer.restart(state.time);
    };

    const chatOnOff = () => {
      state.chatflag = !state.chatflag;
      console.log(state.chatflag);

      emit("chatOnOff", {
        chatflag: state.chatflag,
      });
    };
    const stopWatchEffect = watchEffect(() => {
      if (state.timer.isRunning == false) {
        console.log("타이머 다됨 !!!!!!!!!!!!!!!!!!!!!!!!");
        if (state.mtsocket != null) {
          timeout();
        }
        goHome();
      }
    });
    const greenWatchEffect = watchEffect(() => {
      if (state.videoflag == true) {
        restartFive();
      }
    });
    const reportOnOff = () => {
      state.reportflag = !state.reportflag;
      console.log(state.reportflag);

      emit("reportOnOff", {
        reportflag: state.reportflag,
      });
    };
    const greenlight = function () {
      console.log("GREEN 실행");
      const msg = {
        command: "meetingAudioStageResult",
        data: { result: "GREEN" },
      };
      console.log(msg);
      store.dispatch("meetings/send", msg);
    };
    const redlight = function () {
      console.log("RED 실행");
      const msg = {
        command: "meetingAudioStageResult",
        data: { result: "RED" },
      };
      console.log(msg);
      store.dispatch("meetings/send", msg);
    };
    const timeout = function () {
      console.log("TIMEOUT 실행");
      const msg = {
        command: "meetingAudioStageResult",
        data: { result: "TIMEOUT" },
      };
      console.log(msg);
      store.dispatch("meetings/send", msg);
    };

    const addFriend = function () {
      console.log("Friend 실행");
      const msg = {
        command: "addFriend",
        data: {
          fromEmail: state.memberinfo.email,
          toEmail: state.partner.email,
          addOrRemove: !state.friendflag,
        },
      };
      console.log(msg);
      store.dispatch("meetings/send", msg);
      state.friendflag = !state.friendflag;
    };
    const goHome = function () {
      console.log(state.mtsocket);
      store.commit("meetings/SET_VIDEOFLAG", false);
      if (state.mtsocket != null) {
        state.mtsocket.disconnect();
      }
      store.commit("meetings/SET_SOCKET", null);
      if (state.ovsocket != null) {
        state.ovsocket.disconnect();
      }
      store.commit("meetings/SET_OVSOCKET", null);
      router.push({ name: "HomeView" });
    };

    return {
      state,
      goHome,
      greenWatchEffect,
      stopWatchEffect,
      timeout,
      restartFive,
      greenlight,
      redlight,
      reportOnOff,
      chatOnOff,
      addFriend,
      BellFilled,
      QuestionFilled,
      WarningFilled,
      Close,
      ChatDotSquare,
    };
  },
};
</script>

<style scope>
.controller {
  width: 100%;
  height: 50px;
  align-items: center;
}
.custom-icon {
  font-size: 1.5rem;
}
.hoverbox {
  background: #f0f8ff;
  visibility: hidden;
  border-radius: 3px;
  -webkit-border-radius: 3px;
  -moz-border-radius: 3px;
  -o-border-radius: 3px;
  border-radius: 3px;
  -webkit-transition: all 0.25s;
  -moz-transition: all 0.25s;
  -ms-transition: all 0.25s;
  -o-transition: all 0.25s;
  transition: all 0.25s;
}
.infobutton:hover .hoverbox {
  opacity: 1;
  visibility: visible;
  -webkit-transition: all 0.25s;
  -moz-transition: all 0.25s;
  -ms-transition: all 0.25s;
  -o-transition: all 0.25s;
  transition: all 0.25s;
}
</style>
