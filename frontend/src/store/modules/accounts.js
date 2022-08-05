import axios from "axios";
const base = {
  baseUrl: "http://localhost:8080/api",
  headers: {
    "Content-type": "application/json",
  },
};

export const accounts = {
  namespaced: true,
  state: {
    member: null,
    token: "",
    authError: null,
  },
  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token;
    },
    SET_MEMBER_INFO: (state, memberInfo) => {
      state.member = memberInfo;
    },
  },
  getters: {
    getMember(state) {
      return state.member;
    },
  },
  actions: {
    checkCode({ state }, { code }) {
      console.log("state", state);
      console.log("axios", code);
      //const url = "api/login";
      return axios.get(`${base.baseUrl}/login?code=${code}`);
    },

    signup({ state }) {
      const params = {
        email: state.member.email,
        nickname: state.member.nickname,
        gender: state.member.gender,
        birth: state.member.birth,
        sido: state.member.sido,
        mbti: state.member.mbti,
        profileUrl: state.member.profileUrl,
      };

      let jwt = sessionStorage.getItem("access-token");
      console.log(jwt);
      console.log(params);
      return axios.post(`${base.baseUrl}/users`, params, {
        headers: {
          "Content-Type": "application/json",
        },
      });
    },

    getMemberinfo() {
      let jwt = sessionStorage.getItem("access-token");
      console.log("jwt", jwt);
      axios.defaults.headers.common["Authorization"] = `Bearer ${jwt}`;
      return axios.get(`${base.baseUrl}/users/me`);
    },

    getUserName({ state }, { nickname }) {
      console.log("state", state);
      console.log("nick", nickname);
      return axios.get(`${base.baseUrl}/users/`, {
        params: {
          nickname: nickname,
        },
      });
    },
  },
};
