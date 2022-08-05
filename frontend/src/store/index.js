import { createStore } from "vuex";
import { accounts } from "@/store/modules/accounts";
import { meetings } from "@/store/modules/meetings";
import { friends } from "@/store/modules/friends";
//import { createPersistedState } from "vuex-persistedstate";

export default createStore({
  modules: { accounts, meetings, friends },
  //plugins: [createPersistedState()],
});
