<template>
  <div class="controller" style="display: flex; justify-content: center">
    <el-button @click="videoOnOff">화면끄기</el-button>
    <el-button @click="audioOnOff">소리끄기</el-button>
    <el-button
      @click="leaveSession"
      type="danger"
      :icon="Close"
      round
    ></el-button>
  </div>
</template>

<script>
import { Close } from "@element-plus/icons-vue";
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

    const leaveSession = () => {
      emit("leaveSession");
    };

    return {
      state,
      videoOnOff,
      audioOnOff,
      leaveSession,
      Close,
    };
  },
};
</script>

<style scoped></style>
