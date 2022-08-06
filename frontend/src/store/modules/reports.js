import axios from "axios";
const base = {
  baseUrl: "http://localhost:8080/api/report",
  headers: {
    "Content-type": "application/json",
  },
};

export const reports = {
  namespaced: true,
  actions: {
    registerReport({ state }, { to, from, content }) {
      console.log(state);
      console.log("registerReport");
      const params = {
        to_id: to,
        from_id: from,
        content: content,
      };
      return axios.post(`${base.baseUrl}/`, params, {
        headers: {
          "Content-Type": "application/json",
        },
      });
    },
  },
};
