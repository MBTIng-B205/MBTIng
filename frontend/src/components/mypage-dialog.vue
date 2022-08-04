<template>
  <el-dialog
    style="background-color: black"
    v-model="state.mypageDialog"
    @close="handleClose"
  >
    <img class="profile" :src="state.memberinfo.profileUrl" />
    <el-form :model="state.memberinfo">
      <el-form-item>
        <el-input v-model="state.memberinfo.mbti" />
      </el-form-item>
      <el-form-item>
        <el-input v-model="state.memberinfo.nickname" />
      </el-form-item>
      <el-form-item>
        <el-radio-group v-model="state.memberinfo.gender">
          <el-radio :label="true">Male</el-radio>
          <el-radio :label="false">Female</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-date-picker v-model="state.memberinfo.birth" type="date" />
      </el-form-item>
      <el-form-item>
        <el-select v-model="state.memberinfo.sido">
          <el-option label="서울" value="서울" />
          <el-option label="대전" value="대전" />
        </el-select>
      </el-form-item>
    </el-form>
    <el-footer>
      <el-button @click="updateInfo">수정</el-button>
      <el-button @click="deleteMember" type="danger">탈퇴</el-button>
    </el-footer>
  </el-dialog>
</template>

<script>
import { useStore } from "vuex";
import { ref, onMounted } from "vue";
import { computed, reactive } from "@vue/runtime-core";
export default {
  props: {
    open: {
      type: Boolean,
      default: false,
    },
  },

  setup(props, { emit }) {
    onMounted(() => {
      console.log("mypage");
      console.log("open", props.open);
    });

    const store = useStore();
    const mypageForm = ref(null);
    const state = reactive({
      memberinfo: computed(() => store.getters["accounts/getMember"]),
      mypageDialog: computed(() => props.open),
    });

    const handleClose = function () {
      emit("closeMyPage");
    };

    const updateInfo = function () {
      // 회원정보 수정
    };

    const deleteMember = function () {
      // 회원 탈퇴
    };

    return { state, mypageForm, handleClose, updateInfo, deleteMember };
  },
};
</script>

<style>
.profile {
  border-radius: 50%;
  width: 100px;
  height: 100px;
}
</style>
