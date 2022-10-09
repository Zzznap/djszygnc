<template>
  <div>
    <!-- :data="tableData"  -->
    <i class="el-icon-refresh-left " style="font-size: 20px;cursor: pointer " @click="handlereflash"></i>
    <el-table v-loading="loading" :key="key" border style="width: 100%"
      :data="tableData.slice((currentPage[0] - 1) * pagesize, currentPage[0] * pagesize)" @row-click="handelRowClick"
      >
      <el-table-column prop="messionname" label="任务名称" width="150">
      </el-table-column>
      <el-table-column prop="messionid" label="活动编号" width="100">
      </el-table-column>
      <el-table-column prop="starttime" label="开始时间" width="120">
      </el-table-column>
      <el-table-column prop="endtime" label="结束时间" width="120">
      </el-table-column>
      <el-table-column prop="teg" label="状态" width="100">
        <template slot-scope="scope">
          <span v-if="scope.row.teg==0" class="tegspan" style="border-color:#E6A23C;color:#E6A23C">待开始</span>
          <span v-if="scope.row.teg==1" class="tegspan" style="border-color:#67C23A;color:#67C23A">进行中</span>
          <span v-if="scope.row.teg==2" class="tegspan" style="border-color:#91d5fc;color:#91d5fc">已关闭</span>
          <span v-if="scope.row.teg==3" class="tegspan" style="border-color:#e35179;color:#e35179">已完成</span>
          <span v-if="scope.row.teg==4" class="tegspan" style="border-color:#409EFF;color:#409EFF">已取消</span>
        </template>
      </el-table-column>
      <el-table-column prop="creater" label="创建人" width="120" :formatter="showcreater">
        <!-- <template slot-scope="scope">
            {{scope.row.creater.creatername}}
        </template> -->
      </el-table-column>
      <el-table-column prop="assigner" label="指派给" width="100" :formatter="showassigner">
        <!-- <template slot-scope="scope">
          {{scope.row.assigner.assignername}}
        </template> -->
      </el-table-column>
      <el-table-column prop="costtime" label="计划用时" width="100">
      </el-table-column>
      <el-table-column label="操作" width="160px" fixed="right">
        <template slot-scope="scope">
          <!-- <h2 @click="h2click">row:{{row}}</h2> -->
          <!-- <Control :scope="scope" :row="nowRow"></Control> -->
          <el-button-group class="buttonicon">
            <CancelTask class="left buttonicon" :row="nowRow" v-if="scope.row.teg != 4"></CancelTask>
            <accomplish class="left buttonicon" :row="nowRow" v-if="scope.row.teg == '1'"></accomplish>
            <Hours class="left buttonicon" :row="nowRow"  v-if="scope.row.assigner.assignerid==userid &&scope.row.teg == '1'"  ></Hours>
            <Start class="left buttonicon" :row="nowRow" v-if="scope.row.teg == '0'"></Start>
            <Modification class="left buttonicon" :row="nowRow" v-if="scope.row.creater.createrid == userid"></Modification>
            <WindowEvaluate class="left buttonicon" v-if="scope.row.teg == '3'&&scope.row.creater.createrid==userid" :row="nowRow"></WindowEvaluate>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>
    <!-- :page-count="totalPage" -->
    <div style="text-align:center">
      <el-pagination v-if="pageshow" :total="parseInt(totalrow)" :page-size="pagesize" :pager-count="11"
        layout="prev, pager, next" @size-change="handleSizeChange" @current-change="handleCurrentChange"
        @next-click="handleNextClick" :hide-on-single-page="true">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import Control from './buttonComponents/buttonControl.vue'
import CancelTask from './buttonComponents/WindowCancelTask.vue'
import Accomplish from './buttonComponents/WindowWorkingAccomplish.vue'
import Hours from './buttonComponents/WindowWorkingHours.vue'
import Start from './buttonComponents/WindowWorkingStart.vue'
import Modification from './buttonComponents/WindowTaskModification.vue'
import WindowEvaluate from './buttonComponents/WindowEvaluate.vue'

export default {
  components: {
    Control,
    WindowEvaluate,
    CancelTask,
    Accomplish,
    Hours,
    Start,
    Modification
  },
  props: ['statusid'],
  computed:{
      proid(){
          return  this.$store.state.pro.proid
      }

  },
  data() {
    return {
      userid:this.$store.state.userid,
      nowRow: {
        messionname: '',
        need: '',
        milepost: '',
        moudle: '',
        assiner: '',
        importance: '',
        starttime: '',
        endtime: '',
        cost: '',
        describe: '',
      },
      pageshow: true,
      loadedPage: 1,
      currentPage: [1],
      currentIndex: '',
      pagesize: 10,
      tableData: [
        {
          creater:{
            creatername:'',
            createrid:''
          },
          assigner:{
            assignername:'',
            assignerid:''
          }
        }
      ],
      totalPage: 1,
      totalrow: 0,
      loading: true,
      key: 1
    }
  },
  created() {
    this.$watch(
      () => this.$route.params,
      (toParams, previousParams) => {
        this.proid = this.$route.params.projectid
        this.tableData = [],
          this.loading = true,
          this.$set(this.currentPage, 0, 1)
        this.getTotalPage()
        this.pageshow = false
        this.$nextTick(() => {//重新渲染分页
          this.pageshow = true;
        })
      }
    )
  },
  mounted() {
    this.getTotalPage()
  },
 
  methods: {
    showassigner(row) {
      try {
        return row.assigner.assignername
      } catch (err) {

      }
    },
    showcreater(row) {
      try {

        return row.creater.creatername
      } catch (err) {

      }
    },
    async getTotalPage() {
      this.totalrow = 0
      try {
        if (this.statusid == 2) {
          await axios({
            url: "/check/getcreatermessionpage",
            method: 'POST',
            params: {
              getpage: JSON.stringify({
                proid: this.proid,
                userid: this.userid,
                teg: '%',
                need: '%',
                moudle: '%',
              })
            }
          })
            .then(res => {

              // this.totalPage = parseInt((res.data.number - 1) / 10) + 1
              this.totalrow = res.data.number
            })
        }
        else if (this.statusid == 1) {
          await axios({
            url: "/check/getassignermessionpage",
            method: 'POST',
            params: {
              getpage: JSON.stringify({
                proid: this.proid,
                userid: this.userid,
                teg: '%',
                need: '%',
                moudle: '%',
              })
            }
          })
            .then(res => {
              // this.totalPage = parseInt((res.data.number - 1) / 10) + 1

              this.totalrow = res.data.number
            })
        }
        else if (this.statusid == 3) {
          await axios({
            url: "/check/getcreatermessionpage",
            method: 'POST',
            params: {
              getpage: JSON.stringify({
                proid: this.proid,
                userid: this.userid,
                teg: '2',
                need: '%',
                moudle: '%',
              })
            }
          })
            .then(res => {
              // this.totalPage = parseInt((res.data.number - 1) / 10) + 1
              this.totalrow = res.data.number

            })
        } else if (this.statusid == 4) {
          await axios({
            url: "/check/getassignermessionpage",
            method: 'POST',
            params: {
              getpage: JSON.stringify({
                proid: this.proid,
                userid: this.userid,
                teg: '3',
                need: '%',
                moudle: '%',
              })
            }
          })
            .then(res => {
              // this.totalPage = parseInt((res.data.number - 1) / 10) + 1
              this.totalrow = res.data.number
            })
        } else if (this.statusid == 5) {
          await axios({
            url: "/check/getcreatermessionpage",
            method: 'POST',
            params: {
              getpage: JSON.stringify({
                proid: this.proid,
                userid: this.userid,
                teg: '4',
                need: '%',
                moudle: '%',
              })
            }
          })
            .then(res => {
              // this.totalPage = parseInt((res.data.number - 1) / 10) + 1
              this.totalrow = res.data.number

            })
        }
        else if (this.statusid == 6) {
          await axios({
            url: "/check/getcreatermessionpage",
            method: 'POST',
            params: {
              getpage: JSON.stringify({
                proid: this.proid,
                userid: this.userid,
                teg: '%',
                need: '%',
                moudle: '%',
              })
            }
          })
            .then(res => {
              // this.totalPage = parseInt((res.data.number - 1) / 10) + 1
              this.totalrow += parseInt(res.data.number)
            })
          await axios({
            url: "/check/getassignermessionpage",
            method: 'POST',
            params: {
              getpage: JSON.stringify({
                proid: this.proid,
                userid: this.userid,
                teg: '%',
                need: '%',
                moudle: '%',
              })
            }
          })
            .then(res => {
              // this.totalPage = parseInt((res.data.number - 1) / 10) + 1
              this.totalrow += parseInt(res.data.number)
            })
          // this.totalPage = parseInt((total1 + total2 - 1) / 10) + 1
        }
      } catch (err) {
        console.log(err)
      }
      for (var i = 0; i < this.totalrow; i++) {
        this.tableData.push({})
      }
      this.getList()
    },
    handleClick(row) {
      // console.log(row);
    },
    /**
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     */
    async getList(pagenum = 1) {
      if (this.totalrow == 0) {
        this.loading = false
        return
      }
      try {
        if (this.statusid == 2) {
          await axios({
            url: "/check/getcreatermession",
            method: 'POST',
            params: {
              getmession: JSON.stringify({
                proid: this.proid,
                userid: this.userid,
                teg: '%',
                need: '%',
                moudle: '%',
                loc: pagenum.toString()
              })
            }
          })
            .then(res => {
              for (var i = 0; i < res.data.result.length; i++) {
                this.tableData[pagenum * 10 - 10 + i] = res.data.result[i]
              }
              this.key++
              this.loading = false
            })
        }
        else if (this.statusid == 1) {
          await axios({
            url: "/check/getassignermession",
            method: 'POST',
            params: {
              getmession: JSON.stringify({
                proid: this.proid,
                userid: this.userid,
                teg: '%',
                need: '%',
                moudle: '%',
                loc: pagenum.toString()


              })
            }
          })
            .then(res => {

              for (var i = 0; i < res.data.result.length; i++) {
                this.tableData[pagenum * 10 - 10 + i] = res.data.result[i]
              }
              this.key++
              this.loading = false
            })
        }
        else if (this.statusid == 3) {
          await axios({
            url: "/check/getcreatermession",
            method: 'POST',
            params: {
              getmession: JSON.stringify({
                proid: this.proid,
                userid: this.userid,
                teg: '2',
                need: '%',
                moudle: '%',
                loc: pagenum.toString()


              })
            }
          })
            .then(res => {
              for (var i = 0; i < res.data.result.length; i++) {
                this.tableData[pagenum * 10 - 10 + i] = res.data.result[i]
              }
              this.key++
              this.loading = false
            })
        } else if (this.statusid == 4) {
          await axios({
            url: "/check/getassignermession",
            method: 'POST',
            params: {
              getmession: JSON.stringify({
                proid: this.proid,
                userid: this.userid,
                teg: '3',
                need: '%',
                moudle: '%',
                loc: pagenum.toString()


              })
            }
          })
            .then(res => {
              for (var i = 0; i < res.data.result.length; i++) {
                this.tableData[pagenum * 10 - 10 + i] = res.data.result[i]
              }
              this.key++
              this.loading = false
            })
        }
        // 我取消的
        else if (this.statusid == 5) {
          await axios({
            url: "/check/getcreatermession",
            method: 'POST',
            params: {
              getmession: JSON.stringify({
                proid: this.proid,
                userid: this.userid,
                teg: '4',
                need: '%',
                moudle: '%',
                loc: pagenum.toString()

              })
            }
          })
            .then(res => {
              for (var i = 0; i < res.data.result.length; i++) {
                this.tableData[pagenum * 10 - 10 + i] = res.data.result[i]
              }
              this.key++
              this.loading = false
            })
        }
        // 全部
        else if (this.statusid == 6) {
          console.log(this.proid)
          await axios({
            url: "/check/getallmession",
            method: 'POST',
            params: {
              getmession: JSON.stringify({
                proid: this.proid,
                userid: this.userid,
                teg: '%',
                need: '%',
                moudle: '%',
                loc: pagenum.toString()
              })
            }
          })
            .then(res => {
              console.log(res)
              for (var i = 0; i < res.data.result.length; i++) {
                this.tableData[pagenum * 10 - 10 + i] = res.data.result[i]
              }
              this.key++
              this.loading = false
            })
        }
      } catch (error) {
        console.log('error:')
        console.log(error);

      }
    }, handleSizeChange(val) {
      this.pagesize = val;
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      this.$set(this.currentPage, 0, val)
      if (JSON.stringify(this.tableData[this.currentPage[0] * 10 - 10]) == "{}") {
        this.getList(val)
      }
    },
    handleNextClick(val) {
      this.currentPage[0] = val
    },
    handelRowClick(row) {
      this.nowRow = row
        console.log('nowrow',this.nowRow.messionid)
        console.log('row',row.messionid)
    },
    handlereflash(){
      this.loading=true
      console.log(this.currentPage[0])
      this.getList(this.currentPage[0])
    }
  }
}

</script>
<style>
.tegspan{
  padding-right:3px;padding-left:3px;padding-top:1px;padding-bottom:1px;
  border:1px;border-style:solid;border-radius:6px; 
}
</style>