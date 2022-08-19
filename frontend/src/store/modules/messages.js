import axios from "axios";
const base = {
  baseUrl: `${process.env.VUE_APP_API_SERVER_BASE_URL}/message`,
  headers: {
    "Content-type": "application/json",
  },
};

export const messages = {
  namespaced: true,
  actions: {
    sendMsg({ state }, { senderId, receiverId, content }) {
      console.log(state);
      const params = {
        senderId: senderId,
        receiverId: receiverId,
        content: content,
      };
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
      return axios.post(`${base.baseUrl}/fromlist/${email}`, params, {
        headers: {
          "Content-Type": "application/json",
        },
      });
    },

    deleteSendList({ state }, { list }) {
      console.log(state);
      let del = [];
      for (let i = 0; i < list.length; i++) {
        del.push(list[i]);
      }
      const params = {
        deletedBy: "SENDER",
        deletelist: del,
      };
      return axios.delete(
        `${base.baseUrl}/delete`,
        { data: params },
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
    },

    getReceiveList({ state }, { email, page, key, word, size }) {
      console.log(state);
      const params = {
        page: page,
        searchUtil: {
          key: key,
          word: word,
        },
        size: size,
      };
      return axios.post(`${base.baseUrl}/tolist/${email}`, params, {
        headers: {
          "Content-Type": "application/json",
        },
      });
    },

    deleteReceiveList({ state }, { list }) {
      console.log(state);
      let del = [];
      for (let i = 0; i < list.length; i++) {
        del.push(list[i]);
      }
      const params = {
        deletedBy: "RECEIVER",
        deletelist: del,
      };
      return axios.delete(
        `${base.baseUrl}/delete`,
        { data: params },
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
    },

    readList({ state }, { list }) {
      console.log(state);
      let readList = [];
      for (let index = 0; index < list.length; index++) {
        readList.push(list[index]);
      }
      return axios.put(
        `${base.baseUrl}/read`,
        { readList: readList },
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
    },

    getMessage({ state }, { id, type }) {
      console.log(state);
      return axios.get(`${base.baseUrl}/${id}/${type}`);
    },
  },
};
