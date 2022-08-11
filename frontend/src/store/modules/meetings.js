// import $axios from "axios";

export const meetings = {
  namespaced: true,
  state: () => ({
    chats: [],
  }),
  mutations: {
    SAVE_CHAT: (state, chats) => {
      console.log(state);
      console.log(chats.chats);

      state.chats = chats.chats;
    },
  },
  getters: {
    getChats(state) {
      return state.chats;
    },
  },
  actions: {},
};
