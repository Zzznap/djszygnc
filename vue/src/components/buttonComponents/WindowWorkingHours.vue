<!-- 工时填报弹窗 -->
<!-- Working Hours 缩写wh -->
<template>
    <div id="wh">
        <el-button  type="text" @click="dialogVisible = true" style="font-size:20px;padding:0px"><i class="el-icon-date"></i></el-button>
        
        <el-dialog title="工时填报" :visible.sync="dialogVisible" width="75%" :before-close="handleClose"
        :append-to-body="true">
        <div id="workhour">
                <el-table :data="WorkHour" :model="WorkHour" height="500" 
                    :rules="rules">
                    <el-table-column prop="date" label="日期" width="250" header-align="center"
                        :render-header="addRedStar">
                        <template slot-scope="scope">
                            <el-date-picker v-model="scope.row.date" value-format="yyyy-MM-dd" format="yyyy 年 MM 月 dd 日"
                               :disabled="scope.row.unav1" type="date" placeholder="选择日期"></el-date-picker>
                        </template>
                    </el-table-column>
                    <!-- 总计total + 工时time = total_time -->
                    <el-table-column prop="total_time" label="总计消耗" type="text" width="200" header-align="center"
                        :render-header="addRedStar">
                        <template slot-scope="scope">
                            <el-input v-model="scope.row.consume" placeholder="消耗工时" auto-complete="false" id="Value"
                               :disabled="scope.row.unav2" onkeyup="value=value.replace(/[^\d]/g,'')"
                                oninput="if(value>24)value=24">
                            </el-input>
                        </template>
                    </el-table-column>
                    <el-table-column prop="remark" label="备注" width="300" header-align="center">
                        <template slot-scope="scope">
                            <el-input type="textarea" placeholder="请输入内容" v-model="scope.row.remark" maxlength="300"
                               :disabled="scope.row.unav3" show-word-limit>
                            </el-input>
                        </template>
                    </el-table-column>
                    <el-table-column width="135" align="right" fixed="right">
                        <template slot="header" slot-scope="{}">
                            <span style="margin-right: 10px;">操作</span>
                            <el-button size="mini" type="primary" icon="el-icon-circle-plus-outline"
                             @click="WorkHourAdd">新增
                            </el-button>
                        </template>
                        <template slot-scope="scope">
                            <el-button size="mini" type="success" icon="el-icon-check" :disabled="scope.row.unav4"
                                @click="WorkHourSubLock(scope.$index, scope.row)" :loading="scope.row.load" circle>
                            </el-button>
                            <el-button size="mini" type="info"  
                                @click="WorkHourDelete(scope.$index, scope.row)" :disabled="scope.row.unav5" icon="el-icon-close" circle>
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
        </div>
        <span slot="footer" class="dialog-footer">
            <el-button @click="WorkHourClose">关 闭</el-button>
        </span>
    </el-dialog>
    </div>
</template>

<script>
import axios from 'axios';

export default {
  props:["row"],
     watch:{
    row(newVal, oldVal) {
      // newVal是新值，oldVal是旧值
      // Object.assign(this.newForm,this.row)
      this.newForm=JSON.parse(JSON.stringify(newVal))
    }
  },
    name: 'WorkHour',
     computed:{
      proid(){
          return  this.$store.state.pro.proid
      }
 },
    data() {
        return {
            newForm:this.row,

            userid:this.$store.state.userid,
            
            WorkHour: [{
                date: '',
                consume: '',
                remark: '',
                thedata: '',
                load: false,//控制按钮加载状态，每行单独一个控制
                unav1: false,//控制五个部件提交后的不可用状态
                unav2: false,
                unav3: false,
                unav4: false,
                unav5: false,
            }],
            colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
            dialogVisible: false,

        }
    },
    methods: {
        //删除一行
        WorkHourDelete(index, row) {
            console.log(row)
            this.WorkHour.splice(index, 1);
        },
        //插入一行
        WorkHourAdd() {
            if (this.WorkHour == undefined) {
                this.WorkHour = [];
            }
            let obj = {};
            obj.date = '';
            obj.consume = '';
            obj.remark = '';
            obj.load = false;//控制按钮加载状态，每行单独一个控制
            obj.unav1 = false;//控制五个部件提交后的不可用状态
            obj.unav2 = false;
            obj.unav3 = false;
            obj.unav4 = false;
            obj.unav5 = false;
            this.WorkHour.push(obj);
        },
        //关闭窗口
        handleClose() {
            this.dialogVisible = false;
            this.WorkHour = [{
                date: '',
                consume: '',
                remark: '',
                load: false,
                unav1: false,
                unav2: false,
                unav3: false,
                unav4: false,
                unav5: false,
            }];
        },
        WorkHourClose() {
            this.$emit("update:show", false);
        },
        //提交并锁定信息
        WorkHourSubLock(index, row) {
            this.$confirm('您要提交工时，请确认信息准确无误', '提交确认', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                //空值检测
                console.log(row)
                if (row.consume == '' || row.date == '') {
                    this.$message({
                        type: 'error',
                        message: '提交失败，请检查所有必填项是否填写'
                    })
                    return;
                }else{
                    row.load = true;
                    var submit={}
                    submit.messionname=this.row.messionname
                    submit.proid=this.proid
                    submit.assigner=this.userid
                    submit.date=row.date
                    submit.remark=row.remark
                    submit.worktime=row.consume
                    axios({
                        url:'/mession/addtime',
                        method:'POST',
                        params:{
                            addtime:JSON.stringify(submit)
                        }
                    }).then()
                }
                //向后端post,数据在row.date row.consume row.remark 以及父组件传来的三个id


                setTimeout(() => {
                    row.load = false;
                    row.unav1 = true;
                    row.unav2 = true;
                    row.unav3 = true;
                    row.unav4 = true;
                    row.unav5 = true;
                    this.$message({
                        type: 'success',
                        message: '已提交!'
                    });

                }, 1000);
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消提交'
                })
            });

        },
        addRedStar(h, { column }) { // 给表头加必选标识
            return [h('span', { style: 'color: red' }, '*'), h('span', ' ' + column.label)]
        }
    }
}

</script>



<style scoped lang="scss">
#workhour {
    // border: black solid 1px;
}
</style>
