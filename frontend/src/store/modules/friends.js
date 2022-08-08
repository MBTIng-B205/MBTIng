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
      return axios.get(`${base.baseUrl}/?email=${email}`);
    },
    getFriendByName({ state }, { nickname, email }) {
      console.log("getFriendsByName");
      console.log("state", state);
      console.log("axios", nickname);
      return axios.get(`${base.baseUrl}/`, {
        params: {
          email: email,
          nickname: nickname,
        },
      });
    },
    getFriendByMbti({ state }, { mbti, email }) {
      console.log("getFriendByMbti");
      console.log("state", state);
      console.log("axios", mbti);
      return axios.get(`${base.baseUrl}/`, {
        params: {
          email: email,
          mbti: mbti,
        },
      });
    },
    addFriend({ state }, { to, from }) {
      console.log(state);
      console.log("addFriend", to + " " + from);
      return axios.post(`${base.baseUrl}/${from}/${to}`);
    },
    deleteFriend({ state }, { to, from }) {
      console.log("deleteFriend");
      console.log("state", state);
      console.log("delete", to + " " + from);
      return axios.delete(`${base.baseUrl}/${from}/${to}`);
    },
  },
};
