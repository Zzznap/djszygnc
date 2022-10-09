<template>
  <div class="home" style="width: 100%;height:100%;">
    <!-- 响应式布局+一般布局-->
    <!-- 现需要循环展示出script打开的多张图片地址，使用v-for  gutter代表图表之间的间隔-->
        <div ref="fxChart" id="fxChart" style="width: 100%;height:100%;"></div>
  </div>
</template>

<script>

// 引入echarts,之前也需要下载安装echarts
import * as echarts from 'echarts';
export default {
  name: 'home',
  props:{
      datax:[],
      datasum:[[]],
      resdata:{
        type:Array
      }
      // dataLine:[],
  },
  data:{
     return:{
        
     }
  },
   watch:{
       resdata(val){
      console.log(this.resdata), this.init();
    },
  },
  methods:{
    init(){
       let seriesdata = [];
       for (var i = 0; i < this.resdata[0].length; i++) {
        // var obj = new Object();
        var obj= {};
        var arr=[];
          for(var j=0;j<this.resdata.length-2;j++){
            arr[j]=this.resdata[j+2][i]
        }
        obj.name = this.resdata[0][i];
        obj.data = arr;
        obj.type='line';
        seriesdata[i] = obj;
      }
        var myChart = echarts.init(document.getElementById('fxChart'));
      var option = {
  title: {
    text: '任务工时分析'
  },
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'cross',
      label: {
        backgroundColor: '#6a7985'
      }
    }
  },
  legend: {
    // data: this.resdata[0]
  },

  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  xAxis: [
    {
      type: 'category',
      boundaryGap: false,
      data: this.resdata[1]
    }
  ],
  yAxis: [
    {
      type: 'value',
      axisTick:{
         length:1
      },
      max:100,
      min:0,
    }
  ],
   series: seriesdata,
 
};

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option,true);
    }
  },  
}
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