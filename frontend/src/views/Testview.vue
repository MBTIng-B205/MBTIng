<template>
  <div id="main-container" class="container">
    <div id="join" v-if="!session">
      <div id="img-div">
        <img src="" />
      </div>
      <div id="join-dialog" class="jumbotron vertical-center">
        <h1>Join a video session</h1>
        <div class="form-group">
          <p>
            <label>Participant</label>
            <input
              v-model="myUserName"
              class="form-control"
              type="text"
              required
            />
          </p>
          <p>
            <label>Session</label>
            <input
              v-model="mySessionId"
              class="form-control"
              type="text"
              required
            />
          </p>
          <p class="text-center">
            <button class="btn btn-lg btn-success" @click="joinSession()">
              Join!
            </button>
          </p>
        </div>
      </div>
    </div>

    <div id="session" v-if="session">
      <div id="session-header">
        <h1 id="session-title">{{ mySessionId }}</h1>
        <input
          type="button"
          id="buttonLeaveSession"
          @click="leaveSession"
          value="Leave session"
        />
      </div>
      <div id="main-video" class="col-md-6">
        <user-video :stream-manager="mainStreamManager" />
      </div>
      <div id="video-container" class="col-md-6">
        <user-video
          :stream-manager="publisher"
          @click="updateMainVideoStreamManager(publisher)"
        />
        <user-video
          v-for="sub in subscribers"
          :key="sub.stream.connection.connectionId"
          :stream-manager="sub"
          @click="updateMainVideoStreamManager(sub)"
        />
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "./components/UserVideo";
import { reactive } from "@vue/reactivity";

axios.defaults.headers.post["Content-Type"] = "application/json";
const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
  setup() {


        const data = reactive({
          OV: undefined,
          session: undefined,
          mainStreamManager: undefined,
          publisher: undefined,
          subscribers: [],
          mySessionId: "SessionA",
          myUserName: "participant" + Math.floor(Math.random() * 100),
        });

        const leaveSession = () => {
          if (data.session) {data.session.disconnect();
          data.session = undefined;
          data.mainStreamManager = undefined;
          data.publisher = undefined;
          data.subscribers = [];
          data.OV = undefined;
          window.removeEventListener("beforeunload", leaveSession)}
        },

        const joinSession = () => {
          data.OV = new OpenVidu();
          data.session = data.OV.initSession();
          data.session.on("streamCreated", ({stream}) => {
            const subscribers = data.session.subscribe(stream);
            subscribers.push(subscribers);
          });
          data.session.on("streamDestroyed", ({stream}) => {
            const index = subscribers.indexOf(stream.streamManager, 0);
            if (index>=0) {
              subscribers.splice(index, 1);
            }
          });
          data.session.on('exception', ({ exception }) => {
    				console.warn(exception);
    			});
          getToken(data.mySessionId).then(token => {
            data.session.connect(token, { clientData: data.myUserName}).then(() => {
                  let publisher = data.OV.initPublisher(undefined, {
    							audioSource: undefined, // The source of audio. If undefined default microphone
    							videoSource: undefined, // The source of video. If undefined default webcam
    							publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
    							publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
    							resolution: '640x480',  // The resolution of your video
    							frameRate: 30,			// The frame rate of your video
    							insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
    							mirror: false       	// Whether to mirror your local video or not
    						});
    						data.mainStreamManager = publisher;
    						data.publisher = publisher;
    						// --- Publish your stream ---
    						data.session.publish(publisher);
            })
            .catch(error => {
    						console.log('There was an error connecting to the session:', error.code, error.message);
    					});
          });
          window.addEventListener('beforeunload', leaveSession)
        },
        const updateMainVideoStreamManger = (stream) => {
          if (data.mainStreamManager === stream) return data.mainStreamManager = stream
        },
        const getToken = (mySessionId) => {
          return createSession(mySessionId).then(sessionId => createToken(sessionId))
        },
        const createSession = (sessionId) => {
          return new Promise((resolve, reject) => {
            axios
              .post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
    						customSessionId: sessionId,
    					}), {
    						auth: {
    							username: 'OPENVIDUAPP',
    							password: OPENVIDU_SERVER_SECRET,
    						},
    					})
    					.then(response => response.data)
    					.then(data => resolve(data.id))
    					.catch(error => {
    						if (error.response.status === 409) {
    							resolve(sessionId);
    						} else {
    							console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
    							if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
    								location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
    							}
    							reject(error.response);
                }
              });
          });
        }
        const createToken = (sessionId) => {
    			return new Promise((resolve, reject) => {
    				axios
    					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`, {}, {
    						auth: {
    							username: 'OPENVIDUAPP',
    							password: OPENVIDU_SERVER_SECRET,
    						},
    					})
    					.then(response => response.data)
    					.then(data => resolve(data.token))
    					.catch(error => reject(error.response));
    			});
    		},
        return { user, joinSession, updateMainVideoStreamManger, createSession, createToken, leaveSession, getToken};
  },
};
</script>

<style></style>
