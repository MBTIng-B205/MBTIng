<template>
  <el-container style="background-color: #fadce1">
    <el-header>
      <img class="logo" @click="goHome" src="@/assets/logo.png" alt="logo" />
    </el-header>
    <el-card style="padding: 0px; margin-bottom: 0px; margin-top: 30px">
      <el-row><div class="title">MBTI 별 매칭 성공률</div></el-row>
      <div style="margin-top: 10px">
        <div class="outline" style="margin-left: 200px; display: inline-block">
          <el-select v-model="mbti" @change="changeChart">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </div>
        <span class="rank">
          1. <span class="rank-mbti"> {{ first }} </span> 2.
          <span class="rank-mbti"> {{ second }} </span> 3.
          <span class="rank-mbti"> {{ third }} </span>
        </span>
      </div>
      <div class="chart">
        <div
          class="hidden-box"
          style="
            background-color: white;
            z-index: 10;
            height: 20px;
            width: 100px;
            position: absolute;
            left: 47%;
            top: 26%;
          "
        ></div>
        <vue3-chart-js
          ref="chartRef"
          :id="lineChart.id"
          :type="lineChart.type"
          :data="lineChart.data"
        ></vue3-chart-js>
      </div>
    </el-card>
  </el-container>
</template>

<script>
import Vue3ChartJs from "@j-t-mcc/vue3-chartjs";
import { useRouter } from "vue-router";
import { reactive, ref, computed, onMounted } from "vue";
import { useStore } from "vuex";
export default {
  components: {
    Vue3ChartJs,
  },
  setup() {
    const router = useRouter();
    var mbti = ref(0);
    const first = ref("ESTP");
    const second = ref("ESFP");
    const third = ref("ISTP");
    const chartRef = ref(null);
    const store = useStore();
    const state = reactive({
      userMbti: "",
    });
    onMounted(() => {
      store.dispatch("accounts/getMemberinfo").then(function (res) {
        store.commit("accounts/SET_MEMBER_INFO", res.data.body);
        state.userMbti = computed(
          () => store.getters["accounts/getMember"].mbti
        );
        if (state.userMbti != null) preSetting();
      });
    });
    const lineChart = {
      id: "bar",
      type: "bar",
      data: {
        labels: [
          "INFP",
          "ENFP",
          "ENFJ",
          "INTJ",
          "INTP",
          "ENTP",
          "ENTJ",
          "ISFP",
          "ESFP",
          "ISTP",
          "ESTP",
          "ISTJ",
          "ESTJ",
          "ISFJ",
          "ESFJ",
        ],
        datasets: [
          {
            label: "INFJ",
            backgroundColor: [
              "#a0e691",
              "#b6e438",
              "#197361",
              "#6144a8",
              "#8e60b6",
              "#b093bf",
              "#7d86d9",
              "#f29f95",
              "#fcb979",
              "#f3737d",
              "#fa9751",
              "#1d6aa5",
              "#1b81a3",
              "#53ace9",
              "#7cc6cb",
            ],
            data: [3, 11, 6, 9, 12, 10, 8, 7, 14, 13, 15, 2, 1, 4, 5],
          },
        ],
        options: {
          plugins: {
            legend: {
              display: false,
            },
          },
        },
      },
    };

    const options = [
      {
        value: 0,
        label: "INFJ",
      },
      {
        value: 1,
        label: "INFP",
      },
      {
        value: 2,
        label: "ENFP",
      },
      {
        value: 3,
        label: "ENFJ",
      },
      {
        value: 4,
        label: "INTJ",
      },
      {
        value: 5,
        label: "INTP",
      },
      {
        value: 6,
        label: "ENTP",
      },
      {
        value: 7,
        label: "ENTJ",
      },
      {
        value: 8,
        label: "ISFP",
      },
      {
        value: 9,
        label: "ESFP",
      },
      {
        value: 10,
        label: "ISTP",
      },
      {
        value: 11,
        label: "ESTP",
      },
      {
        value: 12,
        label: "ISTJ",
      },
      {
        value: 13,
        label: "ESTJ",
      },
      {
        value: 14,
        label: "ISFJ",
      },
      {
        value: 15,
        label: "ESFJ",
      },
    ];

    const dataList = [
      {
        label: "INFJ",
        labels: [
          "INFP",
          "ENFP",
          "ENFJ",
          "INTJ",
          "INTP",
          "ENTP",
          "ENTJ",
          "ISFP",
          "ESFP",
          "ISTP",
          "ESTP",
          "ISTJ",
          "ESTJ",
          "ISFJ",
          "ESFJ",
        ],
        backgroundColor: [
          "#a0e691",
          "#b6e438",
          "#197361",
          "#6144a8",
          "#8e60b6",
          "#93d6ff",
          "#7e085e",
          "#f29f95",
          "#fcb979",
          "#f3737d",
          "#fa9751",
          "#1d6aa5",
          "#1b81a3",
          "#53ace9",
          "#ff9edb",
        ],
        data: [3, 11, 6, 9, 12, 10, 8, 7, 14, 13, 15, 2, 1, 4, 5],
      },
      {
        label: "INFP",
        labels: [
          "INFJ",
          "ENFP",
          "ENFJ",
          "INTJ",
          "INTP",
          "ENTP",
          "ENTJ",
          "ISFP",
          "ESFP",
          "ISTP",
          "ESTP",
          "ISTJ",
          "ESTJ",
          "ISFJ",
          "ESFJ",
        ],
        backgroundColor: [
          "#71c727",
          "#b6e438",
          "#197361",
          "#6144a8",
          "#8e60b6",
          "#93d6ff",
          "#7e085e",
          "#f29f95",
          "#fcb979",
          "#f3737d",
          "#fa9751",
          "#1d6aa5",
          "#1b81a3",
          "#53ace9",
          "#ff9edb",
        ],
        data: [3, 5, 11, 13, 7, 9, 14, 4, 6, 2, 1, 12, 15, 8, 10],
      },
      {
        label: "ENFP",
        labels: [
          "INFJ",
          "INFP",
          "ENFJ",
          "INTJ",
          "INTP",
          "ENTP",
          "ENTJ",
          "ISFP",
          "ESFP",
          "ISTP",
          "ESTP",
          "ISTJ",
          "ESTJ",
          "ISFJ",
          "ESFJ",
        ],
        backgroundColor: [
          "#71c727",
          "#a0e691",
          "#197361",
          "#6144a8",
          "#8e60b6",
          "#93d6ff",
          "#7e085e",
          "#f29f95",
          "#fcb979",
          "#f3737d",
          "#fa9751",
          "#1d6aa5",
          "#1b81a3",
          "#53ace9",
          "#ff9edb",
        ],
        data: [11, 6, 5, 10, 7, 4, 9, 8, 3, 1, 2, 15, 12, 14, 13],
      },
      {
        label: "ENFJ",
        labels: [
          "INFJ",
          "INFP",
          "ENFP",
          "INTJ",
          "INTP",
          "ENTP",
          "ENTJ",
          "ISFP",
          "ESFP",
          "ISTP",
          "ESTP",
          "ISTJ",
          "ESTJ",
          "ISFJ",
          "ESFJ",
        ],
        backgroundColor: [
          "#71c727",
          "#a0e691",
          "#b6e438",
          "#6144a8",
          "#8e60b6",
          "#93d6ff",
          "#7e085e",
          "#f29f95",
          "#fcb979",
          "#f3737d",
          "#fa9751",
          "#1d6aa5",
          "#1b81a3",
          "#53ace9",
          "#ff9edb",
        ],
        data: [6, 11, 5, 8, 14, 9, 4, 10, 12, 15, 13, 1, 2, 3, 7],
      },
      {
        label: "INTJ",
        labels: [
          "INFJ",
          "INFP",
          "ENFP",
          "ENFJ",
          "INTP",
          "ENTP",
          "ENTJ",
          "ISFP",
          "ESFP",
          "ISTP",
          "ESTP",
          "ISTJ",
          "ESTJ",
          "ISFJ",
          "ESFJ",
        ],
        backgroundColor: [
          "#71c727",
          "#a0e691",
          "#b6e438",
          "#197361",
          "#8e60b6",
          "#93d6ff",
          "#7e085e",
          "#f29f95",
          "#fcb979",
          "#f3737d",
          "#fa9751",
          "#1d6aa5",
          "#1b81a3",
          "#53ace9",
          "#ff9edb",
        ],
        data: [11, 12, 10, 7, 4, 9, 5, 13, 15, 8, 14, 6, 3, 2, 1],
      },
      {
        label: "INTP",
        labels: [
          "INFJ",
          "INFP",
          "ENFP",
          "ENFJ",
          "INTJ",
          "ENTP",
          "ENTJ",
          "ISFP",
          "ESFP",
          "ISTP",
          "ESTP",
          "ISTJ",
          "ESTJ",
          "ISFJ",
          "ESFJ",
        ],
        backgroundColor: [
          "#71c727",
          "#a0e691",
          "#b6e438",
          "#197361",
          "#6144a8",
          "#93d6ff",
          "#7e085e",
          "#f29f95",
          "#fcb979",
          "#f3737d",
          "#fa9751",
          "#1d6aa5",
          "#1b81a3",
          "#53ace9",
          "#ff9edb",
        ],
        data: [12, 4, 8, 14, 6, 7, 9, 2, 1, 5, 3, 10, 11, 13, 15],
      },
      {
        label: "ENTP",
        labels: [
          "INFJ",
          "INFP",
          "ENFP",
          "ENFJ",
          "INTJ",
          "INTP",
          "ENTJ",
          "ISFP",
          "ESFP",
          "ISTP",
          "ESTP",
          "ISTJ",
          "ESTJ",
          "ISFJ",
          "ESFJ",
        ],
        backgroundColor: [
          "#71c727",
          "#a0e691",
          "#b6e438",
          "#197361",
          "#6144a8",
          "#8e60b6",
          "#7e085e",
          "#f29f95",
          "#fcb979",
          "#f3737d",
          "#fa9751",
          "#1d6aa5",
          "#1b81a3",
          "#53ace9",
          "#ff9edb",
        ],
        data: [11, 9, 5, 8, 10, 7, 3, 1, 2, 6, 4, 14, 13, 15, 12],
      },
      {
        label: "ENTJ",
        labels: [
          "INFJ",
          "INFP",
          "ENFP",
          "ENFJ",
          "INTJ",
          "INTP",
          "ENTP",
          "ISFP",
          "ESFP",
          "ISTP",
          "ESTP",
          "ISTJ",
          "ESTJ",
          "ISFJ",
          "ESFJ",
        ],
        backgroundColor: [
          "#71c727",
          "#a0e691",
          "#b6e438",
          "#197361",
          "#6144a8",
          "#8e60b6",
          "#93d6ff",
          "#f29f95",
          "#fcb979",
          "#f3737d",
          "#fa9751",
          "#1d6aa5",
          "#1b81a3",
          "#53ace9",
          "#ff9edb",
        ],
        data: [8, 14, 9, 6, 7, 10, 4, 15, 13, 11, 12, 5, 3, 1, 2],
      },
      {
        label: "ISFP",
        labels: [
          "INFJ",
          "INFP",
          "ENFP",
          "ENFJ",
          "INTJ",
          "INTP",
          "ENTP",
          "ENTJ",
          "ESFP",
          "ISTP",
          "ESTP",
          "ISTJ",
          "ESTJ",
          "ISFJ",
          "ESFJ",
        ],
        backgroundColor: [
          "#71c727",
          "#a0e691",
          "#b6e438",
          "#197361",
          "#6144a8",
          "#8e60b6",
          "#93d6ff",
          "#7e085e",
          "#fcb979",
          "#f3737d",
          "#fa9751",
          "#1d6aa5",
          "#1b81a3",
          "#53ace9",
          "#ff9edb",
        ],
        data: [9, 5, 6, 11, 13, 2, 1, 15, 8, 4, 7, 12, 3, 10],
      },
      {
        label: "ESFP",
        labels: [
          "INFJ",
          "INFP",
          "ENFP",
          "ENFJ",
          "INTJ",
          "INTP",
          "ENTP",
          "ENTJ",
          "ISFP",
          "ISTP",
          "ESTP",
          "ISTJ",
          "ESTJ",
          "ISFJ",
          "ESFJ",
        ],
        backgroundColor: [
          "#71c727",
          "#a0e691",
          "#b6e438",
          "#197361",
          "#6144a8",
          "#8e60b6",
          "#93d6ff",
          "#7e085e",
          "#f29f95",
          "#f3737d",
          "#fa9751",
          "#1d6aa5",
          "#1b81a3",
          "#53ace9",
          "#ff9edb",
        ],
        data: [14, 6, 3, 12, 15, 1, 2, 13, 8, 7, 5, 10, 11, 9, 4],
      },
      {
        label: "ISTP",
        labels: [
          "INFJ",
          "INFP",
          "ENFP",
          "ENFJ",
          "INTJ",
          "INTP",
          "ENTP",
          "ENTJ",
          "ISFP",
          "ESFP",
          "ESTP",
          "ISTJ",
          "ESTJ",
          "ISFJ",
          "ESFJ",
        ],
        backgroundColor: [
          "#71c727",
          "#a0e691",
          "#b6e438",
          "#197361",
          "#6144a8",
          "#8e60b6",
          "#93d6ff",
          "#7e085e",
          "#f29f95",
          "#fcb979",
          "#fa9751",
          "#1d6aa5",
          "#1b81a3",
          "#53ace9",
          "#ff9edb",
        ],
        data: [13, 2, 1, 15, 7, 6, 5, 11, 3, 9, 8, 4, 10, 12, 14],
      },
      {
        label: "ESTP",
        labels: [
          "INFJ",
          "INFP",
          "ENFP",
          "ENFJ",
          "INTJ",
          "INTP",
          "ENTP",
          "ENTJ",
          "ISFP",
          "ESFP",
          "ISTP",
          "ISTJ",
          "ESTJ",
          "ISFJ",
          "ESFJ",
        ],
        backgroundColor: [
          "#71c727",
          "#a0e691",
          "#b6e438",
          "#197361",
          "#6144a8",
          "#8e60b6",
          "#93d6ff",
          "#7e085e",
          "#f29f95",
          "#fcb979",
          "#f3737d",
          "#1d6aa5",
          "#1b81a3",
          "#53ace9",
          "#ff9edb",
        ],
        data: [15, 1, 2, 13, 14, 5, 4, 12, 7, 6, 10, 9, 3, 11, 8],
      },
      {
        label: "ISTJ",
        labels: [
          "INFJ",
          "INFP",
          "ENFP",
          "ENFJ",
          "INTJ",
          "INTP",
          "ENTP",
          "ENTJ",
          "ISFP",
          "ESFP",
          "ISTP",
          "ESTP",
          "ESTJ",
          "ISFJ",
          "ESFJ",
        ],
        backgroundColor: [
          "#71c727",
          "#a0e691",
          "#b6e438",
          "#197361",
          "#6144a8",
          "#8e60b6",
          "#93d6ff",
          "#7e085e",
          "#f29f95",
          "#fcb979",
          "#f3737d",
          "#fa9751",
          "#1b81a3",
          "#53ace9",
          "#ff9edb",
        ],
        data: [2, 12, 15, 1, 6, 9, 14, 3, 13, 10, 4, 11, 8, 5, 7],
      },
      {
        label: "ESTJ",
        labels: [
          "INFJ",
          "INFP",
          "ENFP",
          "ENFJ",
          "INTJ",
          "INTP",
          "ENTP",
          "ENTJ",
          "ISFP",
          "ESFP",
          "ISTP",
          "ESTP",
          "ISTJ",
          "ISFJ",
          "ESFJ",
        ],
        backgroundColor: [
          "#71c727",
          "#a0e691",
          "#b6e438",
          "#197361",
          "#6144a8",
          "#8e60b6",
          "#93d6ff",
          "#7e085e",
          "#f29f95",
          "#fcb979",
          "#f3737d",
          "#fa9751",
          "#1d6aa5",
          "#53ace9",
          "#ff9edb",
        ],
        data: [1, 15, 9, 2, 5, 13, 12, 4, 14, 10, 11, 3, 8, 7, 6],
      },
      {
        label: "ISFJ",
        labels: [
          "INFJ",
          "INFP",
          "ENFP",
          "ENFJ",
          "INTJ",
          "INTP",
          "ENTP",
          "ENTJ",
          "ISFP",
          "ESFP",
          "ISTP",
          "ESTP",
          "ISTJ",
          "ESTJ",
          "ESFJ",
        ],
        backgroundColor: [
          "#71c727",
          "#a0e691",
          "#b6e438",
          "#197361",
          "#6144a8",
          "#8e60b6",
          "#93d6ff",
          "#7e085e",
          "#f29f95",
          "#fcb979",
          "#f3737d",
          "#fa9751",
          "#1d6aa5",
          "#1b81a3",
          "#ff9edb",
        ],
        data: [4, 8, 14, 5, 2, 13, 15, 1, 3, 11, 12, 10, 6, 9, 7],
      },
      {
        label: "ESFJ",
        labels: [
          "INFJ",
          "INFP",
          "ENFP",
          "ENFJ",
          "INTJ",
          "INTP",
          "ENTP",
          "ENTJ",
          "ISFP",
          "ESFP",
          "ISTP",
          "ESTP",
          "ISTJ",
          "ESTJ",
          "ISFJ",
        ],
        backgroundColor: [
          "#71c727",
          "#a0e691",
          "#b6e438",
          "#197361",
          "#6144a8",
          "#8e60b6",
          "#93d6ff",
          "#7e085e",
          "#f29f95",
          "#fcb979",
          "#f3737d",
          "#fa9751",
          "#1d6aa5",
          "#1b81a3",
          "#53ace9",
        ],
        data: [5, 11, 12, 7, 1, 15, 13, 2, 8, 3, 14, 9, 10, 4, 6],
      },
    ];
    const preSetting = function () {
      for (var i = 0; i <= 15; i++) {
        if (dataList[i].label === state.userMbti) {
          mbti.value = i;
          break;
        }
      }
      changeChart();
    };
    const changeChart = function () {
      lineChart.data.datasets[0].label = dataList[mbti.value].label;
      lineChart.data.datasets[0].backgroundColor =
        dataList[mbti.value].backgroundColor;
      lineChart.data.datasets[0].data = dataList[mbti.value].data;
      lineChart.data.labels = dataList[mbti.value].labels;
      chartRef.value.update();

      let firstIndex = dataList[mbti.value].data.indexOf(15);
      let secondIndex = dataList[mbti.value].data.indexOf(14);
      let thirdIndex = dataList[mbti.value].data.indexOf(13);
      first.value = dataList[mbti.value].labels[firstIndex];
      second.value = dataList[mbti.value].labels[secondIndex];
      third.value = dataList[mbti.value].labels[thirdIndex];

      console.log(first.value + " " + second.value + " " + third.value);
    };

    const goHome = function () {
      router.push({ name: "HomeView" });
    };
    return {
      chartRef,
      dataList,
      mbti,
      first,
      second,
      third,
      options,
      lineChart,
      changeChart,
      goHome,
    };
  },
};
</script>

<style scoped>
.title {
  font-size: x-large;
  font-weight: bold;
  background-color: white;
  text-align: center;
  color: rgb(255, 91, 136);
  border-radius: 30px;
  border: solid rgb(255, 91, 136);
}

.rank {
  background-color: white;
  font-size: large;
  font-weight: bold;
  float: right;
  margin-right: 200px;
  padding: 5px;
  color: rgb(255, 91, 136);
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.12);
}
.rank-mbti {
  color: black;
  font-weight: bold;
}
.chart {
  width: 1000px;
  display: flex;
  flex-direction: column;
  margin: 0 auto;
}
.selected,
.outline {
  --el-color-primary: #f56c6c;
  --el-color-primary-light-3: #f89898;
  --el-color-primary-light-5: #fab6b6;
  --el-color-primary-light-7: #fcd3d3;
  --el-color-primary-light-8: #fde2e2;
  --el-color-primary-light-9: #fef0f0;
  --el-color-primary-dark-2: #c45656;
  --el-select-input-focus-border-color: #f56c6c;
  box-shadow: #f56c6c;
}
</style>
