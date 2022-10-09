<template>

  <body id="poster">
    <el-form class="form" :model="form" label-width="60px" label-position="left">
      <h2 class="login_title">登录窗口</h2>
      <el-form-item label="用户名:">
        <el-input type="text" v-model="form.userid" placeholder="请输入您的用户名"></el-input>
      </el-form-item>
      <el-form-item label="密码:">
        <el-input type="password" v-model="form.password" placeholder="请输入您的密码"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit" id="button">登录账号</el-button>
        <el-button @click="onRegister" id="button">立刻注册</el-button>
      </el-form-item>
    </el-form>
  </body>
</template>

<script>
import axios from 'axios'

export default {
  name: 'VueLogin',
  data() {
    return {
      form: {
        userid: '',
        password: '',
        token: ''
      }
    }
  },
  methods: {
    onSubmit() {
      //console.log('submit!');
      if (sessionStorage.getItem("token") == null) {
        sessionStorage.setItem("token", "")
      }
      axios({
        url: '/user/login',//////////////////端口号注意修改
        method: 'POST',
        params: {
          login: JSON.stringify(this.form)
        }
      }, {
        headers: {
          'Content-Type': 'application/json',
          // 'Authorization': sessionStorage.getItem("token")
        }
      })
        .then((resp) => {
          let data = resp.data
          console.log(resp)

          if (data.status == "登录成功") {
            sessionStorage.setItem("token", data.token)
            axios.defaults.headers.common['Authorization'] = sessionStorage.getItem("token");
            console.log(sessionStorage.getItem("token"))

            this.$message({
              message: resp.data.status,
              type: 'success'
            })
            console.log(this.form)
            this.$store.commit('changeuserid',this.form.userid)
            this.$store.commit('changeusername',resp.data.username)
            console.log(this.$store.state.userid)
            console.log(this.$store.state.username)

            this.$router.push({path:'/home'})//跳转到主页面的路由
          }
          else {
            this.$message({
              message: resp.data.status+'!',
              type: 'error'
            })
          }
          this.form = {}
        })
    },
    onRegister() {
      this.$router.push({ path: 'user/VueRegister' })
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

.login_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458
}

#button {
  position: relative;
  left: -20px;
}
</style>