<template>
  <div
    class="controller"
    style="
      display: flex;
      justify-content: space-between;
      background-color: rgb(255, 189, 207);
    "
  >
    <div class="leftside" style="margin-left: 2rem; display: flex">
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
            상대방이 맘에 들지 않으면 레드라이트를 누르세요!
          </div>
        </div>
      </a>
    </div>

    <div>
      <div style="margin-left: 180px">
        <span style="font-size: 3rem"
          >{{ state.timer.minutes }} : {{ state.timer.seconds }}</span
        >
      </div>
    </div>
    <div class="rightside" style="margin-right: 2rem">
      <el-button
        type="primary"
        :icon="WarningFilled"
        size="large"
        round
        @click="addFriend"
        >친구추가</el-button
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
export default {
  setup(props, { emit }) {
    const store = useStore();
    const state = reactive({
      chatflag: false,
      reportflag: false,
      time: null,
      timer: null,
      partner: computed(() => store.getters["meetings/getPartner"]),
      memberinfo: computed(() => store.getters["accounts/getMember"]),
    });

    state.time = new Date();
    state.time.setSeconds(state.time.getSeconds() + 600); // 10 minutes timer
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
        timeout();
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
      if (confirm("친구추가 하시겠습니까?")) {
        console.log(state.memberinfo);
        console.log(state.partner);
        store
          .dispatch("friends/addFriend", {
            from: state.memberinfo.email,
            to: state.partner.email,
          })
          .then(function (result) {
            console.log("addResult", result);
            state.friendFlag = true;
          });
      }
    };
    return {
      state,
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
