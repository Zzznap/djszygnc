<template>
  <div id="piliang">
    <el-button type="primary" @click="dialogVisible = true">新增任务</el-button>
    <el-dialog title="新增任务" :visible.sync="dialogVisible" :before-close="handleClose" width="55%" :append-to-body="true"
      :destroy-on-close="true" @open="handleopen">

      <div id="bodyForm" style="width:100%;height:520px;">
        <el-form :model="newForm" :rules="rules" ref="newForm" label-width="100px" class="demo-ruleForm">

          <el-row :gutter="20">
            <el-col :span="12">
              <div class="grid-content" style="overflow: hidden;">
                <el-form-item label="任务名称" prop="messionname">
                  <el-input v-model="newForm.messionname" clearable placeholder="请输入任务名称" style="width: 220px;">
                  </el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="12">
              <div class="grid-content" style="overflow: hidden;">
                <el-form-item label="相关需求" prop="need">
                  <el-select v-model="newForm.need" placeholder="请选择相关需求">
                    <el-option label="额外资金" value="额外资金"></el-option>
                    <el-option label="新增后端工程师" value="新增后端工程师"></el-option>
                    <el-option label="新增前端工程师" value="新增前端工程师"></el-option>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <div class="grid-content " style="overflow: hidden;">
                <el-form-item label="里程碑" prop="milepost">
                  <el-select v-model="newForm.milepost" placeholder="请选择里程碑">
                    <el-option label="需求分析完毕" value="需求分析完毕"></el-option>
                    <el-option label="前后端设计完成" value="前后端设计完成"></el-option>
                    <el-option label="前后端连接成功" value="前后端连接成功"></el-option>
                    <el-option label="交付使用" value="交付使用"></el-option>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="12">
              <div class="grid-content " style="overflow: hidden;">
                <el-form-item label="相关模块" prop="moudle">
                  <el-select v-model="newForm.moudle" placeholder="请选择相关模块">
                    <el-option label="登录模块" value="登录模块"></el-option>
                    <el-option label="数据库模块" value="数据库模块"></el-option>
                    <el-option label="运营模块" value="运营模块"></el-option>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <div class="grid-content " style="overflow: hidden;">
                <el-form-item label="指派给" prop="assiner">
                    <el-select v-model="value" multiple placeholder="请选择指派对象" :popper-append-to-body="false"
                    @remove-tag="removeTag">
                    <el-option v-for="item in options" :key="item.userid" :label="item.username" :value="item.userid">
                    <el-checkbox>
                    {{ item.username }},
                    </el-checkbox>
                    </el-option>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="12">
              <div class="grid-content " style="overflow: hidden;">
                <el-form-item label="重要程度" prop="importance">
                  <el-select v-model="newForm.importance" placeholder="请选择重要程度">
                    <el-option label="一般" value="一般"></el-option>
                    <el-option label="中等" value="中等"></el-option>
                    <el-option label="紧急" value="紧急"></el-option>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
          </el-row>


          <el-row :gutter="20">
            <el-col :span="12">
              <div class="grid-content " style="overflow: hidden;">
                <el-form-item label="开始时间" prop="starttime">
                  <el-form-item prop="date1">
                    <el-date-picker type="date" placeholder="开始日期" value-format="yyyy-MM-dd" format="yyyy 年 MM 月 dd 日"
                      v-model="newForm.starttime" style="width: 220px;">
                    </el-date-picker>
                  </el-form-item>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="12">
              <div class="grid-content " style="overflow: hidden;">
                <el-form-item label="结束时间" prop="endtime">
                  <el-form-item prop="date1">
                    <el-date-picker type="date" placeholder="结束日期" value-format="yyyy-MM-dd" format="yyyy 年 MM 月 dd 日"
                      v-model="newForm.endtime" style="width: 220px;">
                    </el-date-picker>
                  </el-form-item>
                </el-form-item>
              </div>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <div class="grid-content " style="overflow: hidden;">
                <el-form-item label="预计消耗" prop="cost">
                  <el-input v-model="newForm.cost" placeholder="请输入预计消耗" style="width: 220px;" clearable></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="12">
              <div class="grid-content ">
              </div>
            </el-col>
          </el-row>

          <div style="overflow: hidden;">
            <el-form-item label="描述" style="width: 80%;" prop="describe">
              <el-input type="textarea" :rows="6" v-model="newForm.describe" placeholder="请输入正文" maxlength="100"
                show-word-limit></el-input>
            </el-form-item>
          </div>

          <el-row :gutter="20">
            <el-col :span="12">
              <div class="grid-content ">
              </div>
            </el-col>
            <el-col :span="12">
              <div class="grid-content">
                <div style="float:left;margin-top: 16px;margin-right: 20px; margin-left: 40%;">
                  <el-button type="primarySub" @click="dialogVisible = false">取消</el-button>
                </div>
                <div style="float:left;margin-top: 16px;margin-right: 5px;">
                  <el-button type="primary" @click="submitNewForm('newForm')">提交</el-button>
                </div>
              </div>
            </el-col>
          </el-row>

        </el-form>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// import axios from 'axios';
export default {

  data() {
    return {
      userid:this.$store.state.userid,
      loading: false,
      // options: [],
      value: [],
      list: [],
      newForm: {
        creater: this.userid,
        teg: '0',
        messionname: '',
        need: '',
        milepost: '',
        moudle: '',
        assigner: '',
        importance: '',
        starttime: '',
        endtime: '',
        costtime: '',
        description: '',
        proid: this.$store.state.userid,
         options: [{
          value: '选项1',
          label: '黄金糕'
        }, {
          value: '选项2',
          label: '双皮奶'
        }, {
          value: '选项3',
          label: '蚵仔煎'
        }, {
          value: '选项4',
          label: '龙须面'
        }, {
          value: '选项5',
          label: '北京烤鸭'
        }],
        value1: [],
      },
      dialogVisible: false,
      rules: {
        messionname: [
          { required: true, message: '请输入任务名称', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10个字符', trigger: 'blur' }
        ],
        desc: [
          { required: false, message: '请填写描述', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
        handleopen(){
          console.log(this.proid)
          console.log(JSON.stringify({proid:this.proid,userid:this.userid}))
            axios({
              url:'/check/getuserbyproid',
              method:'POST',
              params:{
                getuserbyproid:JSON.stringify({proid:this.proid,userid:this.userid})
              }
            }).then(res=>{
              this.options=res.data.result
              console.log(this.options)
            })
        },
        isChecked(item) {
          console.log(item)
          if (item.check && this.value.indexOf(item.value) == -1) {
            this.value.push(item.value)
          } else if (!item.check) {
            this.value.forEach((elm, idx) => {
              if (elm == item.value) {
                this.value.splice(idx, 1)
              }
            })
          }
        },removeTag(value) {
          this.options.forEach((elm, idx) => {
            if (elm.value == value) {
              elm.check = false
            }
          })
        },
        handleClose(done) {
          this.$confirm('关闭将不会保存已填信息，确认关闭？')
            .then(_ => {
              done();
            })
            .catch(_ => { });
        },
        submitNewForm(formName) {
          this.$refs[formName].validate((valid) => {
            if (valid) {


              // for(let i in this.newForm){
              //   if(this.newForm[i] === ""){
              //   delete this.newForm[i]
              //   }
              // }
              console.log(this.newForm)
              console.log(encodeURIComponent(JSON.stringify(this.newForm)))
              axios({
                url: '/mession/addmession',
                method: 'POST',
                params: {
                  addmession: JSON.stringify(encodeURIComponent(JSON.stringify(this.newForm))
    )
                }
              }).then(res => {
                console.log(res)
              })
              alert('submit!');
            } else {
              alert('error submit!!');
              return false;
            }
          });
        },

        // showmes(){
        //    console.log(this.gongshiForm.proj+" "+this.gongshiForm.data1+" "+this.gongshiForm.data2);
        // }
  }
};

</script>



<style scoped lang="scss">
.grid-content {
  min-height: 52px;
}
</style>
