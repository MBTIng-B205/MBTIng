<template>
  <el-container style="background-color: #fadce1">
    <el-header>
      <img class="logo" src="@/assets/logo.png" alt="logo" />
    </el-header>
    <el-card style="text-align: center; padding: 0">
      <el-row type="flex" justify="space-around">
        <el-header style="font-size: x-large"
          >자신의 프로필을 작성해주세요.</el-header
        >
        <form>
          <el-col
            style="display: flex; justify-content: center; margin-bottom: 5px"
          >
            <img
              :src="form.memberinfo.profileUrl"
              alt="profile"
              style="border-radius: 50%; width: 100px; height: 100px"
            />
          </el-col>
          <el-form :model="form" label-width="120px" style="width: 500px">
            <el-form-item style="margin-bottom: 10px" label="MBTI">
              <el-input
                v-model="form.mbti"
                readonly
                style="width: 220px; margin-left: 30px"
              ></el-input>
            </el-form-item>
            <el-form-item label="닉네임" style="margin-bottom: 10px">
              <el-input
                v-model="form.nickname"
                style="width: 220px; margin-left: 30px"
              ></el-input>
              <el-button
                @click="nameCheck"
                style="margin-left: 10px"
                size="large"
                >중복확인</el-button
              >
            </el-form-item>
            <el-form-item label="성별" style="margin-bottom: 10px">
              <el-radio-group
                v-model="form.gender"
                style="margin-left: 30px"
                type="danger"
              >
                <el-radio label="MALE">남자</el-radio>
                <el-radio label="FEMALE">여자</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="생년월일" style="margin-bottom: 10px">
              <el-date-picker
                type="date"
                placeholder="생년월일"
                v-model="form.birth"
                style="margin-left: 30px"
              ></el-date-picker>
            </el-form-item>
            <el-form-item label="사는지역" style="margin-bottom: 10px">
              <el-select
                v-model="form.sido"
                placeholder="거주 지역을 선택하세요"
                style="margin-left: 30px"
              >
                <el-option
                  v-for="item in option"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
            <el-col style="flex-direction: column">
              <el-form-item label="관심사" style="margin-bottom: 10px">
                <el-checkbox-group
                  v-model="form.interests"
                  style="width: 380px; align-items: center"
                >
                  <el-checkbox label="캠핑" name="캠핑" />
                  <el-checkbox label="맛집탐방" name="맛집탐방" />
                  <el-checkbox label="코딩" name="코딩" />
                  <el-checkbox label="TV/영화" name="TV/영화" />
                  <el-checkbox label="스포츠" name="스포츠" />
                  <el-checkbox label="술" name="술" />
                  <el-checkbox label="음악" name="음악" />
                  <el-checkbox label="쇼핑" name="쇼핑" />
                  <el-checkbox label="자동차" name="자동차" />
                  <el-checkbox label="게임" name="게임" />
                  <el-checkbox label="동물" name="동물" />
                  <el-checkbox label="패션" name="패션" />
                  <el-checkbox label="뷰티" name="뷰티" />
                  <el-checkbox label="디자인" name="디자인" />
                </el-checkbox-group>
              </el-form-item>
            </el-col> </el-form
          ><el-button type="danger" plain size="large" @click="signup" round
            >확인</el-button
          >
        </form>
      </el-row>
    </el-card>
  </el-container>
</template>

<script>
import { computed, reactive } from "vue";
import { useStore } from "vuex";
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
      profileUrl: "",
      mbti: tmpmemberinfo.value.mbti,
      nickname: tmpmemberinfo.value.nickname,
      gender: {},
      birth: {},
      sido: "",
      interests: [],
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
        form.memberinfo.interests = form.interests;
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
.el-radio__input.is-checked .el-radio__inner {
  border-color: #f56c6c;
  background: #f56c6c;
}
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
.el-form-item {
  --el-color-primary: #f56c6c;
  --el-color-primary-light-3: #f89898;
  --el-color-primary-light-5: #fab6b6;
  --el-color-primary-light-7: #fcd3d3;
  --el-color-primary-light-8: #fde2e2;
  --el-color-primary-light-9: #fef0f0;
  --el-color-primary-dark-2: #c45656;
}
</style>
