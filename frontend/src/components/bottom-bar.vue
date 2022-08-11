<template>
  <div class="controller" style="display: flex; justify-content: space-between">
    <div class="leftside" style="margin-left: 2rem">
      <el-button type="success" :icon="BellFilled" circle />
      <el-button type="danger" :icon="BellFilled" circle />
      <el-button type="info" :icon="QuestionFilled" circle />
    </div>
    <div class="rightside" style="margin-right: 2rem">
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
      <el-button @click="chatOnOff" type="info" :icon="ChatDotSquare" round
        >채팅</el-button
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
  ChatDotSquare,
} from "@element-plus/icons-vue";
import { reactive } from "vue";

export default {
  setup(props, { emit }) {
    const data = reactive({
      flag: false,
    });

    const chatOnOff = () => {
      data.flag = !data.flag;
      console.log(data.flag);

      emit("chatOnOff", {
        flag: data.flag,
      });
    };

    return {
      data,
      chatOnOff,
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
