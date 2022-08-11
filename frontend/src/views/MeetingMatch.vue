<template>
  <el-container style="background-color: #fff4b8">
    <el-header>
      <img class="logo" src="@/assets/logo.png" />
      <el-button style="float: right; margin-top: 25px" type="danger" round
        >소개팅종료</el-button
      >
    </el-header>
    <el-card
      style="
        text-align: center;
        background-color: #fff4b8;
        box-shadow: none;
        border: none;
      "
    >
      <div style="margin-bottom: 50px">
        <img class="small" src="@/assets/smallpink.png" />
        <div class="infoBox">
          <div style="margin-top: 20px">
            <img
              class="gender"
              v-if="state.proposal.gender == `MALE`"
              src="@/assets/male.png"
            />
            <img class="gender" v-else src="@/assets/female.png" />
            <div>
              <span style="font-size: 100px; font-weight: bold">{{
                state.proposal.mbti
              }}</span>
              <img style="width: 70px; height: 70px" src="@/assets/ask.png" />
            </div>
          </div>
        </div>
        <img class="small" src="@/assets/smallgreen.png" />
      </div>
      <el-row style="flex-direction: row; justify-content: space-evenly">
        <el-button type="success" size="large" @click="proposalAccept"
          >수락</el-button
        >
        <el-button type="danger" size="large" @click="proposalRefuse"
          >거절</el-button
        >
      </el-row>
    </el-card>
  </el-container>
</template>

<script>
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { reactive, computed } from "vue";
export default {
  setup() {
    const store = useStore();
    const router = useRouter();
    const state = reactive({
      memberinfo: computed(() => store.getters["accounts/getMember"]),
      proposal: computed(() => store.getters["meetings/getProposal"]),
      mtsocket: computed(() => store.getters["meetings/getSocket"]),
    });
    const proposalAccept = function () {
      console.log("proposalAccept 실행");
      const msg = {
        command: "proposalResult",
        data: {
          proposalResult: true,
        },
      };
      console.log(msg);
      store.dispatch("meetings/send", msg);
    };

    const proposalRefuse = function () {
      console.log("proposalRefuse 실행");
      const msg = {
        command: "rejoin",
        data: {},
      };
      console.log(msg);
      store.dispatch("meetings/send", msg);
      router.push({ name: "MeetingWait" });
    };
    return { state, proposalAccept, proposalRefuse };
  },
};
</script>

<style>
.logo {
  width: 250px;
}
.small {
  width: 200px;
  height: 250px;
  vertical-align: bottom;
}

.gender {
  width: 50px;
  height: 50px;
}

.infoBox {
  display: inline-block;
  text-align: center;
  padding: 10px;
  height: 275px;
  width: 600px;
  border: 20px solid deeppink;
  background-color: white;
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.12);
}

.el-loading-text {
  font-weight: bold;
}

.el-loading-spinner {
  color: black;
}
</style>
