  <!-- <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <div>num1*num2={{num}}</div>
    <div>num1:{{num1}}</div>
    <div>num2:{{num2}}</div>
    <button @click="num1++">增加num1 </button>
    <button @click="num2++">增加num2 </button>
    <button v-for="(item,index) in list" :key="index">{{item}}</button>
    <a v-bind:href="url">地址</a>
  </div> -->
  <!-- <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
  </div> -->
<template >
  <div style="background-color:#e8edf7; height:100%; position: absolute; top: 0; left: 0; width: 100%;">

    <el-container style="height: 64px; margin-bottom: 16px; border: #ccc solid 1px; background-color: white;">
      <el-header>
        <div style="float: left; width:14%">
          <img style="height: 60px;" src="../assets/logo.png"></img>
        </div>

        <div style="height:60px;width:80%;background-color:white; float: left; overflow: hidden; ">
          <img style="height: 60px;" src="../assets/bar.png"></img>
        </div>

        <div style="float:left; height: 60px; width:4%; overflow: hidden;background-color: white;">
          <User></User>
        </div>
      </el-header>
    </el-container>
    <el-container>



    </el-container>
    <el-container>
      <el-aside style="width:15%  margin-left: 20px;">
        <Menu></Menu>
      </el-aside>
      <el-main style="background-color: white; height: 750px; margin-left: 20px ;">
        <!-- <MainInfo></MainInfo> -->
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import HelloWorld from '@/components/HelloWorld.vue';
import MainInfo from '@/components/MainInfo.vue';
import AsideMenu from '@/components/AsideMenu.vue'
import Menu from '@/components/Menu.vue';
import User from '@/components/user.vue'
import buttonControl from '@/components/windowConmponents/buttoncontrol.vue'
export default {
  data() {
    return {
      num1: 0,
      num2: 0,
      list: [1, 1, 2, 3, 5, 8, 13],
      url: "https://www.bilibili.com",
    };
  },
  props: {
    msg: String,
  },
  computed: {
    num() {
      var numc = this.num1 + this.num2;
      return numc;
    },
  },
  mounted() {
    this.num1 = 114514;
  },
  components: {
    HelloWorld,
    MainInfo,
    AsideMenu,
    Menu,
    User,
    buttonControl
  }
};
</script>
