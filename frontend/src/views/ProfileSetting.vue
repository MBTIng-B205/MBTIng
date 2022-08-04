<template>
  <el-row type="flex" justify="space-around">
    <el-col>
      <h1>ProfileSetting</h1>
    </el-col>
    <form>
      <el-col flex-direction="column">
        <img
          :src="form.memberinfo.profileUrl"
          alt="profile"
          :span="10"
          width="100"
        />
      </el-col>
      <el-form label-width="120px">
        <el-form-item label="MBTI">
          <el-input v-model="form.mbti" readonly></el-input>
        </el-form-item>
        <el-form-item label="Nickname">
          <el-input v-model="form.nickname"></el-input>
        </el-form-item>
        <el-form-item label="Gender">
          <el-select v-model="form.gender" placeholder="Select">
            <el-option label="Male" value="true"></el-option>
            <el-option label="Female" value="false"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Birthday">
          <el-col :span="24">
            <el-date-picker
              type="date"
              placeholder="Pick a date"
              v-model="form.birth"
              style="width: 100%"
            ></el-date-picker>
          </el-col>
        </el-form-item>
        <el-form-item label="Place">
          <el-select v-model="form.sido" placeholder="Select">
            <el-option label="Seoul" value="seoul"></el-option>
            <el-option label="Daejeon" value="daejeon"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="Medium" @click="signup"
            >확인</el-button
          >
        </el-form-item>
      </el-form>
    </form>
  </el-row>
</template>

<script>
import { computed, reactive } from "vue";
// import axios from "axios";
import { useStore } from "vuex";
// import { onMounted } from "vue";
import { useRouter } from "vue-router";

export default {
  setup() {
    const router = useRouter();
    const store = useStore();
    const tmpmemberinfo = computed(() => store.getters["accounts/getMember"]);
    const form = reactive({
      mbti: tmpmemberinfo.value.mbti,
      nickname: tmpmemberinfo.value.nickname,
      gender: {},
      birth: {},
      sido: {},
      memberinfo: computed(() => store.getters["accounts/getMember"]),
    });
    console.log(form);

    const signup = function () {
      form.memberinfo.nickname = form.nickname;
      form.memberinfo.gender = form.gender;
      form.memberinfo.birth = form.birth.toISOString().slice(0, 10);
      form.memberinfo.sido = form.sido;
      //form.memberinfo.nickname = form.nickname;
      store.commit("accounts/SET_MEMBER_INFO", form.memberinfo);
      //date => date.toISOString().slice(0, 10);
      store.dispatch("accounts/signup");
      console.log(form.memberinfo);
      router.push({ name: "HomeView" });
    };

    return { signup, form };
  },
};
</script>

<style>
.el-row {
  display: flex;
  flex-wrap: wrap;
  position: relative;
  box-sizing: border-box;
  align-items: center;
  justify-content: space-evenly;
  align-content: center;
  flex-direction: column;
}
</style>
