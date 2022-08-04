import { createRouter, createWebHistory } from "vue-router";
import MBTITest from "@/views/MBTITest";
import MBTISetting from "@/views/MBTISetting";
import ProfileSetting from "@/views/ProfileSetting";
import HomeView from "@/views/HomeView";
import LoginView from "@/views/LoginView";
import People from "@/views/PeoplePage";
import MBTIResult from "@/views/MBTIResult";
import MeetingWait from "@/views/MeetingWait";
import MeetingMatch from "@/views/MeetingMatch";
import MyPage from "@/views/MyPage";
import LogoutView from "@/views/LogoutView";

const routes = [
  {
    path: "/",
    name: "HomeView",
    component: HomeView,
  },
  {
    path: "/loginview",
    name: "LoginView",
    component: LoginView,
  },
  {
    path: "/logoutview",
    name: "LogoutView",
    component: LogoutView,
  },
  {
    path: "/mypage",
    name: "MyPage",
    component: MyPage,
  },

  {
    path: "/mbtisetting",
    name: "MBTISetting",
    component: MBTISetting,
  },
  {
    path: "/mbtitest",
    name: "MBTITest",
    component: MBTITest,
  },
  {
    path: "/mbtiresult",
    name: "MBTIResult",
    component: MBTIResult,
  },
  {
    path: "/profilesetting",
    name: "ProfileSetting",
    component: ProfileSetting,
  },
  {
    path: "/people",
    name: "People",
    component: People,
    children: [
      {
        path: "friend",
        name: "friend",
        component: () => import("@/components/people-friend.vue"),
      },
      {
        path: "sendMessage",
        name: "sendMessage",
        component: () => import("@/components/send-message.vue"),
      },
      {
        path: "receiveMessage",
        name: "receiveMessage",
        component: () => import("@/components/receive-message"),
      },
    ],
  },
  {
    path: "/meetingwait",
    name: "MeetingWait",
    component: MeetingWait,
  },
  {
    path: "/meetingmatch",
    name: "/MeetingMatch",
    component: MeetingMatch,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
