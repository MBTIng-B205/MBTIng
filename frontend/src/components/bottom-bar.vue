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
        <span>{{ data.timer.minutes }}</span>
        :<span>{{ data.timer.seconds }}</span>
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
import { reactive, watchEffect } from "vue";
import { useStore } from "vuex";
import { useTimer } from "vue-timer-hook";
export default {
  setup(props, { emit }) {
    const data = reactive({
      flag: false,
      reportflag: false,
      time: null,
      timer: null,
    });
    data.time = new Date();

    data.time.setSeconds(data.time.getSeconds() + 10); // 10 minutes timer
    data.timer = useTimer(data.time);
    data.timer.start();
    const restartFive = () => {
      // Restarts to 10 minutes timer
      data.time = new Date();
      data.time.setSeconds(data.time.getSeconds() + 600);
      data.timer.restart(data.time);
    };
    const store = useStore();
    const chatOnOff = () => {
      data.flag = !data.flag;
      console.log(data.flag);

      emit("chatOnOff", {
        flag: data.flag,
      });
    };
    const stopWatchEffect = watchEffect(() => {
      if (data.timer.isRunning == false)
        console.log("타이머 다됨 !!!!!!!!!!!!!!!!!!!!!!!!");
    });
    const reportOnOff = () => {
      data.reportflag = !data.reportflag;
      console.log(data.reportflag);

      emit("reportOnOff", {
        flag: data.reportflag,
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
    const addFriend = () => {};
    return {
      data,
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
