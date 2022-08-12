<template>
  <div class="controller" style="display: flex; justify-content: space-between">
    <div class="leftside" style="margin-left: 2rem">
      <el-button
        type="success"
        :icon="BellFilled"
        style="margin-right: 7px"
        @click="greenlight"
        circle
      />
      <el-button
        type="danger"
        :icon="BellFilled"
        style="margin-right: 7px"
        @click="redlight"
        circle
      />
      <el-button
        type="Water"
        style="margin-right: 7px"
        @click="timeout"
        circle
      />
      <el-button type="info" :icon="QuestionFilled" circle />
    </div>

    <div>
      <div style="margin-left: 97px">
        <span>{{ state.timer.minutes }}</span>
        :<span>{{ state.timer.seconds }}</span>
      </div>
      <!--
        <button @click="data.timer.start()">Start</button>
        <button @click="data.timer.pause()">Pause</button>
        <button @click="data.timer.resume()">Resume</button>
        -->
    </div>
    <div class="rightside" style="margin-right: 2rem">
      <button @click="addFriend">친구추가</button>
      <el-button
        type="danger"
        :icon="WarningFilled"
        round
        @click="
          receiveClose();
          sirenOpen();
        "
        >신고하기</el-button
      >
      <el-button @click="chatOnOff" type="info" :icon="ChatDotSquare" round />
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
      flag: false,
      reportflag: false,
      time: null,
      timer: null,
      partner: computed(() => store.getters["meetings/getPartner"]),
      memberinfo: computed(() => store.getters["accounts/getMember"]),
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
      state.flag = !state.flag;
      console.log(state.flag);

      emit("chatOnOff", {
        flag: state.flag,
      });
    };
    const stopWatchEffect = watchEffect(() => {
      if (state.timer.isRunning == false)
        console.log("타이머 다됨 !!!!!!!!!!!!!!!!!!!!!!!!");
    });
    const reportOnOff = () => {
      state.reportflag = !state.reportflag;
      console.log(state.reportflag);

      emit("reportOnOff", {
        flag: state.reportflag,
      });
    };
    const greenlight = function () {
      console.log("greenlight 실행");
      const msg = {
        command: "meetingAudioStageResult",
        data: "green",
      };
      console.log(msg);
      store.dispatch("meetings/send", msg);
    };
    const redlight = function () {
      console.log("redlight 실행");
      const msg = {
        command: "meetingAudioStageResult",
        data: "red",
      };
      console.log(msg);
      store.dispatch("meetings/send", msg);
    };
    const timeout = function () {
      console.log("redlight 실행");
      const msg = {
        command: "meetingAudioStageResult",
        data: "timeout",
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
}
.custom-icon {
  font-size: 1.5rem;
}
</style>
