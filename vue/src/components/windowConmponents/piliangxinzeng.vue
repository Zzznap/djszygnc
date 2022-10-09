<template>
    <div id="piliang">


        <el-button type="primary" @click="dialogVisible = true">批量新增</el-button>
        <el-dialog title="批量新增" :visible.sync="dialogVisible" width="70%" :before-close="handleClose">
            <div id="workhour">
                <el-table :data="WorkHour" :model="WorkHour" height="500" style="width: 100%; height: 500px;"
                    :rules="rules">
                    <el-table-column prop="messionname" label="任务名" width="250" header-align="center"
                        :render-header="addRedStar">
                        <template slot-scope="scope">
                            <el-input type="textarea" placeholder="请输入内容" v-model="scope.row.messionname" maxlength="10"
                                rows="2" suffix-icon="false" :disabled="scope.row.unav1" show-word-limit>
                            </el-input>
                        </template>
                    </el-table-column>

                    <!-- <el-table-column prop="creater" label="创建人" type="text" width="140" header-align="center"
                        :render-header="addRedStar">
                        <template slot-scope="scope">
                            <el-input type="textarea" rows="2" v-model="scope.row.creater" placeholder="创建人" clearable>
                            </el-input>
                        </template>
                    </el-table-column>
                    <el-table-column prop="assigner" label="指派给" type="text" width="140" header-align="center"
                        :render-header="addRedStar">
                        <template slot-scope="scope">
                            <el-input type="textarea" rows="2" v-model="scope.row.assigner" placeholder="指派对象" clearable>
                            </el-input>
                        </template>
                    </el-table-column> -->
                    <el-table-column prop="description" label="描述" width="250" header-align="center">
                        <template slot-scope="scope">
                            <el-input type="textarea" placeholder="描述" v-model="scope.row.description"
                                :disabled="scope.row.unav2" maxlength="50" show-word-limit>
                            </el-input>
                        </template>
                    </el-table-column>


                    <el-table-column align="right" fixed="right" width="120px">
                        <template slot="header" slot-scope="{}">
                            <el-button size="mini" type="primary" style="margin-right: 40px;"
                                icon="el-icon-circle-plus-outline" @click="WorkHourAdd">新增
                            </el-button>
                        </template>
                        <template slot-scope="scope">
                            <el-button size="mini" type="success" icon="el-icon-check" :disabled="scope.row.unav4"
                                @click="WorkHourSubLock(scope.$index, scope.row)" :loading="scope.row.load" circle>
                            </el-button>
                            <el-button size="mini" type="info" @click="WorkHourDelete(scope.$index, scope.row)"
                                :disabled="scope.row.unav5" icon="el-icon-close" circle>
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>


            <span slot="footer" class="dialog-footer">
                <el-button @click="handleClose">关闭</el-button>
            </span>

        </el-dialog>
    </div>
</template>

<script>
import axios from 'axios';
export default {
    computed: {
        proid() {
            return this.$store.state.pro.proid
        }
    },
    name: 'WorkHour',
    data() {
        return {
            WorkHour: [{
                userid: this.$store.state.userid,
                creater: this.$store.state.userid,

                teg: '0',
                messionname: '',
                need: '',
                milepost: '',
                moudle: '',
                assigner: [],
                importance: '',
                starttime: '',
                endtime: '',
                costtime: '',
                description: '',
                commend: '',
                load: false,//控制按钮加载状态，每行单独一个控制
                unav1: false,//控制五个部件提交后的不可用状态
                unav2: false,

                unav4: false,
                unav5: false,
            }],
            colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
            dialogVisible: false

        }
    },
    props: {
        //接收父组件传来的id信息
        toid: []
        // proid: String,
        // messionid: String,
        // userid: String,

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
            obj.userid = this.$store.state.userid
            obj.creater = this.$store.state.userid
            obj.proid = this.proid
            obj.teg = '0';
            obj.messionname = '';
            obj.need = '';
            obj.milepost = '';
            obj.moudle = '';
            obj.assigner = [];
            obj.importance = '';
            obj.starttime = '';
            obj.endtime = '';
            obj.costtime = '';
            obj.description = '';
            obj.commend = '';
            obj.load = false;//控制按钮加载状态，每行单独一个控制
            obj.unav1 = false;//控制五个部件提交后的不可用状态
            obj.unav2 = false;

            obj.unav4 = false;
            obj.unav5 = false;
            this.WorkHour.push(obj);
        },
        //关闭窗口
        handleClose() {
            this.dialogVisible = false;
            this.WorkHour = [{
                teg: '0',
                messionname: '',
                description: '',
                load: false,
                unav1: false,
                unav2: false,
                unav3: false,
                unav4: false,
                unav5: false,
            }];
        },
        WorkHourClose() {
            this.dialogVisible = "false";
            this.$emit("update:show", false);
        },
        //提交并锁定信息
        WorkHourSubLock(index, row) {
            this.$confirm('您要提交任务，请确认信息准确无误', '提交确认', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                //空值检测
                console.log(row)
                row.proid = this.proid
                // if (row.messionname == '' || row.description == '' || row.creater == '' || row.assigner == '') {
                if (row.messionname == '' || row.description == '') {

                    this.$message({
                        type: 'error',
                        message: '提交失败，请检查所有必填项是否填写'
                    })
                    return;
                } else {
                    row.load = true;
                    axios({
                        url: '/mession/addmession',
                        method: 'POST',
                        params: {
                            addmession: encodeURIComponent(JSON.stringify(row))
                        }
                    }).then(res => {
                        console.log(res)
                        if (res.data.status == 'success') {
                            this.$message('新增成功!')
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
                            }, 100);
                        } else {
                            this.$message.error('任务名重复，新增失败!')

                        }
                    })

                }
                //向后端post,数据在row.date row.consume row.description 以及父组件传来的三个id



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
</style>
