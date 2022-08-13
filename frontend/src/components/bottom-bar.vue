<template>
  <div
    class="controller"
    style="
      display: flex;
      justify-content: space-between;
      background-color: rgb(255, 189, 207);
    "
  >
    <div class="leftside" style="margin-left: 2rem">
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
        style="margin-right: 7px"
        @click="redlight"
        size="large"
        circle
      />
      <el-button type="info" :icon="QuestionFilled" size="large" circle />
    </div>

    <div>
      <div style="margin-left: 97px">
        <span style="font-size: 3rem">{{ state.timer.minutes }}</span>
        :<span style="font-size: 3rem">{{ state.timer.seconds }}</span>
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
</style>
