<template>
  <div
    class="wrapper"
    style="display: flex; flex-direction: column-reverse; overflow-y: auto"
  >
    <!-- chat-bar -->
    <div ref="content" class="background-color: white;">
      <!-- 채팅 내용 -->
      <ul id="chat-bar" style="list-style-type: none">
        <li class="" v-for="(chat, idx) in state.chats" :key="idx">
          <!-- 내가 보낸 채팅인 경우 -->
          <div v-if="chat.isMyMessage" class="">
            <div>
              <p>
                나 :
                {{ chat.content.content }}
              </p>
            </div>
          </div>
          <!-- 다른 사람이 보낸 채팅인 경우 -->
          <div v-else>
            <div>
              <p>
                {{ chat.userId }} :
                {{ chat.content.content }}
              </p>
            </div>
          </div>
        </li>
      </ul>
    </div>
    <!-- 메시지 작성 -->
    <div style="position: absolute; margin-left: 5px; margin-top: 10px">
      <div class="">
        <el-input
          v-model="state.message"
          @keydown.enter="sendMessage"
          style="width: 230px; margin-right: 3px"
        >
        </el-input>
        <el-button
          type="warning"
          plain
          :icon="Promotion"
          @click="sendMessage()"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { reactive } from "vue";
import { Promotion } from "@element-plus/icons-vue";
export default {
  props: {
    subscribers: Object,
  },

  setup(props, { emit }) {
    const state = reactive({
      // right: true,
      isSidebarOpen: true,
      selectedUser: "all",
      message: "",
      subscribers: props.subscribers,
      chats: [],
    });

    const sendMessage = () => {
      let strippeddMessage = state.message.trim();

      if (strippeddMessage === "") return;

      console.log("보낼 메시지 : " + strippeddMessage);

      emit("message", {
        content: strippeddMessage,
        to: state.selectedUser,
      });

      event.preventDefault(); // enter키 누를 때 줄바꿈 방지
      state.message = ""; // 메시지 창 초기화

      console.log(state.subscribers);
    };

    const addMessage = async (messageData, isMyMessage) => {
      let message = JSON.parse(messageData);
      // 내가 보낸 메시지인 경우
      if (isMyMessage) {
        message.sender += " (You)";
      }

      let chatBar = document.querySelector("#chat-bar");
      console.log(chatBar, "chatbar");
      let isScrollBottom =
        chatBar.scrollHeight - chatBar.scrollTop <= chatBar.clientHeight + 2;

      // await 키워드 => 새로운 채팅 메시지 추가 완료 후 스크롤바가 아래로 이동되도록 함.
      await state.chats.push({
        userId: message.sender,
        content: message.content,
        isMyMessage: isMyMessage,
      });

      // 채팅 스크롤이 끝까지 내려가 있는 경우 => 스크롤바 맨 아래로 이동시키기
      if (isScrollBottom) {
        chatBar.scrollTo({ top: chatBar.scrollHeight, behavior: "smooth" });
      }

      console.log("메시지 수신 완료");
    };

    return { state, sendMessage, addMessage, Promotion };
  },
};
</script>
<style></style>
