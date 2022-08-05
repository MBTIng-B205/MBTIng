import axios from "axios";
const base = {
  baseUrl: "http://localhost:8080/api/message",
  headers: {
    "Content-type": "application/json",
  },
};

export const message = {
  namespaced: true,
  actions: {
    sendMsg({ msg }) {
      const params = {
        senderId: msg.fromFriend,
        receiverId: msg.toFriend,
        content: msg.content,
      };
      console.log("sendMSg", msg);
      return axios.post(`${base.baseUrl}/`, params, {
        headers: {
          "Content-Type": "application/json",
        },
      });
    },
  },
};
