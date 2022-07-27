<template>
  <el-row type="flex" justify="space-around">
    <el-col>
      <h1>ProfileSetting</h1>
    </el-col>
    <el-col flex-direction="column">
      <img
        src="https://t3.ftcdn.net/jpg/03/46/83/96/360_F_346839683_6nAPzbhpSkIpb8pmAwufkC7c5eD7wYws.jpg"
        alt="profile"
        :span="10"
        width="100"
      />
    </el-col>
    <el-form label-width="120px">
      <el-form-item label="MBTI">
        <el-input
          v-model="form.mbti"
          :value="$route.params.mbti"
          readonly
        ></el-input>
      </el-form-item>
      <el-form-item label="Nickname">
        <el-input v-model="form.nickname"></el-input>
      </el-form-item>
      <el-form-item label="Gender">
        <el-select v-model="form.gender" placeholder="Select">
          <el-option label="Male" value="male"></el-option>
          <el-option label="Female" value="female"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="Birthday">
        <el-col :span="24">
          <el-date-picker
            type="date"
            placeholder="Pick a date"
            v-model="form.birthday"
            style="width: 100%"
          ></el-date-picker>
        </el-col>
      </el-form-item>
      <el-form-item label="Place">
        <el-select v-model="form.place" placeholder="Select">
          <el-option label="Seoul" value="seoul"></el-option>
          <el-option label="Daejeon" value="daejeon"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="Medium" @click="onSubmit"
          >확인</el-button
        >
      </el-form-item>
    </el-form>
  </el-row>
</template>

<script>
export default {
  data() {
    return {
      form: {
        mbti: "",
        nickname: "",
        gender: "",
        place: "",
        birthday: "",
      },
    };
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
