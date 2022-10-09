<template>
    <div id="piliang" style="">
        <el-button type="primary" @click="dialogVisible = true">工时填报分析</el-button>

        <el-dialog title="工时填报分析" :visible.sync="dialogVisible"  width="60%"  >
      <div style="width:100%;height:50px;text-align: center;">
     <!-- 
         <div id="u44" class="ax_default label" style="color:#7f7f7f; width: 10%; height: 29px; float:left;margin-left: 1%">
           <div id="u44_text" class="text ">
            <p><span >选择项目</span></p>
          </div>
        </div>        
        <div id="u43" class="ax_default droplist" style="float: left ;margin-top: 5px;width: 20%;height: 30px;">
          <el-select v-model="gongshiForm.proj" placeholder="请选择项目">
            <el-option  value="OTC研发项目">OTC研发项目</el-option>
            <el-option  value="原型开发项目">原型开发项目</el-option>
            <el-option  value="操作系统开发">操作系统开发</el-option>
         </el-select>
        </div> -->
        <div id="u46" class="ax_default label" style="margin-left:16%;float:left;width: 10%;height: 29px; ">
          <div id="u46_text" class="text " style="color:#7f7f7f">
            <p><span>日期范围</span></p>
          </div>
        </div>
        <div id="u45" class="ax_default droplist" style="width: 20%;height: 30px;float: left;">
           <div class="block" >
            <el-date-picker
              v-model="gongshiForm.data1"
              type="date"
              placeholder="开始日期" style=" width: 100%;height: 30px; margin-top:4px ;">
            </el-date-picker>
          </div>
        </div> 
        <div id="u47" class="ax_default label" style="width:2%; height: 30px;float: left;margin-top: 15px;">
           <el-col class="line" :span="6">——</el-col>
        </div> 
         <div id="u45" class="ax_default droplist" style="width: 20%;height: 30px;float: left;">
          <div class="block" >
           <el-date-picker
          v-model="gongshiForm.data2"
            type="date"
          placeholder="结束日期" style=" width: 100%;height: 30px; margin-top:4px ;">
         </el-date-picker>
         </div>
        </div> 
        <div style=" width: 10%;float: right;margin-right: 40px;margin-top: 5px;" >
          <el-button type="success"  @click="postMes()">获取分析数据</el-button>
         </div>
     </div>  
      <div class="box" style="height: 500px; border-width: 1px; border:none;margin-top: 20px;">
        <div class="block" style="width:100%;height:500px;">
                <el-carousel trigger="click" style="height:500px" :autoplay="false"  >
                  <el-carousel-item   style="height:500PX;width:100%; ">
                  <!-- <fx :datasum=datasum :dataLine="dataLine" :datax="datax"></fx> -->
                    <fx  :datasum=datasum  :datax="datax" :resdata="resdata"></fx>
                  </el-carousel-item>

                   <el-carousel-item   style="height:500px;width:100%;">
                   <worktime :resdata="resdatatime"></worktime>
                  </el-carousel-item>
                </el-carousel>
          </div>
          
     </div>
  <!-- <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="dialogVisible = false"  >确 定</el-button>
  </span> -->
       </el-dialog>
    </div>
</template>

<script>
import worktime from "./worktimeEchart.vue";
import fx from "./fenxi.vue";
import axios from "axios";
export default {
    components:{
        worktime,
        fx,
    },
     
    data(){
      return {
        gongshiForm:{
        proj:"",
        data1:"",
        data2:"",
        },
        dialogVisible: false,
        pickerOptions: {
          disabledDate(time) {
            return time.getTime() > Date.now();
          },
          shortcuts: [{
            text: '今天',
            onClick(picker) {
              picker.$emit('pick', new Date());
            }
          }, {
            text: '昨天',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit('pick', date);
            }
          }, {
            text: '一周前',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', date);
            }
          }]
        },
        value1: '',
        value2: '',

         chartInstance: null,
         resdata:[[]],
         resdatatime:[[]],
          datax:['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
          datasum:[[20, 18, 19,11,11,11,11],
          [2, 8, 9, 1, 4, 11, 6],
          [0, 1, 1, 2, 3, 6,8]],
          // dataLine:    ['张三', '李四', '王五']
      };
    },
    methods:{
      postMes(){
        console.log(this.gongshiForm)
        if(this.gongshiForm.data1==''||this.gongshiForm.data2==''){
            this.$message({
              type:'error',
              message:'请选择起始日期'
            })
            return 
        }
        axios({
          url:'/chart/getworktimeinfor',
          method:'POST',
          params:{
            getworktimeinfor:JSON.stringify({
              proid:this.$store.state.pro.proid,
              starttime:this.gongshiForm.data1,
              endtime:this.gongshiForm.data2
            }),
          }
        }).then(res=>{
          // console.log(res);
          this.resdata=res.data.worktimelist;
          localStorage.setItem("datas",this.resdata)
        }),
         axios({
          url:'/chart/getabsenceinfor',
          method:'POST',
          params:{
            getabsence:JSON.stringify({
              proid:this.$store.state.pro.proid,
              starttime:this.gongshiForm.data1,
              endtime:this.gongshiForm.data2
            }),
          }
        }).then(res1=>{
          console.log(res1.data)
          this.resdatatime=res1.data.rasult;
        })
      }
    }
}
</script>



<style scoped lang="scss">
#workhour {
    height: 600px;
    width: 700px;
    border: black solid 1px;
}
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

