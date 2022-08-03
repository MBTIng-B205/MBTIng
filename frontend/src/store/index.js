import { createStore } from "vuex";
import { accounts } from "@/store/modules/accounts";
import { meetings } from "@/store/modules/meetings";

export default createStore({
  modules: { accounts, meetings },
});
