import $axios from "axios";

export const accounts = {
  namespaced: true,
  state: () => ({}),
  mutations: {},
  getters: {},
  actions: {
    checkCode({ state }, { code }) {
      console.log("state", state);
      console.log("axios", code);
      //const url = "api/login";
      return $axios.get(`http://localhost:8080/api/login?code=${code}`);
    },
  },
};
