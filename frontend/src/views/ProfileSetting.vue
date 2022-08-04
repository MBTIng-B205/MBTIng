<template>
  <el-row type="flex" justify="space-around">
    <el-col>
      <h1>ProfileSetting</h1>
    </el-col>
    <form>
      <el-col
        style="display: flex; justify-content: center; margin-bottom: 10px"
      >
        <img
          :src="form.memberinfo.profileUrl"
          alt="profile"
          width="100"
          style="border-radius: 50%"
        />
      </el-col>
      <el-form :model="form" label-width="150px">
        <el-form-item label="MBTI">
          <el-input
            v-model="form.mbti"
            readonly
            style="width: 220px"
          ></el-input>
        </el-form-item>
        <el-form-item label="닉네임">
          <el-input v-model="form.nickname" style="width: 220px"></el-input>
          <el-button @click="nameTest">닉네임중복검사</el-button>
        </el-form-item>
        <el-form-item label="성별">
          <el-radio-group v-model="form.gender">
            <el-radio label="남자" value="true" />
            <el-radio label="여자" value="false" />
          </el-radio-group>
        </el-form-item>
        <el-form-item label="생년월일">
          <el-col :span="24">
            <el-date-picker
              type="date"
              placeholder="생년월일"
              v-model="form.birth"
            ></el-date-picker>
          </el-col>
        </el-form-item>
        <el-form-item label="지역" placeholder="please select your zone">
          <el-select v-model="form.sido">
            <el-option label="서울" value="서울" />
            <el-option label="인천" value="인천" />
            <el-option label="경기" value="경기" />
            <el-option label="강원" value="강원" />
            <el-option label="대전" value="대전" />
            <el-option label="대구" value="대구" />
            <el-option label="광주" value="광주" />
            <el-option label="울산" value="울산" />
            <el-option label="부산" value="부산" />
            <el-option label="충남" value="충남" />
            <el-option label="충북" value="충북" />
            <el-option label="전북" value="전북" />
            <el-option label="전남" value="전남" />
            <el-option label="경북" value="경북" />
            <el-option label="경남" value="경남" />
            <el-option label="제주" value="제주" />
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
    let flag = false;
    const signup = function () {
      if (flag === false) {
        alert("닉네임 중복검사를 눌러주세요");
      } else {
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
      }
    };

    const nameTest = function () {
      const state = computed(() => store.getters["accounts/getMember"]);
      console.log("멤버인포", state);
      if (form.nickname === state.value.nickname) {
        alert("중복된 닉네임입니다.");
      } else {
        alert("사용가능한 닉네임입니다.");
        flag = true;
      }
    };

    return { signup, nameTest, form };
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
