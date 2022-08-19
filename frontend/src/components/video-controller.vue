<template>
  <div class="controller" style="display: flex; justify-content: center">
    <el-button
      v-if="state.video === true"
      class="custom-icon"
      @click="videoOnOff"
      size="large"
      round
      :icon="View"
    />
    <el-button
      v-else
      class="custom-icon"
      @click="videoOnOff"
      size="large"
      round
      :icon="Hide"
    />
    <el-button
      v-if="state.audio === true"
      class="custom-icon"
      @click="audioOnOff"
      size="large"
      round
      :icon="Microphone"
    />
    <el-button
      v-else
      class="custom-icon"
      @click="audioOnOff"
      size="large"
      round
      :icon="Mute"
    />
  </div>
</template>

<script>
import {
  CloseBold,
  Microphone,
  View,
  Hide,
  Mute,
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
      CloseBold,
      Microphone,
      View,
      Hide,
      Mute,
    };
  },
};
</script>

<style scoped>
.custom-icon {
  font-size: 1.5rem;
}
</style>
