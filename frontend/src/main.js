import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import ElementPlus from "element-plus";
import "element-plus/dist/index.css";

console.log(process.env);
console.log(process.env.VUE_APP_KAKAO_LOGIN_REDIRECT_URI);
console.log(process.env.VUE_APP_API_SERVER_BASE_URL);

createApp(App).use(ElementPlus).use(store).use(router).mount("#app");
