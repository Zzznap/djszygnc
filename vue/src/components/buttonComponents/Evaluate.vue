<!-- 任务点评弹窗 -->

<template>
  <div id="ev">
    <div>
      <el-button type="text" @click="dialogVisible = true" style="font-size:20px ;padding:0px"><i class="el-icon-s-order"></i></el-button>

      <el-dialog title="任务评价" :visible.sync="dialogVisible" width="75%" :append-to-body="true" >

        <div id="evaluate">
          <!-- 主窗口设计 -->
          <el-table :data="EvaluateData" height=420 v-loading.fullscreen.lock="loading">
            <el-table-column prop="assigner" label="完成者" width="80"></el-table-column>
            <el-table-column prop="esti_time,actu_time" label="起止时间" width="300" header-align="center" align="center">
              <template slot-scope="scope">
                <span style="color: green;">预计时间: {{ scope.row.starttime0 }} ~ {{ scope.row.endtime0 }}</span>
                <br>
                <span style="color: red;">实际时间:{{ scope.row.costtime1 }} ~ {{ scope.row.endtime1 }}</span>
              </template>
            </el-table-column>
            <!-- 预计estimate + 工时time = estimate_time -->
            <!-- 实际actual + 工时time = actual_time -->
            <!-- 修改modify + 工时time = modify_time -->
            <el-table-column prop="costtime0" label="预计工时" width="80" align="center"></el-table-column>
            <el-table-column prop="costtime1" label="实际工时" width="80" align="center"></el-table-column>
            <el-table-column prop="costtime1" label="调整工时" width="120" header-align="center">
              <template slot-scope="scope">
                <el-input v-model="scope.row.costtime1" placeholder="修改工时" id="Value" value="scope.row.costtime1"
                  onkeyup="value=value.replace(/[^\d]/g,'')"></el-input>
              </template>
            </el-table-column>
            <el-table-column prop="commend0" label="评价" width="150" header-align="center" :render-header="addRedStar">
              <template slot-scope="scope">
                <el-rate v-model="scope.row.commend0" :colors="colors"></el-rate>
              </template>
            </el-table-column>
            <el-table-column prop="commend1" label="备注" width="300" header-align="center">
              <template slot-scope="scope">
                <el-input type="textarea" placeholder="请输入备注" v-model="scope.row.commend1" maxlength="100"
                  show-word-limit>
                </el-input>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button style="padding: 10px;font-size: 10px;" @click="dialogVisible = false">取 消</el-button>
          <el-button style="padding: 10px;font-size: 10px;" type="primary" @click="SubmitClose()" round>确 定</el-button>
        </span>
      </el-dialog>

    </div>

  </div>
</template>







<script>
import axios from 'axios';

export default {
  data() {
    return {
      nowrow:'',
      EvaluateData:[{}],
      colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
      dialogVisible: false,
      loading: false
    }
  },
    //接收父组件传来的信息

  props:['row'] ,
  watch:{
    row(newval,oldval){
      this.nowrow=JSON.parse(JSON.stringify(newval))
      console.log(this.nowrow.messionid)
      this.handleopen()
    }
  },

  methods: {
    handleopen(){
      console.log(this.nowrow)
      axios({
        url:'/check/getworktimeinformation',
        method:'POST',
        params:{
          getworktimeinfor:JSON.stringify({messionid:this.nowrow.messionid})
        }
      }).then(res=>{
        this.$set(this.EvaluateData[0],res.data)
        console.log(this.EvaluateData[0])
      })
    },
    addRedStar(h, { column }) { // 给表头加必选标识
      return [h('span', { style: 'color: red' }, '*'), h('span', ' ' + column.label)]
    },
    SubmitClose() {

      //判空
      var isempty = false;
      for (var i = 0; i < this.EvaluateData.length; i++) {
        if (this.EvaluateData[i].commend0 == 0) {
          isempty = true;
          break;
        }
      }

      this.$confirm('您要提交评价，请确认信息准确无误', '提交确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //空值检测
        if (isempty) {
          this.$message({
            type: 'error',
            message: '提交失败，请检查必填项是否填写'
          })
          return;
        }
        //向后端post
        var submit={}
        // this.$set(submit,'messionid',this.row.messionid)
        // this.$set(submit,'commend0',this.EvaluateData.commend0)
        // this.$set(submit,'commend1',this.EvaluateData.commend1)
        // this.$set(submit,'costtime',this.EvaluateData.costtime1)
        submit.messionid=this.nowrow.messionid
        submit.commend0=this.EvaluateData[0].commend0
        submit.commend1=this.EvaluateData[0].commend1
        submit.costtime=this.EvaluateData[0].costtime1
        axios({
          url:'/mession/commend',
          method:'POST',
          params:{
            commendmsg:JSON.stringify(submit)
          }
        }).then(res=>{
          console.log(res)
        })


        this.loading = true;
        setTimeout(() => {
          this.loading = false;
          this.dialogVisible = false;
          this.$message({
            type: 'success',
            message: '已提交!'
          });

        }, 1000);
        //后面把延时改成if判断


      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消提交'
        })
      });



    }
  }
}

</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
#evaluate {
  border-radius: 8px;
  // border: black solid 1px;
}
</style>
