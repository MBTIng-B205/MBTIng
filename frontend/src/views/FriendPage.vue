<template>
  <div>
    <el-container id="upperContainer">
      <el-aside width="50%" class="profilepage"
        ><img src="@/assets/profile_pic.png" alt="profile" id="pic" />
      </el-aside>
      <el-main class="profilepage">
        <el-form :model="form">
          <el-form-item id="input">
            <el-input v-model="form.mbti" autocomplete="off" size="large" />
          </el-form-item>
          <el-form-item id="input">
            <el-input v-model="form.nickname" autocomplete="off" size="large" />
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
    <el-container id="lowerContainer">
      <el-aside width="50%" class="profilepage" id="lowerAsid">
        <h2>Gender</h2>
        <h2>Birthday</h2>
        <h2>Place</h2>
      </el-aside>
      <el-main class="profilepage">
        <el-form>
          <el-form-item id="input">
            <el-select v-model="form.gender" id="gender_input" size="large">
              <el-option label="Male" value="Male" />
              <el-option label="Female" value="Female" />
            </el-select>
          </el-form-item>
          <el-form-item id="input">
            <el-date-picker
              v-model="form.birthday"
              type="date"
              placeholder="Pick a birthday"
              size="large"
            />
          </el-form-item>
          <el-form-item id="input">
            <el-select v-model="form.place" size="large">
              <el-option label="Seoul" value="Seoul" />
              <el-option label="Daejeon" value="Daejeon" />
            </el-select>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
    <el-container id="footerContainer">
      <el-button>수정</el-button>
      <el-button @click="goHome">나가기</el-button>
    </el-container>
  </div>
</template>

<script>
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { reactive, computed, ref } from "vue";

export default {
  setup() {
    const store = useStore();
    const router = useRouter();
    const state = reactive({
      memberinfo: computed(() => store.getters["accounts/getMember"]),
    });
    console.log(state.memberinfo);

    const form = reactive({
      mbti: ref("ENFP"),
      nickname: ref("Jessi"),
      gender: ref("Female"),
      place: ref("Seoul"),
      birthday: ref("2022-10-11"),
      img_input: ref("@/assets/profile_pic.png"),
    });
    const goHome = function () {
      router.push({ name: "HomeView" });
    };
    return { form, goHome };
  },
};
</script>

<style scoped>
#input {
  width: 200px;
}
.el-aside {
  display: flex;
  flex-wrap: wrap;
  position: relative;
  align-items: flex-start;
  justify-content: space-evenly;
  flex-direction: column;
}
.profilepage {
  background-color: #fff4b8;
  margin: 0px;
  padding: 0px;
  align-content: space-around;
}
#upperContainer {
  padding-bottom: 0px;
  padding-top: 10px;
  background-color: #fff4b8;
}
#lowerContainer,
#footerContainer {
  padding-bottom: 10px;
}
#lowerContainer,
#footerContainer {
  margin-top: 0px;
  justify-content: center;
  background-color: #fff4b8;
}
#lowerAsid > h2 {
  margin-top: 0%;
}
#lowerAside {
  text-align: right;
  align-content: flex-end;
}
#pic {
  width: 130px;
  border-radius: 200px;
}
</style>
