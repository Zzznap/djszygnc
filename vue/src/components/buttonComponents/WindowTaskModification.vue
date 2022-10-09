<template>
  <div id="tm">
    <el-button type="text" @click="dialogVisible = true" style="font-size:20px;padding:0px"><i class="el-icon-edit"></i>
    </el-button>
    <el-dialog title="任务修改" :visible.sync="dialogVisible" width="55%" :append-to-body="true" @open="handleopen"
    >
      <el-form :model="newForm" :rules="rules" ref="newForm" label-width="100px" class="demo-ruleForm"
        v-loading="loading">
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
              <el-form-item label="指派给" prop="assigner">
                <el-select v-model="newForm.assigner" placeholder="请选择指派对象" multiple>
                  <el-option v-for="item in options" :key="item.userid" :label="item.username" :value="item.userid">
                    {{ item.username }}
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
                <el-input v-model="newForm.costtime" placeholder="请输入预计消耗" style="width: 220px;" clearable></el-input>
              </el-form-item>
            </div>
          </el-col>
          <el-col :span="12">
            <div class="grid-content ">
            </div>
          </el-col>
        </el-row>

        <div style="overflow: hidden;">
          <!-- <el-form-item label="描述" style="width: 80%;" prop="description">
            <el-input type="textarea" :rows="6" v-model="newForm.description" clearable placeholder="请输入正文"
              maxlength="100" show-word-limit></el-input>
          </el-form-item> -->
          <el-form-item label="描述" style="width: 80%;" prop="description" >
            <vue-tinymce v-model="newForm.description" :setting="setting"  v-if="dialogVisible"/>
          </el-form-item>
        </div>

        <el-row :gutter="20">
          <el-col :span="12">
            <div class="grid-content ">
            </div>
          </el-col>
          <el-col :span="12">
            <div class="grid-content" styl>
              <div style="float:left;margin-top: 16px;margin-right: 20px; margin-left: 40%;">
                <el-button @click="ModificationClose('newForm')" plain>取消</el-button>
              </div>
              <div style="float:left;margin-top: 16px;margin-right: 5px;">
                <el-button type="primary" @click="submitNewForm('newForm')" plain>提交</el-button>
              </div>
            </div>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  //父组件需要传一个obj，用于初始化修改的内容，包括表单所有的数据还有项目id
  //修改任务的接口：向后端post 修改前的任务名 + 项目id + 修改后的所有数据
  props: ['row'],
  watch: {
    row(newVal, oldVal) {
      // newVal是新值，oldVal是旧值
      // Object.assign(this.newForm,this.row)
      this.newForm = JSON.parse(JSON.stringify(newVal))
      delete this.newForm.assigner
      this.newForm.oldmessionname = this.newForm.messionname


    }
  }, computed:{
      proid(){
          return  this.$store.state.pro.proid
      }
 },
    activated() {
         this.tinymceFlag++
     },

  data() {
    return {
      tinymceFlag:1,
      newForm: this.row,
      userid: this.$store.state.userid,

      options: [],
      rules: {
        messionname: [
          { required: true, message: '请输入任务名称', trigger: 'blur' },
        ],
        description: [
          { required: true, message: '请填写描述', trigger: 'blur' }
        ]
      },
      dialogVisible: false,
      loading: false,

      setting: {
        menubar: true, // 隐藏菜单栏
        // autosave_restore_when_empty: true,
        //  save_enablewhendirty : true,
        // min_height: 10000,
        autoresize_on_init: false,
        toolbar:
          "code undo redo restoredraft | cut copy paste pastetext | forecolor backcolor bold italic underline strikethrough link anchor | alignleft aligncenter alignright alignjustify outdent indent | \
        styleselect formatselect fontselect fontsizeselect | bullist numlist | blockquote subscript superscript removeformat | \
        table image media charmap emoticons hr pagebreak insertdatetime print preview | fullscreen | myUpload",
        //工具栏，根据需要写对应的工具名称，顺序及分割线等等，这里的最后的 ‘myUpload’ 是下面setup里的自定义插件
        toolbar_mode: "sliding", // 工具栏模式，这里是滑行模式（当屏幕过小时，多余的工具会隐藏，点击更多按钮会出现其他工具，这里配置出现的特效）
        plugins:
          "preview searchreplace autolink directionality visualblocks visualchars fullscreen image link media template code codesample table charmap pagebreak nonbreaking anchor insertdatetime advlist lists wordcount help emoticons autosave autoresize", // 需要用到的功能插件，如链接，列表等等
        language: 'zh_CN',
        branding: false, // 隐藏品牌，隐藏状态栏中显示的“ Powered by Tiny ”链接
        resize: true, // 是否可以缩放编辑器
        elementpath: false, // 在编辑器底部的状态栏中禁用元素路径。
        fixed_toolbar_container: "#tinymceToolbar", // 可以设置元素选择器指定工具栏嵌套在哪个元素里面
        custom_ui_selector: "body", // 聚焦的元素
        noneditable_noneditable_class: "mceNonEditable", // 使用此选项，您可以指定TinyMCE将使用的类名称，以确定使用noneditable插件时可编辑哪些内容区域。主要用入你想以代码的形式添加某些内容，并给这些内容设置类名，使他们不可编辑，只能整个删除
        height: 550,
        init_instance_callback: (editor) => {
          editor.focus(); // 初始化聚焦，让内联模式的编辑器工具显示
        },
      },
      };
    },
      methods: {
      handleopen(){

        axios({
          url: '/check/getuserbyproid',
          method: 'POST',
          params: {
            getuserbyproid: JSON.stringify({ proid: this.proid, userid: this.userid })
          }
        }).then(res => {
          this.options = res.data.result
        })
      },
      ModificationClose(formName) {
        if (this.$refs[formName] !== undefined) {
          this.$refs[formName].resetFields();
        }
        this.dialogVisible = false;
      },
      submitNewForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {

            //     alert('submit!');
            this.$confirm('您要提交任务修改，请确认信息准确无误', '修改确认', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              //向后端axios.post
              this.newForm.creater = this.userid
              console.log(this.newForm)
              axios({
                url: '/mession/updatemession',
                method: 'POST',
                params: {
                  updatemession: encodeURIComponent(JSON.stringify(
                    this.newForm)
                  )
                }
              }).then(res => {
                console.log(res)
                if (res.data.status == 'success') {
                  this.$message('修改成功!')
                } else {
                  this.$message.error('任务名重复，修改失败!')

                }
              })
              console.log(this.newForm);




              this.loading = true;
              setTimeout(() => {

                this.loading = false;

                //清空表单
                if (this.$refs[formName] !== undefined) {
                  this.$refs[formName].resetFields();
                }
                this.dialogVisible = false;
              }, 1000);
              //后面把延时改成if判断

            }).catch(() => {
              this.$message({
                type: 'info',
                message: '已取消提交'
              })
            });


          } else {

            return false;
          }
        });


      }

    }

  }
</script>

<style>
.el-row {
  margin-bottom: 20px;
}

.grid-content {
  min-height: 52px;
}
</style>