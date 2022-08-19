<template>
  <div
    class="wrapper"
    style="
      display: flex;
      flex-direction: column-reverse;
      overflow: hidden;
      width: 300px;
      height: 550px;
      position: absolute;
      right: 10px;
      top: 15px;
    "
  >
    <!-- chat-bar -->
    <div
      ref="content"
      class="chat-bar"
      id="chat-bar"
      style="
        background-color: white;
        margin-bottom: 50px;
        overflow-y: auto;
        border-radius: 10px;
        margin-left: 5px;
        height: 500px;
      "
    >
      <!-- 채팅 내용 -->
      <ul style="list-style-type: none; padding: 0">
        <li class="" v-for="(chat, idx) in state.chats" :key="idx" style="">
          <!-- 내가 보낸 채팅인 경우 -->
          <div v-if="chat.isMyMessage" class="" style="margin-bottom: 10px">
            <div
              style="
                background: rgb(255, 189, 207);
                border-radius: 10px;
                width: 250px;
                padding-top: 5px;
                padding-left: 10px;
                padding-right: 5px;
                margin-left: 13px;
                word-break: break-all;
              "
            >
              나 :
              {{ chat.content.content }}
            </div>
          </div>
          <!-- 다른 사람이 보낸 채팅인 경우 -->
          <div v-else style="margin-bottom: 10px">
            <div
              style="
                background: #dddddd;
                border-radius: 10px;
                width: 250px;
                padding-top: 5px;
                padding-left: 10px;
                padding-right: 5px;
                margin-left: 13px;
                word-break: break-all;
              "
            >
              {{ chat.userId }} :
              {{ chat.content.content }}
            </div>
          </div>
        </li>
      </ul>
    </div>
    <!-- 메시지 작성 -->
    <div style="position: absolute; margin-left: 5px; margin-bottom: 2px">
      <el-input
        v-model="state.message"
        @keydown.enter="sendMessage"
        style="width: 240px; margin-right: 3px"
      >
      </el-input>
      <el-button
        type="danger"
        plain
        :icon="Promotion"
        @click="sendMessage()"
        size="large"
        style="width: 50px; height: 40px"
      />
    </div>
  </div>
</template>

<script>
import { reactive, computed } from "vue";
import { Promotion } from "@element-plus/icons-vue";
import { useStore } from "vuex";
export default {
  props: {
    subscribers: Object,
  },

  setup(props, { emit }) {
    const store = useStore();
    const state = reactive({
      // right: true,
      isSidebarOpen: true,
      selectedUser: "all",
      message: "",
      subscribers: props.subscribers,
      chats: store.getters["meetings/getChats"],
      chataddflag: computed(() => store.getters["meetings/getChataddflag"]),
      chat: [],
    });

    const sendMessage = () => {
      let strippeddMessage = state.message.trim();

      if (strippeddMessage === "") return;

      emit("message", {
        content: strippeddMessage,
        to: state.selectedUser,
      });

      event.preventDefault(); // enter키 누를 때 줄바꿈 방지
      state.message = ""; // 메시지 창 초기화
    };

    const addMessage = async (messageData, isMyMessage) => {
      let message = JSON.parse(messageData);
      // 내가 보낸 메시지인 경우
      if (isMyMessage) {
        message.sender += " (You)";
      } else {
        store.commit("meetings/SET_CHATADDFLAG", true);
      }
      let chatBar = document.querySelector("#chat-bar");
      let isScrollBottom =
        chatBar.scrollHeight - chatBar.scrollTop <= chatBar.clientHeight + 2;

      // await 키워드 => 새로운 채팅 메시지 추가 완료 후 스크롤바가 아래로 이동되도록 함.
      await state.chat.push({
        userId: message.sender,
        content: message.content,
        isMyMessage: isMyMessage,
      });
      state.chats = state.chat;
      store.commit("meetings/SAVE_CHAT", {
        chats: state.chats,
      });
      // 채팅 스크롤이 끝까지 내려가 있는 경우 => 스크롤바 맨 아래로 이동시키기
      if (isScrollBottom) {
        chatBar.scrollTo({ top: chatBar.scrollHeight, behavior: "smooth" });
      }
    };

    return {
      state,
      sendMessage,
      addMessage,
      Promotion,
    };
  },
};
</script>
<style>
.chat-bar::-webkit-scrollbar {
  width: 8px;
}
.chat-bar::-webkit-scrollbar-thumb {
  height: 30%;
  background: #f56c6c;
  border-radius: 10px;
}
.chat-bar::-webkit-scrollbar-track {
  background: rgb(33, 122, 244, 0.1);
}
.controller > button,
.wrapper {
  --el-color-primary: #f56c6c;
  --el-color-primary-light-3: #f89898;
  --el-color-primary-light-5: #fab6b6;
  --el-color-primary-light-7: #fcd3d3;
  --el-color-primary-light-8: #fde2e2;
  --el-color-primary-light-9: #fef0f0;
  --el-color-primary-dark-2: #c45656;
  --el-select-input-focus-border-color: #f56c6c;
  --el-menu-hover-text-color: #f56c6c;
}
</style>
