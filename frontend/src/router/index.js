import { createRouter, createWebHistory } from "vue-router";
import MyMBTITest from "@/views/MyMBTITest";
import MyMBTIResult from "@/views/MyMBTIResult";
import YourMBTITest from "@/views/YourMBTITest";
import YourMBTIResult from "@/views/YourMBTIResult";
import MBTIWorldCup from "@/views/MBTIWorldCup";
import MBTIWorldCupResult from "@/views/MBTIWorldCupResult";
import MBTISetting from "@/views/MBTISetting";
import ProfileSetting from "@/views/ProfileSetting";
import HomeView from "@/views/HomeView";
import LoginView from "@/views/LoginView";
import People from "@/views/PeoplePage";
import MeetingWait from "@/views/MeetingWait";
import MeetingMatch from "@/views/MeetingMatch";
import LogoutView from "@/views/LogoutView";
import MeetingRoom from "@/views/MeetingRoom";

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
    path: "/mbtisetting",
    name: "MBTISetting",
    component: MBTISetting,
  },
  {
    path: "/mymbtitest",
    name: "MyMBTITest",
    component: MyMBTITest,
  },
  {
    path: "/mymbtiresult",
    name: "MyMBTIResult",
    component: MyMBTIResult,
  },
  {
    path: "/yourmbtitest",
    name: "YourMBTITest",
    component: YourMBTITest,
  },
  {
    path: "/yourmbtiresult",
    name: "YourMBTIResult",
    component: YourMBTIResult,
  },
  {
    path: "/mbtiworldcup",
    name: "MBTIWorldCup",
    component: MBTIWorldCup,
  },
  {
    path: "/mbtiworldcupresult",
    name: "MBTIWorldCupResult",
    component: MBTIWorldCupResult,
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
  {
    path: "/room",
    name: "room",
    component: MeetingRoom,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
