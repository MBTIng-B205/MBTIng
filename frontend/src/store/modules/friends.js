import axios from "axios";
const base = {
  baseUrl: "http://localhost:8080/api/friend",
  headers: {
    "Content-type": "application/json",
  },
};

export const friends = {
  namespaced: true,
  actions: {
    getFriendsList({ state }, email) {
      console.log("getFriendList");
      console.log("state", state);
      console.log("axios", email);
      return axios.get(`${base.baseUrl}?email=${email}`);
    },
  },
};
