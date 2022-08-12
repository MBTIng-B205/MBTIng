import { createStore } from "vuex";
import { accounts } from "@/store/modules/accounts";
import { meetings } from "@/store/modules/meetings";
import { friends } from "@/store/modules/friends";
import { messages } from "@/store/modules/messages";
import { reports } from "@/store/modules/reports";
//import { createPersistedState } from "vuex-persistedstate";

export default createStore({
  modules: { accounts, meetings, friends, messages, reports },
  //plugins: [createPersistedState()],
});
