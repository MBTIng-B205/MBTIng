<template>
  <div class="controller" style="display: flex; justify-content: space-between">
    <div class="leftside" style="margin-left: 2rem">
      <el-button type="success" :icon="BellFilled" @click="greenlight" circle />
      <el-button type="danger" :icon="BellFilled" @click="redlight" circle />
      <el-button type="info" :icon="QuestionFilled" circle />
    </div>
    <div class="rightside" style="margin-right: 2rem">
      <el-button type="danger" :icon="WarningFilled" round @click="reportOnOff"
        >신고하기</el-button
      >
      <button @click="addFriend">친구추가</button>
      <el-button
        type="danger"
        :icon="WarningFilled"
        round
        @click="
          receiveClose();
          sirenOpen();
        "
      />
      <el-button @click="chatOnOff" type="info" :icon="ChatDotSquare" round
        >채팅</el-button
      >
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
import { reactive } from "vue";
import { useStore } from "vuex";
export default {
  setup(props, { emit }) {
    const data = reactive({
      flag: false,
      reportflag: false,
    });
    const store = useStore();
    const chatOnOff = () => {
      data.flag = !data.flag;
      console.log(data.flag);

      emit("chatOnOff", {
        flag: data.flag,
      });
    };

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
        data: { result: "GREEN" },
      };
      console.log(msg);
      store.dispatch("meetings/send", msg);
    };
    const redlight = function () {
      console.log("redlight 실행");
      const msg = {
        command: "meetingAudioStageResult",
        data: { result: "RED" },
      };
      console.log(msg);
      store.dispatch("meetings/send", msg);
    };
    const addFriend = () => {};
    return {
      data,
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
</style>
