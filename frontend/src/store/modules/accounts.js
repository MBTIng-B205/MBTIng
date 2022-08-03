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
      console.log(memberInfo);
      state.member = memberInfo;
      console.log(state.member);
    },
  },
  getters: {
    getMember(state) {
      console.log("getters ------------------");
      console.log(state);
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

    // saveToken({ commit }, token) {
    //   commit("SET_TOKEN", token);
    //   SessionStorage.setItem("token", token);
    // },
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
      return axios.post(`${base.baseUrl}/v1/users`, params, {
        headers: {
          "Content-Type": "application/json",
        },
      });
    },
    getMemberinfo() {
      let jwt = sessionStorage.getItem("access-token");
      console.log("jwt", jwt);
      axios.defaults.headers.common["Authorization"] = `Bearer ${jwt}`;
      return axios.get(`${base.baseUrl}/v1/users/me`);
    },
  },
};
