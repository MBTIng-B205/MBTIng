import axios from "axios";
const base = {
  baseUrl: "http://localhost:8080/api/message",
  headers: {
    "Content-type": "application/json",
  },
};

export const messages = {
  namespaced: true,
  actions: {
    sendMsg({ state }, { senderId, receiverId, content }) {
      console.log(state);
      console.log("msg", senderId + " " + receiverId + " " + content);
      const params = {
        senderId: senderId,
        receiverId: receiverId,
        content: content,
      };
      console.log("sendMSg-axios", params);
      return axios.post(`${base.baseUrl}/`, params, {
        headers: {
          "Content-Type": "application/json",
        },
      });
    },
  },
};
