import { createRouter, createWebHistory } from "vue-router";
import MBTITest from "@/views/MBTITest";
import MBTISetting from "@/views/MBTISetting";
import ProfileSetting from "@/views/ProfileSetting";
import KakaoLogin from "@/views/KakaoLogin";
import HomeView from "@/views/HomeView";
const routes = [
  {
    path: "/",
    name: "KakaoLogin",
    component: KakaoLogin,
  },
  {
    path: "/MbtiTest",
    name: "MBTITest",
    component: MBTITest,
  },
  {
    path: "/HomeView",
    name: "HomeView",
    component: HomeView,
  },
  {
    path: "/MbtiSetting",
    name: "MBTISetting",
    component: MBTISetting,
  },
  {
    path: "/ProfileSetting",
    name: "ProfileSetting",
    component: ProfileSetting,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
