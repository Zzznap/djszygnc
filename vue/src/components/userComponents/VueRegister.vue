<template>
  <body id="poster">
    <el-form class="form" :model="form" label-width="60px" label-position="left">
      <h2 class="register_title">注册窗口</h2>
      <el-form-item label="用户id:">
        <el-input type="text" v-model="form.userid" placeholder="请输入您的用户id"></el-input>
      </el-form-item>
      <el-form-item label="用户名:">
        <el-input type="text" v-model="form.username" placeholder="请输入您的用户名"></el-input>
      </el-form-item>
      <el-form-item label="密码:">
        <el-input type="password" v-model="form.password" placeholder="请输入您的密码"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit" id="button">注册账号</el-button>
        <el-button @click="onLogin" id="button">立刻登录</el-button>
      </el-form-item>
    </el-form>
  </body>
</template>
<script>
import axios from 'axios'

export default {
  name: 'VueRegister',
  data() {
    return {
      form: {
        userid: '',
        password: '',
        username:''
      }
    }
  },
  methods: {
    onSubmit() {
      console.log('request')
      axios({
        url: '/user/register',//////////////////端口号注意修改
        method: 'POST',
        params: {
          getmission: JSON.stringify(this.form)
        }
      })
        .then((resp) => {
          console.log(resp)
          
          if (resp.data.status=="注册成功") {
            this.$message({
              message: resp.data.status,
              type: 'success'
            })
            this.$router.push({path:'/user'})
          }
          else{
            this.$message({
                message: resp.data.status,
                type:'error'
            })
          }
        this.form={}
        })
    },
    onLogin() {//跳转回登陆界面
      this.$router.push({path: '/user'})
    }
  }
}

</script>

<style>
#poster {
  background-position: center;
  height: 100%;
  width: 100%;
  background-size: cover;
  position: fixed;
}

body {
  margin: 0px;
  padding: 0px;
}

.form {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 90px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.register_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458
}

#button{
  position: relative;
  left: -20px;
}
</style>