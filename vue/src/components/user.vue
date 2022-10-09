<!-- <template>
  <div class="demo-type">
     <el-avatar> user </el-avatar>
  </div>
</template> -->
<template>
    
<el-dropdown @command="handlecommand">
  <el-avatar class="el-dropdown-link">{{username}} </el-avatar>
  <el-dropdown-menu slot="dropdown" >
    <el-dropdown-item command="0"><i class="el-icon-switch-button"></i>注销</el-dropdown-item>
  </el-dropdown-menu>
</el-dropdown>
</template>
<script>

export default {
  computed:{
    username(){
      return this.$store.state.username
    }
  },
  methods:{
    handlecommand(command){
      console.log(command)
     if(command=='0'){
      console.log('clear')
      sessionStorage.getItem("token","")
      this.$router.push({path:'/'})
     }
     
    }
  }
}
</script>
<style>
  .el-dropdown-link {
    cursor: pointer;
    margin: 10px;
  }
  .el-icon-arrow-down {
    font-size: 12px;
  }
</style>