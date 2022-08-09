import { createStore } from "vuex";
import { accounts } from "@/store/modules/accounts";
import { meetings } from "@/store/modules/meetings";
import { friends } from "@/store/modules/friends";
import { messages } from "@/store/modules/messages";
//import { createPersistedState } from "vuex-persistedstate";

export default createStore({
  modules: { accounts, meetings, friends, messages },
  //plugins: [createPersistedState()],
});
