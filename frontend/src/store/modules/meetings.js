// import $axios from "axios";

export const meetings = {
  namespaced: true,
  state: {
    mtsocket: null,
    proposal: null,
    token: null,
  },
  mutations: {
    SET_SOCKET: (state, mtsocket) => {
      state.mtsocket = mtsocket;
    },
    SET_PROPOSAL: (state, proposal) => {
      state.proposal = proposal;
    },
    SET_TOKEN: (state, token) => {
      state.token = token;
    },
  },
  getters: {
    getProposal(state) {
      return state.proposal;
    },
    getSocket(state) {
      return state.mtsocket;
    },
    getToken(state) {
      return state.token;
    },
  },
  // 체크 필요
  actions: {
    send({ state }, data) {
      console.log("send 실행");
      console.log(data);
      state.mtsocket.send(
        `/ws/msg/indi/${data.command}`,
        JSON.stringify(data.data),
        {}
        // data,
        // JSON.stringify({ msg: "" })
      );
    },
  },
};
