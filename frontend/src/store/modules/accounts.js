import axios from "axios";
const base = {
  baseUrl: process.env.VUE_APP_API_SERVER_BASE_URL,
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
        interests: state.member.interests,
      };

      return axios.post(`${base.baseUrl}/users`, params, {
        headers: {
          "Content-Type": "application/json",
        },
      });
    },
    profileUpload({ state }, upfile) {
      let formData = new FormData();
      formData.append("upfile", upfile);
      return axios.post(
        `${base.baseUrl}/users/userprofile/${state.member.email}`,
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );
    },
    getMemberinfo() {
      let jwt = sessionStorage.getItem("access-token");
      axios.defaults.headers.common["Authorization"] = `Bearer ${jwt}`;
      return axios.get(`${base.baseUrl}/users/me`);
    },
    updateMemberinfo(
      { state },
      { mbti, interests, nickname, profileUrl, sido }
    ) {
      console.log(state);
      const params = {
        email: state.member.email,
        interests: interests,
        mbti: mbti,
        nickname: nickname,
        profileUrl: profileUrl,
        sido: sido,
      };
      return axios.put(`${base.baseUrl}/users`, params);
    },
    deleteMemberinfo({ state }) {
      return axios.delete(`${base.baseUrl}/users/?email=${state.member.email}`);
    },
    getUserName({ state }, { nickname }) {
      console.log("state", state);
      return axios.get(`${base.baseUrl}/users/`, {
        params: {
          nickname: nickname,
        },
      });
    },
  },
};
