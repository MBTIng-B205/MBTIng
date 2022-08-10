<template>
  <div class="controller">
    <div class="left">
      <el-button type="success" :icon="BellFilled" circle />
      <el-button type="danger" :icon="BellFilled" circle />
      <el-button type="info" :icon="QuestionFilled" circle />
      <span>라이트를 눌러 화상여부를 선택하세요</span>
    </div>
    <div class="timer"></div>
    <div class="right">
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
      <el-button @click="videoOnOff">화면끄기</el-button>
      <el-button @click="audioOnOff">소리끄기</el-button>
      <!-- <el-button type="info" :icon="ChatDotSquare" round>채팅</el-button> -->
      <el-button type="danger" :icon="Close" round @click="leaveSession"
        >나가기</el-button
      >
    </div>

    <!-- report dialog -->
    <el-dialog v-model="sirenDialog" @close="sirenClose">
      <div style="font-weight: bold; float: left; margin: 10px">
        신고대상자 : {{}}
      </div>
      <el-input
        v-model="sirenMsg"
        type="textarea"
        placeholder="신고사유를 입력해주세요"
        rows="5"
      ></el-input>
      <div style="margin-top: 20px">
        <el-button type="danger" @click="clickSiren">신고하기</el-button>
        <el-button @click="sirenClose">취소</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  QuestionFilled,
  BellFilled,
  WarningFilled,
  Close,
} from "@element-plus/icons-vue";
import { reactive } from "vue";
export default {
  setup(props, { emit }) {
    const state = reactive({
      video: true,
      audio: true,
    });

    const videoOnOff = () => {
      if (state.video) {
        state.video = false;
      } else {
        state.video = true;
      }

      emit("videoOnOff", {
        video: state.video,
      });
    };

    const audioOnOff = () => {
      if (state.audio) {
        state.audio = false;
      } else {
        state.audio = true;
      }
      console.log(state.audio);
      emit("audioOnOff", {
        audio: state.audio,
      });
    };
    return {
      videoOnOff,
      audioOnOff,
      BellFilled,
      QuestionFilled,
      WarningFilled,
      Close,
    };
  },
};
</script>

<style></style>
