import axios from "axios";
const base = {
  baseUrl: `${process.env.VUE_APP_API_SERVER_BASE_URL}/friend`,
  headers: {
    "Content-type": "application/json",
  },
};

export const friends = {
  namespaced: true,
  actions: {
    getFriendsList({ state }, email) {
      console.log("state", state);
      return axios.get(`${base.baseUrl}/?email=${email}`);
    },
    getFriendByName({ state }, { nickname, email }) {
      console.log("state", state);
      return axios.get(`${base.baseUrl}/`, {
        params: {
          email: email,
          nickname: nickname,
        },
      });
    },
    getFriendByMbti({ state }, { mbti, email }) {
      console.log("state", state);
      return axios.get(`${base.baseUrl}/`, {
        params: {
          email: email,
          mbti: mbti,
        },
      });
    },
    addFriend({ state }, { to, from }) {
      console.log(state);
      return axios.post(`${base.baseUrl}/${from}/${to}`);
    },
    deleteFriend({ state }, { to, from }) {
      console.log("state", state);
      return axios.delete(`${base.baseUrl}/${from}/${to}`);
    },
  },
};
