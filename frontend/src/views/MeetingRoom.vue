<template>
  <div style="position: absolute; margin-left: 20px">
    <img
      src="@/assets/logo.png"
      @click="goHome"
      style="width: 200px; height: 94px; cursor: pointer"
    />
  </div>
  <el-button
    style="position: absolute; margin-top: 25px; right: 0; margin-right: 10px"
    type="danger"
    size="large"
    round
    @click="goHome"
    >소개팅종료</el-button
  >
  <el-container
    style="display: flex; flex-direction: column; background-color: #fadce1"
  >
    <!-- cam -->
    <div class="cam" style="display: flex; flex-direction: row">
      <div
        class="video2-wrapper"
        style="margin: auto auto 0 0; margin-left: 30px; z-index: 2"
      >
        <user-video
          class="userVideo-me"
          style="border-radius: 1rem"
          :stream-manager="state.publisher"
          @click="updateMainVideoStreamManager(state.publisher)"
        />
        <video-controller
          @videoOnOff="videoOnOff"
          @audioOnOff="audioOnOff"
        ></video-controller>
      </div>

      <div
        class="video1-wrapper"
        style="
          position: absolute;
          left: 24%;
          margin-top: 0;
          margin-bottom: 0;
          width: 840px;
          height: 550px;
        "
      >
        <user-video
          class="uservideo-you"
          v-for="sub in state.subscribers"
          :key="sub.stream.connection.connectionId"
          :stream-manager="sub"
          @click="updateMainVideoStreamManager(sub)"
          style="width: 100%; height: 100%; margin-top: 0; margin-bottom: 0"
        />
        <!-- <div>
            <span
              style="
                font-size: 50px;
                font-weight: bold;
                position: absolute;
                right: 361px;
              "
              >{{ state.partner.mbti }}</span
            >
          </div> -->
      </div>

      <div v-if="!state.videoflag">
        <div
          style="
            left: 20%;
            margin-top: 0px;
            margin-bottom: 0px;
            width: 840px;
            height: 550px;
            background-color: rgb(250, 220, 225);
            z-index: 1;
            position: absolute;
          "
        ></div>
        <div class="mbtiinfo" style="z-index: 2">
          <!-- <div class="mbtiinfo"></div> -->
          <img
            src="@/assets/meetingimg.png"
            alt=""
            style="width: 500px; height: 500px"
          />
          <div
            style="
              display: flex;
              justify-content: center;
              position: absolute;
              top: 480px;
              font-size: 20px;
              width: 500px;
            "
          >
            <!-- <span

            style="
              font-size: 50px;
              font-weight: bold;
              position: absolute;
              left: 202px;
              bottom: -30px;
            "
            >{{ state.partner.mbti }}</span
          > -->

            <div class="tag">#{{ state.partner.mbti }}</div>
            <div
              class="tag"
              v-for="(interest, index) in state.partner.interests.slice(0, 3)"
              :key="index"
            >
              #{{ interest }}
            </div>
          </div>
        </div>
      </div>

      <div
        v-show="state.chatflag === true"
        class="chatdiv"
        style="
          position: absolute;
          right: 0;
          border-radius: 5px;
          margin-right: 30px;
        "
      >
        <room-chat
          ref="chat"
          @message="sendMessage"
          :subscribers="subscribers"
          style="width: 300px; height: 600px"
        ></room-chat>
      </div>
    </div>
    <div class="bar-wrapper" style="display: flex">
      <bottom-bar
        @chatOnOff="chatOnOff"
        @reportOnOff="reportOnOff"
      ></bottom-bar>
    </div>
    <hr />
  </el-container>

  <!-- report dialog -->
  <el-dialog v-model="sirenDialog" @close="sirenClose">
    <el-row
      style="
        padding: 10px;
        flex-direction: colunm;
        align-content: flex-start;
        background-color: #fab6b6;
      "
    >
      신고대상자 : {{ state.partner.nickname }}
    </el-row>
    <el-input
      v-model="sirenMsg"
      type="textarea"
      placeholder="신고사유를 입력해주세요"
      rows="5"
    ></el-input>
    <div style="margin-top: 20px; text-align: center">
      <el-button type="danger" @click="clickSiren" size="large" round
        >신고하기</el-button
      >
      <el-button @click="sirenClose" size="large" round plain>취소</el-button>
    </div>
  </el-dialog>
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
import {
  Check,
  QuestionFilled,
  BellFilled,
  WarningFilled,
  ChatDotSquare,
  Close,
} from "@element-plus/icons-vue";
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "@/components/UserVideo.vue";
import RoomChat from "@/components/RoomChat.vue";
import BottomBar from "@/components/bottom-bar.vue";

import { reactive, ref, onMounted, computed } from "vue";

import VideoController from "@/components/video-controller.vue";

import { useRouter } from "vue-router";
import { useStore } from "vuex";

axios.defaults.headers.post["Content-Type"] = "application/json";

const OPENVIDU_SERVER_URL = process.env.VUE_APP_OPENVIDU_SERVER_URL;
const OPENVIDU_SERVER_SECRET = process.env.VUE_APP_OPENVIDU_SERVER_SECRET;
export default {
  components: {
    UserVideo,
    RoomChat,
    BottomBar,
    VideoController,
  },
  setup() {
    const router = useRouter();
    const store = useStore();
    const state = reactive({
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],
      mtsocket: computed(() => store.getters["meetings/getSocket"]),
      partner: computed(() => store.getters["meetings/getPartner"]),
      token: computed(() => store.getters["meetings/getToken"]),
      memberinfo: computed(() => store.getters["accounts/getMember"]),
      mySessionId: "SessionA",
      chatflag: false,
      videoflag: computed(() => store.getters["meetings/getVideoflag"]),
      alertdialog: computed(() => store.getters["meetings/getAlertdialog"]),
      alertmsg: computed(() => store.getters["meetings/getAlertmsg"]),
      alertcommand: computed(() => store.getters["meetings/getAlertcommand"]),
    });

    onMounted(() => {
      joinSession();
    });
    const chat = ref(null);
    const joinSession = () => {
      state.OV = new OpenVidu();
      state.session = state.OV.initSession();
      store.commit("meetings/SET_OVSOCKET", state.session);
      state.session.on("streamCreated", ({ stream }) => {
        const subscriber = state.session.subscribe(stream);
        state.subscribers.push(subscriber);
      });

      state.session.on("streamDestroyed", ({ stream }) => {
        const index = state.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          state.subscribers.splice(index, 1);
        }
      });

      state.session.on("exception", ({ exception }) => {
        console.log(exception);
      });

      // Add chat
      state.session.on("signal:public-chat", (event) => {
        chat.value.addMessage(
          event.data,
          JSON.parse(event.data).sender === state.memberinfo.nickname,
          false
        );
      });
      state.session
        .connect(state.token, { clientData: state.memberinfo.nickname })
        .then(() => {
          let publisher = state.OV.initPublisher(undefined, {
            audioSource: undefined, // The source of audio. If undefined default microphone
            videoSource: undefined, // The source of video. If undefined default webcam
            publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
            publishVideo: true, // Whether you want to start publishing with your video enabled or not
            resolution: "320x240", // The resolution of your video
            frameRate: 30, // The frame rate of your video
            insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
            mirror: true, // Whether to mirror your local video or not
          });
          state.mainStreamManager = publisher;
          state.publisher = publisher;

          state.session.publish(state.publisher);
        })
        .catch((error) => {
          console.log(
            "There was an error connecting to the session:",
            error.code,
            error.message
          );
        });
      window.addEventListener("beforeunload", leaveSession);
    };

    const videoOnOff = ({ video }) => {
      state.publisher.publishVideo(video);
    };

    //button video -> v-if ->
    //화상
    //greenlight 2개 video cam on
    //send
    const audioOnOff = ({ audio }) => {
      state.publisher.publishAudio(audio);
    };

    const chatOnOff = ({ chatflag }) => {
      state.chatflag = chatflag;
      if (chatflag == true) {
        store.commit("meetings/SET_CHATADDFLAG", false);
      }
    };

    const reportOnOff = ({ reportflag }) => {
      sirenDialog.value = reportflag;
    };

    const leaveSession = () => {
      if (state.session) state.session.disconnect();

      state.session = undefined;
      state.mainStreamManager = undefined;
      state.publisher = undefined;
      state.subscribers = [];
      state.OV = undefined;

      window.removeEventListener("beforeunload", leaveSession);
      router.push({ name: "HomeView" });
    };

    const updateMainVideoStreamManager = (stream) => {
      if (state.mainStreamManager === stream) return;
      state.mainStreamManager = stream;
    };

    const getToken = (mySessionId) => {
      return createSession(mySessionId).then((sessionId) =>
        createToken(sessionId)
      );
    };

    const createSession = (sessionId) => {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
            JSON.stringify({
              customSessionId: sessionId,
            }),
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.id))
          .catch((error) => {
            if (error.response.status === 409) {
              resolve(sessionId);
            } else {
              console.warn(
                `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`
              );
              if (
                window.confirm(
                  `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`
                )
              ) {
                location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
              }
              reject(error.response);
            }
          });
      });
    };

    const createToken = (sessionId) => {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
            {},
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.token))
          .catch((error) => reject(error.response));
      });
    };

    const sendMessage = (content) => {
      // let now = new Date();
      // let current = now.toLocaleTimeString([], {
      // 	hour: '2-digit',
      // 	minute: '2-digit',
      // 	hour12: false, // true인 경우 오후 10:25와 같이 나타냄.
      // });

      let messageData = {
        content: content,
        sender: state.memberinfo.nickname,
        // time: current,
      };

      state.session
        .signal({
          data: JSON.stringify(messageData),
          to: [],
          type: "public-chat",
        })
        .then(() => {})
        .catch((error) => {
          console.log(error);
        });
    };

    // 신고기능
    const sirenDialog = ref(false);
    const sirenMsg = ref("");
    const sirenOpen = function () {
      sirenDialog.value = true;
    };

    const sirenClose = function () {
      sirenMsg.value = "";
      sirenDialog.value = false;
    };

    const clickSiren = function () {
      if (sirenMsg.value == "") {
        store.commit("meetings/SET_ALERTCOMMAND", "reportnull");
        store.commit("meetings/SET_ALERTDIALOG", true);
        store.commit("meetings/SET_ALERTMSG", "신고 사유를 입력하세요!");
        //alert("신고 사유를 입력하세요!");
      } else {
        const msg = {
          command: "createReport",
          data: {
            from_id: state.memberinfo.email,
            to_id: state.partner.email,
            content: sirenMsg.value,
          },
        };
        store.dispatch("meetings/send", msg);

        sirenClose();
      }
    };
    const goHome = function () {
      router.push({ name: "HomeView" });
      store.commit("meetings/SET_VIDEOFLAG", false);
      if (state.mtsocket != null) {
        state.mtsocket.disconnect();
      }
      store.commit("meetings/SET_SOCKET", null);
      if (state.session != null) {
        state.session.disconnect();
      }
      store.commit("meetings/SET_OVSOCKET", null);
    };
    const closedialog = function () {
      store.commit("meetings/SET_ALERTDIALOG", false);
      store.commit("meetings/SET_ALERTMSG", null);
      if (state.alertcommand == "audiorefuse") {
        store.commit("meetings/SET_ALERTCOMMAND", null);
        router.push({ name: "HomeView" });
      } else if (state.alertcommand == "audioaccept") {
        store.commit("meetings/SET_ALERTCOMMAND", null);
        // router.push({ name: "MeetingWait" });
      } else if (state.alertcommand == "opponentleft") {
        store.commit("meetings/SET_ALERTCOMMAND", null);
        router.push({ name: "HomeView" });
      } else if (state.alertcommand == "reportnull") {
        store.commit("meetings/SET_ALERTCOMMAND", null);
      }
    };
    return {
      state,
      chat,
      closedialog,
      joinSession,
      leaveSession,
      reportOnOff,
      videoOnOff,
      audioOnOff,
      chatOnOff,
      updateMainVideoStreamManager,
      getToken,
      createSession,
      createToken,
      sendMessage,
      sirenDialog,
      sirenMsg,
      sirenOpen,
      sirenClose,
      clickSiren,
      goHome,
      Check,
      QuestionFilled,
      BellFilled,
      WarningFilled,
      ChatDotSquare,
      Close,
    };
  },
};
</script>

<style scoped>
.tag {
  border-radius: 20px;
  background-color: azure;
  margin-right: 10px;
  padding: 7px;
}
.el-container {
  display: flex;
  justify-content: center;
}
.cam {
  display: inline-flex;
  background-color: #fadce1;
  height: 600px;
  margin-bottom: 34px;
  justify-content: space-between;
  align-items: auto;
}
.mbtiinfo {
  position: absolute;
  left: 35%;
  top: 15%;
}
.video-wrapper {
  width: 10rem;
  height: 10rem;
}
.uservideo-you {
  width: 100%;
  height: 100%;
}
.chatdiv {
  background-color: white;
  border-radius: 1px;
  z-index: 1;
}
::v-deep .uservideo-you video {
  width: 820px;
  height: 545px;
  border-radius: 20px;
  margin-top: 0;
  margin-bottom: 0;
}
::v-deep .userVideo-me {
  display: flex;
  margin-top: auto;
  margin-right: auto;
  align-self: flex-start;
  border-radius: 20px;
}
.tag {
  border-radius: 20px;
  background-color: azure;
  margin-right: 10px;
  padding: 7px;
}
</style>
