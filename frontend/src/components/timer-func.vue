<template>
  <div class="flex justify-center">
    <div v-if="state.time.count" class="flex justify-center">
      <div class="">
        <div class="content-center mt-1">
          <vue-countdown
            v-if="state.counting"
            class="border-2 border-main-200 px-4 rounded-xl text-2xl font-semibold"
            :time="state.min * 60 * 1000 + state.sec * 1000"
            @end="onCountdownEnd"
            v-slot="{ minutes, seconds }"
          >
            {{ minutes }} : {{ seconds }}
          </vue-countdown>
          <div
            v-else
            class="border-2 border-main-200 px-4 rounded-xl text-2xl font-semibold"
          >
            {{ state.min }} : {{ state.sec }}
          </div>
        </div>
        <div class="mb-1 mt-1">
          <button
            v-if="state.counting"
            class="bg-main-200 border-2 border-main-200 w-16 rounded-full font font-semibold text-black"
            @click="stopCountdown"
          >
            stop
          </button>
          <button
            v-else
            type="button"
            class="bg-main-200 border-2 border-main-200 w-16 rounded-full font-semibold text-black"
            @click="sendTimer"
          >
            start
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style></style>

<script>
import { computed, reactive } from "vue";
import { useStore } from "vuex";
import { swal } from "@/assets/js/common";

export default {
  setup(props, { emit }) {
    const store = useStore();
    const state = reactive({
      time: computed(() => store.getters["meetings/getTimerTime"]),
      min: 0,
      sec: 0,
      counting: false,
    });
    const startCountdown = (timerData) => {
      let timer = JSON.parse(timerData);
      state.min = timer.min;
      state.sec = timer.sec;
      state.time.count = timer.count;
      state.counting = timer.counting;
    };

    const sendTimer = async () => {
      emit("startCountdown", {
        timer: {
          min: state.time.min,
          sec: state.time.sec,
          count: state.time.count,
          counting: true,
        },
      });
    };
    const onCountdownEnd = () => {
      let alarm = new Audio(
        "https://t1.daumcdn.net/cfile/tistory/99412B355CF6B93806?original"
      );
      // https://t1.daumcdn.net/cfile/tistory/991DD6475CF6C09E07?original
      alarm.play();
      state.counting = false;
      store.commit("root/setTimer", {
        min: 0,
        sec: 0,
        count: false,
      });
      // 타이머 시간 초기화
      state.time.min = null;
      state.time.sec = null;

      swal(true, "top-right", 1500, "info", "타이머가 종료되었습니다.", null);
    };
    const stopCountdown = () => {
      state.counting = false;
      emit("startCountdown", {
        timer: {
          min: state.time.min,
          sec: state.time.sec,
          count: true,
          counting: state.counting,
        },
      });
    };
    return { state, startCountdown, onCountdownEnd, stopCountdown, sendTimer };
  },
};
</script>
