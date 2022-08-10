<template>
  <el-container style="background-color: #fff4b8">
    <el-header>
      <img class="logo" src="@/assets/logo.png" />
    </el-header>
    <div>
      <div style="margin-left: 50px">
        <el-select v-model="mbti" @change="changeChart">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </div>
      <div class="chart">
        <vue3-chart-js
          :id="lineChart.id"
          :type="lineChart.type"
          :data="lineChart.data"
        ></vue3-chart-js>
      </div>
    </div>
  </el-container>
</template>

<script>
import Vue3ChartJs from "@j-t-mcc/vue3-chartjs";
import { useRouter } from "vue-router";
import { ref } from "vue";
export default {
  components: {
    Vue3ChartJs,
  },
  setup() {
    const router = useRouter();
    const mbti = ref(0);
    const lineChart = {
      id: "line",
      type: "line",
      data: {
        labels: ["VueJs", "EmberJs", "ReactJs", "AngularJs"],
        datasets: [
          {
            label: "MBTI Chart",
            backgroundColor: ["#41B883", "#E46651", "#00D8FF", "#DD1B16"],
            data: [40, 20, 80, 10],
          },
        ],
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
          "#ba91df",
          "#ffda96",
          "#5ee1d1",
          "#321886",
          "#0e88ad",
          "#93d6ff",
          "#7e085e",
          "#c869a3",
          "#ff578b",
          "#448d84",
          "#e65844",
          "#2db47c",
          "#961830",
          "#dce76f",
          "#ff9edb",
        ],
        data: [],
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
          "#7663e1",
          "#ffda96",
          "#5ee1d1",
          "#321886",
          "#0e88ad",
          "#93d6ff",
          "#7e085e",
          "#c869a3",
          "#ff578b",
          "#448d84",
          "#e65844",
          "#2db47c",
          "#961830",
          "#dce76f",
          "#ff9edb",
        ],
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
          "#7663e1",
          "#ba91df",
          "#5ee1d1",
          "#321886",
          "#0e88ad",
          "#93d6ff",
          "#7e085e",
          "#c869a3",
          "#ff578b",
          "#448d84",
          "#e65844",
          "#2db47c",
          "#961830",
          "#dce76f",
          "#ff9edb",
        ],
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
          "#7663e1",
          "#ba91df",
          "#ffda96",
          "#321886",
          "#0e88ad",
          "#93d6ff",
          "#7e085e",
          "#c869a3",
          "#ff578b",
          "#448d84",
          "#e65844",
          "#2db47c",
          "#961830",
          "#dce76f",
          "#ff9edb",
        ],
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
          "#7663e1",
          "#ba91df",
          "#ffda96",
          "#5ee1d1",
          "#0e88ad",
          "#93d6ff",
          "#7e085e",
          "#c869a3",
          "#ff578b",
          "#448d84",
          "#e65844",
          "#2db47c",
          "#961830",
          "#dce76f",
          "#ff9edb",
        ],
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
          "#7663e1",
          "#ba91df",
          "#ffda96",
          "#5ee1d1",
          "#321886",
          "#93d6ff",
          "#7e085e",
          "#c869a3",
          "#ff578b",
          "#448d84",
          "#e65844",
          "#2db47c",
          "#961830",
          "#dce76f",
          "#ff9edb",
        ],
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
          "#7663e1",
          "#ba91df",
          "#ffda96",
          "#5ee1d1",
          "#321886",
          "#0e88ad",
          "#7e085e",
          "#c869a3",
          "#ff578b",
          "#448d84",
          "#e65844",
          "#2db47c",
          "#961830",
          "#dce76f",
          "#ff9edb",
        ],
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
          "#7663e1",
          "#ba91df",
          "#ffda96",
          "#5ee1d1",
          "#321886",
          "#0e88ad",
          "#93d6ff",
          "#c869a3",
          "#ff578b",
          "#448d84",
          "#e65844",
          "#2db47c",
          "#961830",
          "#dce76f",
          "#ff9edb",
        ],
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
          "#7663e1",
          "#ba91df",
          "#ffda96",
          "#5ee1d1",
          "#321886",
          "#0e88ad",
          "#93d6ff",
          "#7e085e",
          "#ff578b",
          "#448d84",
          "#e65844",
          "#2db47c",
          "#961830",
          "#dce76f",
          "#ff9edb",
        ],
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
          "#7663e1",
          "#ba91df",
          "#ffda96",
          "#5ee1d1",
          "#321886",
          "#0e88ad",
          "#93d6ff",
          "#7e085e",
          "#c869a3",
          "#448d84",
          "#e65844",
          "#2db47c",
          "#961830",
          "#dce76f",
          "#ff9edb",
        ],
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
          "#7663e1",
          "#ba91df",
          "#ffda96",
          "#5ee1d1",
          "#321886",
          "#0e88ad",
          "#93d6ff",
          "#7e085e",
          "#c869a3",
          "#ff578b",
          "#e65844",
          "#2db47c",
          "#961830",
          "#dce76f",
          "#ff9edb",
        ],
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
          "#7663e1",
          "#ba91df",
          "#ffda96",
          "#5ee1d1",
          "#321886",
          "#0e88ad",
          "#93d6ff",
          "#7e085e",
          "#c869a3",
          "#ff578b",
          "#448d84",
          "#2db47c",
          "#961830",
          "#dce76f",
          "#ff9edb",
        ],
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
          "#7663e1",
          "#ba91df",
          "#ffda96",
          "#5ee1d1",
          "#321886",
          "#0e88ad",
          "#93d6ff",
          "#7e085e",
          "#c869a3",
          "#ff578b",
          "#448d84",
          "#e65844",
          "#961830",
          "#dce76f",
          "#ff9edb",
        ],
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
          "#7663e1",
          "#ba91df",
          "#ffda96",
          "#5ee1d1",
          "#321886",
          "#0e88ad",
          "#93d6ff",
          "#7e085e",
          "#c869a3",
          "#ff578b",
          "#448d84",
          "#e65844",
          "#2db47c",
          "#dce76f",
          "#ff9edb",
        ],
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
          "#7663e1",
          "#ba91df",
          "#ffda96",
          "#5ee1d1",
          "#321886",
          "#0e88ad",
          "#93d6ff",
          "#7e085e",
          "#c869a3",
          "#ff578b",
          "#448d84",
          "#e65844",
          "#2db47c",
          "#961830",
          "#ff9edb",
        ],
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
          "#7663e1",
          "#ba91df",
          "#ffda96",
          "#5ee1d1",
          "#321886",
          "#0e88ad",
          "#93d6ff",
          "#7e085e",
          "#c869a3",
          "#ff578b",
          "#448d84",
          "#e65844",
          "#2db47c",
          "#961830",
          "#dce76f",
        ],
      },
    ];

    const changeChart = function () {
      console.log(mbti.value);
    };

    const goHome = function () {
      router.push({ name: "HomeView" });
    };
    return { dataList, mbti, options, lineChart, changeChart, goHome };
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
.chart {
  width: 1200px;
  display: flex;
  flex-direction: column;
  margin: 0 auto;
  margin-top: 50px;
}
.result {
  display: inline-block;
  text-align: center;
  font-weight: bold;
  font-size: x-large;
  height: 275px;
  line-height: 275px;
  width: 600px;
  border: 20px solid deeppink;
  background-color: white;
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.12);
}
</style>
