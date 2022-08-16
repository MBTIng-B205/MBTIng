// import $axios from "axios";

export const meetings = {
  namespaced: true,
  state: {
    ovsocket: null,
    mtsocket: null,
    proposal: null,
    token: null,
    chats: [],
    partner: null,
    videoflag: false,
    alertdialog: false,
    alertmsg: null,
    alertcommand: null,
    chataddflag: false,
  },
  mutations: {
    SET_CHATADDFLAG: (state, chataddflag) => {
      state.chataddflag = chataddflag;
    },
    SET_ALERTCOMMAND: (state, alertcommand) => {
      state.alertcommand = alertcommand;
    },
    SET_ALERTMSG: (state, alertmsg) => {
      state.alertmsg = alertmsg;
    },
    SET_ALERTDIALOG: (state, alertdialog) => {
      state.alertdialog = alertdialog;
    },
    SET_OVSOCKET: (state, ovsocket) => {
      state.ovsocket = ovsocket;
    },
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
    getChataddflag(state) {
      return state.chataddflag;
    },
    getAlertcommand(state) {
      return state.alertcommand;
    },
    getAlertdialog(state) {
      return state.alertdialog;
    },
    getAlertmsg(state) {
      return state.alertmsg;
    },
    getOvsocket(state) {
      return state.ovsocket;
    },
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
