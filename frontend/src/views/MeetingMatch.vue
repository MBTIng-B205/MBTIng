<template>
  <el-container style="background-color: #fadce1">
    <el-header>
      <img
        class="logo"
        @click="goHome"
        src="@/assets/logo.png"
        alt="logo"
        style="cursor: pointer"
      />
      <el-button
        style="float: right; margin-top: 25px"
        type="danger"
        @click="goHome"
        size="large"
        round
        >소개팅종료</el-button
      >
    </el-header>
    <el-card
      style="
        text-align: center;
        background-color: #fadce1;
        box-shadow: none;
        border: none;
      "
    >
      <div style="margin-bottom: 50px">
        <img
          class="small"
          src="@/assets/smallpink.png"
          style="width: 250px; height: 250px"
        />
        <div class="infoBox">
          <div style="margin-top: 20px">
            <img
              class="gender"
              v-if="state.proposal.gender == `MALE`"
              src="@/assets/male.png"
            />
            <img class="gender" v-else src="@/assets/female.png" />
            <div>
              <span style="font-size: 100px; font-weight: bold">{{
                state.proposal.mbti
              }}</span>
              <a href="#" class="info">
                <img
                  style="width: 70px; height: 70px"
                  src="@/assets/ask.png"
                  class="question"
                />
                <div class="Bubble bubblePosition hoverBubble">
                  <p>
                    {{ mbtiInfo[state.proposal.mbti] }}
                  </p>
                </div>
              </a>
            </div>
          </div>
        </div>
        <img class="small" src="@/assets/smallgreen.png" />
      </div>
      <el-row style="flex-direction: row; justify-content: center">
        <el-button type="success" size="large" @click="proposalAccept" round
          >수락</el-button
        >
        <el-button type="danger" size="large" @click="proposalRefuse" round
          >거절</el-button
        >
      </el-row>
    </el-card>
  </el-container>

  <el-dialog top="250px" v-model="state.alertdialog" width="30%" center>
    <el-row style="top: 12px; font-size: 16.5px">{{ state.alertmsg }}</el-row>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="danger" round @click="closedialog">확인</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { reactive, computed } from "vue";
export default {
  setup() {
    const store = useStore();
    const router = useRouter();
    const state = reactive({
      proposal: computed(() => store.getters["meetings/getProposal"]),
      mtsocket: computed(() => store.getters["meetings/getSocket"]),
      alertdialog: computed(() => store.getters["meetings/getAlertdialog"]),
      alertmsg: computed(() => store.getters["meetings/getAlertmsg"]),
      alertcommand: computed(() => store.getters["meetings/getAlertcommand"]),
    });
    const mbtiInfo = {
      ENFP: "ENFP는 상대방의 이야기를 진지하게 들어주고 공감능력또한 뛰어납니다.",
      ENFJ: "ENFJ는 솔직하게 자신의 이야기를 꺼내 친근한 대화를 이끕니다.",
      ENTP: "ENTP는 유머감각이 뛰어나 재미있고 엉뚱한 이야기로 사람들을 웃게 만듭니다.",
      ENTJ: "ENTJ는 대화의 주도권을 가지는 것을 선호하고 바로 요점으로 들어가는 대화방식을 선호합니다.",
      ESFP: "ESFP는 적극적으로 대화를 하고 동작을 많이 사용합니다.",
      ESFJ: "ESFJ는 대화를 나눌때 스스로를 낮춰서 상대방을 배려합니다.",
      ESTP: "ESTP는 먼저 대화를 시도하고 상대방의 말을 재치있게 받아줍니다.",
      ESTJ: "ESTJ는 직설적인 화법을 구사해 간혹 오해를 받습니다.",
      INFP: "INFP는 일대일 대화를 선호하고 조용하게 이야기를 들어줍니다.",
      INFJ: "INFP는 깊은 속마음을 잘 드러내지 않습니다.",
      INTP: "INTP는 충분히 생각한 후 대화를 시작합니다.",
      INTJ: "INTJ는 말이 없어보이만 질문을 받으면 이야기를 잘 이어나갑니다.",
      ISFP: "ISFP는 먼저 대화를 시작하기보다 상대방의 말을 들어주는 편입니다.",
      ISFJ: "ISFJ는 다른 사람의 이야기를 잘 들어주고 공감을 잘 해준다.",
      ISTP: "ISTP는 이야기가 길어지면 지루해하고 말을 할 때 요점만 이야기합니다.",
      ISTJ: "ISTJ는 대화를 주도하기보다 조용하게 분위기를 지켜보는 스타일입니다.",
    };
    const proposalAccept = function () {
      console.log("proposalAccept 실행");
      const msg = {
        command: "proposalResult",
        data: true,
      };
      console.log(msg);
      store.dispatch("meetings/send", msg);
    };

    const proposalRefuse = function () {
      console.log("proposalRefuse 실행");
      const msg = {
        command: "proposalResult",
        data: false,
      };
      console.log(msg);
      store.dispatch("meetings/send", msg);
      state.mtsocket.disconnect();
      store.commit("meetings/SET_SOCKET", null);
      router.push({ name: "MeetingWait" });
    };
    const goHome = function () {
      router.push({ name: "HomeView" });
      state.mtsocket.disconnect();
      store.commit("meetings/SET_SOCKET", null);
    };
    const closedialog = function () {
      store.commit("meetings/SET_ALERTDIALOG", false);
      store.commit("meetings/SET_ALERTMSG", null);
      if (state.alertcommand == "proposalaccept") {
        store.commit("meetings/SET_ALERTCOMMAND", null);
        router.push({ path: "/room" });
      }
      /*else if (state.alertcommand == "proposalrefuse") {
        store.commit("meetings/SET_ALERTCOMMAND", null);
        router.push({ name: "MeetingWait" });
      } else {
        store.commit("meetings/SET_ALERTCOMMAND", null);
        router.push({ name: "MeetingWait" });
      }*/
    };
    return {
      state,
      mbtiInfo,
      proposalAccept,
      proposalRefuse,
      goHome,
      closedialog,
    };
  },
};
</script>

<style>
.small {
  width: 200px;
  height: 250px;
  vertical-align: bottom;
}

.gender {
  width: 50px;
  height: 50px;
}

.infoBox {
  display: inline-block;
  text-align: center;
  padding: 10px;
  height: 275px;
  width: 600px;
  border: 20px solid rgb(255, 91, 136);
  background-color: white;
}

.el-loading-text {
  font-weight: bold;
}

.el-loading-spinner {
  color: black;
}
.info {
  color: #ffffff;
  display: block;
  position: relative;
  text-decoration: none;
  width: 70px;
}
.hoverBubble {
  background: #f0f8ff;
  color: black;
  cursor: pointer;
  opacity: 0;
  padding: 5px;
  position: absolute;
  text-align: left;
  visibility: hidden;
  -webkit-border-radius: 3px;
  -moz-border-radius: 3px;
  -o-border-radius: 3px;
  border-radius: 3px;
  -webkit-transition: all 0.25s;
  -moz-transition: all 0.25s;
  -ms-transition: all 0.25s;
  -o-transition: all 0.25s;
  transition: all 0.25s;
}

.bubblePosition {
  bottom: 115%;
  height: inherit;
  left: 80%;
  min-height: 10px;
  top: auto;
  width: 190px;
}

.hoverBubble:after,
.bubblePosition:after {
  content: "";
  height: 0px;
  left: 0;
  margin: 0 auto;
  right: 0;
  width: 0px;
}

a:hover .hoverBubble {
  opacity: 1;
  visibility: visible;
  -webkit-transition: all 0.25s;
  -moz-transition: all 0.25s;
  -ms-transition: all 0.25s;
  -o-transition: all 0.25s;
  transition: all 0.25s;
}

.question {
  margin: 0;
  border: 0;
  box-shadow: none;
}
.path,
.el-loading-text {
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
