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

    getSendList({ state }, { email, page, key, word, size }) {
      console.log(state);
      const params = {
        page: page,
        searchUtil: {
          key: key,
          word: word,
        },
        size: size,
      };
      console.log("getSendList", email + " " + params);
      return axios.post(`${base.baseUrl}/fromlist/${email}`, params, {
        headers: {
          "Content-Type": "application/json",
        },
      });
    },

    deleteSendList({ state }, { list }) {
      console.log(state);
      console.log(list);
      const params = {
        deletedBy: "SENDER",
        deletelist: [201],
      };
      console.log("delete", params);
      return axios.delete(`${base.baseUrl}/delete`, params, {
        headers: {
          "Content-Type": "application/json",
        },
      });
    },
  },
};
