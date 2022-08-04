import { createStore } from "vuex";
import { accounts } from "@/store/modules/accounts";
import { meetings } from "@/store/modules/meetings";
import { friends } from "@/store/modules/friends";
export default createStore({
  modules: { accounts, meetings, friends },
});
