// import $axios from "axios";

export const meetings = {
  namespaced: true,
  actions: {},
  state: {
    mtsocket: null,
    proposal: null,
  },
  mutations: {
    SET_SOCKET: (state, mtsocket) => {
      state.mtsocket = mtsocket;
    },
    SET_PROPOSAL: (state, proposal) => {
      state.proposal = proposal;
    },
  },
  getters: {
    getProposal(state) {
      return state.proposal;
    },
  },
};
