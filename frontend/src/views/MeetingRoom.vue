<template>
  <el-container>
    <div class="cam">
      <div class="mbtiinfo">
        <div div class="mbtic"></div>
        <user-video
          class="userVideo-you"
          v-for="sub in state.subscribers"
          :key="sub.stream.connection.connectionId"
          :stream-manager="sub"
          @click="updateMainVideoStreamManager(sub)"
          style=""
        />
        <user-video
          class="userVideo-me"
          :stream-manager="state.publisher"
          @click="updateMainVideoStreamManager(state.publisher)"
        />
        <div id="session">
          <div id="session-header">
            <h1 id="seesion-title">{{ mySessionId }}</h1>
          </div>
        </div>
      </div>
    </div>

    <div class="chatdiv">
      <room-chat
        ref="chat"
        @message="sendMessage"
        :subscribers="subscribers"
        style="
          border-color: deeppink;
          width: 300px;
          height: 300px;
          border-radius: 5px;
        "
      ></room-chat>
    </div>
    <bottom-bar @videoOnOff="videoOnOff" @audioOnOff="audioOnOff"></bottom-bar>
  </el-container>
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
import { reactive, ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

axios.defaults.headers.post["Content-Type"] = "application/json";

const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";
export default {
  components: { UserVideo, RoomChat, BottomBar },
  setup() {
    const router = useRouter();
    const store = useStore();
    const state = reactive({
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],
      mySessionId: "SessionA",
      myUserName: "Participant" + Math.floor(Math.random() * 100),
    });
    onMounted(() => {
      joinSession();
    });
    const chat = ref(null);
    const joinSession = () => {
      state.OV = new OpenVidu();
      state.session = state.OV.initSession();

      state.session.on("streamCreated", ({ stream }) => {
        const subscriber = state.session.subscribe(stream);
        console.log(subscriber, "이거다");
        state.subscribers.push(subscriber);
        console.log("streamCreated");
      });

      state.session.on("streamDestroyed", ({ stream }) => {
        const index = state.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          state.subscribers.splice(index, 1);
          console.log("streamDestroyed");
        }
      });

      state.session.on("exception", ({ exception }) => {
        console.log(exception);
      });

      // Add chat
      state.session.on("signal:public-chat", (event) => {
        chat.value.addMessage(
          event.data,
          JSON.parse(event.data).sender === state.myUserName,
          false
        );
      });

      getToken(state.mySessionId).then((token) => {
        // token =
        //   "wss://i7b205.p.ssafy.io:4443?sessionId=ses_J0lpY4CygF&token=tok_SWIrIydEDAq6ZLyT";
        state.session
          .connect(token, { clientData: state.myUserName })
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
      });
      window.addEventListener("beforeunload", leaveSession);
    };
    // const audioOnOff = ({ audio }) => {
    //   console.log("audio");
    //   this.publisher.publishAudio(audio);
    // };

    const videoOnOff = ({ video }) => {
      console.log("video");
      state.publisher.publishVideo(video);
    };

    const audioOnOff = ({ audio }) => {
      console.log("audio");
      state.publisher.publishAudio(audio);
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
        sender: state.myUserName,
        // time: current,
      };

      state.session
        .signal({
          data: JSON.stringify(messageData),
          to: [],
          type: "public-chat",
        })
        .then(() => {
          console.log(messageData);
        })
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
      console.log("신고", sirenMsg.value);
      if (sirenMsg.value == "") {
        alert("신고 사유를 입력하세요!");
      } else {
        store
          .dispatch("reports/registerReport", {
            from: state.memberinfo.email,
            to: state.message.sender.email,
            content: sirenMsg.value,
          })
          .then(function (result) {
            console.log("result-report", result);
            alert("신고가 접수되었습니다.");
          });
        sirenClose();
      }
    };

    return {
      state,
      chat,
      joinSession,
      leaveSession,
      videoOnOff,
      audioOnOff,
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
.el-container {
  margin-top: 40px;
  display: flex;
  justify-content: center;
}
.cam {
  display: flex;
  background-color: #7d7d7d;
  width: 80%;
  height: 600px;
  margin-bottom: 34px;
  justify-content: center;
  align-items: center;
}
.mbtiinfo {
  background-color: #908d8d;
  border-radius: 50%;
  width: 500px;
  height: 500px;
  align-items: center;
  display: flex;
  position: relative;
}
.mbtic {
  display: flex;
}
.mbti {
  left: 39%;
  position: absolute;
}

.controller {
  background-color: #fff4b8;
  height: 100px;
  display: flex;
  justify-content: center;
}
.timer {
  background-color: deeppink;
  height: 100px;
  width: 300px;
  margin-right: 10px;
  margin-left: 10px;
}
.right {
  display: flex;
  justify-content: center;
  align-items: center;
}
.left {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
