// import $axios from "axios";

export const meetings = {
  namespaced: true,
  state: {
    mtsocket: null,
    proposal: null,
    token: null,
    chats: [],
    partner: null,
    videoflag: false,
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
    SET_PARTNER: (state, partner) => {
      state.partner = partner;
    },
    SAVE_CHAT: (state, chats) => {
      console.log(state);
      console.log(chats.chats);

      state.chats = chats.chats;
    },
    SET_VIDEOFLAG: (state, videoflag) => {
      state.videoflag = videoflag;
    },
  },
  getters: {
    getPartner(state) {
      return state.partner;
    },
    getProposal(state) {
      return state.proposal;
    },
    getSocket(state) {
      return state.mtsocket;
    },
    getToken(state) {
      return state.token;
    },
    getChats(state) {
      return state.chats;
    },
    getVideoflag(state) {
      return state.videoflag;
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
