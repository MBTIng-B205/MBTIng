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
          style="border-radius: 50%; width: 100px; height: 100px"
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
          <el-button @click="nameCheck">닉네임중복검사</el-button>
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
        <el-form-item label="지역">
          <el-select v-model="form.sido" placeholder="왜안되죠?">
            <el-option
              v-for="item in option"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
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

    const option = [
      {
        value: "서울",
        label: "서울",
      },
      {
        value: "인천",
        label: "인천",
      },
      {
        value: "경기",
        label: "경기",
      },
      {
        value: "강원",
        label: "강원",
      },
      {
        value: "부산",
        label: "부산",
      },
      {
        value: "대전",
        label: "대전",
      },
      {
        value: "울산",
        label: "울산",
      },
      {
        value: "충북",
        label: "충북",
      },
      {
        value: "충남",
        label: "충남",
      },
      {
        value: "세종",
        label: "세종",
      },
      {
        value: "대구",
        label: "대구",
      },
      {
        value: "경북",
        label: "경북",
      },
      {
        value: "경남",
        label: "경남",
      },
      {
        value: "광주",
        label: "광주",
      },
      {
        value: "전북",
        label: "전북",
      },
      {
        value: "전남",
        label: "전남",
      },
      {
        value: "제주",
        label: "제주",
      },
    ];

    const form = reactive({
      mbti: tmpmemberinfo.value.mbti,
      nickname: tmpmemberinfo.value.nickname,
      gender: {},
      birth: {},
      sido: {},
      memberinfo: computed(() => store.getters["accounts/getMember"]),
    });
    // console.log(form);

    let flag = false;
    const nameCheck = function () {
      const nickname = form.nickname;
      console.log("이거는프로필 닉네임", nickname);
      store.dispatch("accounts/getUserName", { nickname }).then(function (res) {
        console.log("res", res);
        if (res.data.body === true) {
          alert("사용가능한 닉네임 입니다.");
          flag = true;
        } else {
          alert("중복 된 닉네임입니다.");
          flag = false;
        }
      });
    };

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
        // console.log(form.memberinfo);
        router.push({ name: "HomeView" });
      }
    };

    return { signup, nameCheck, option, form };
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
