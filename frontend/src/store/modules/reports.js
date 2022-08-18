import axios from "axios";
const base = {
  baseUrl: `${process.env.VUE_APP_API_SERVER_BASE_URL}/report`,
  headers: {
    "Content-type": "application/json",
  },
};

export const reports = {
  namespaced: true,
  actions: {
    registerReport({ state }, { to, from, content }) {
      console.log(state);
      const params = {
        to_id: to,
        from_id: from,
        content: content,
      };
      return axios.post(`${base.baseUrl}`, params, {
        headers: {
          "Content-Type": "application/json",
        },
      });
    },
  },
};
