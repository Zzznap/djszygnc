<template>
  <div class="home" style="width: 100%; height: 100%">
    <div ref="chart" id="chart" style="width: 100%; height: 100%"></div>
  </div>
</template>

<script>
import * as echarts from "echarts";
export default {
  name: "home",
  data: {},
  props: {
    resdata: {
      type: Array,
    },
  },
  watch: {
    resdata(val) {
      console.log(this.resdata), this.init();
    },
  },
  // mounted() {
  //   // 调用初始化实例的函数
  //   this.init();
  // },
  methods: {
    init() {
      let seriesdata = []
       for (var i = 0; i < this.resdata.length; i++) {
        var obj = new Object();
        obj.name = this.resdata[1][i];
        obj.value = this.resdata[2][i];
        seriesdata[i] = obj;
      }
      var myChart = echarts.init(document.getElementById("chart"));
      var option = {
        title: {
          text: "员工出勤率分析",
          left: "center",
        },
        tooltip: {
          trigger: "item",
        },
        legend: {
          orient: "vertical",
          left: "left",
        },
        series: [
          {
            name: "出勤率",
            type: "pie",
            radius: "55%",
            center: ["50%", "60%"],
            data: seriesdata,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          },
        ],
      };
      myChart.setOption(option);
    },
  },
};
</script>

<style>
.el-row {
  margin-bottom: 20px;
}
last-child {
  margin-bottom: 0;
}
.el-col {
  border-radius: 4px;
}
</style>