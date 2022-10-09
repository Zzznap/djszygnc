<template>


  <div>
      <buttonControl v-if="this.$store.state.limitgrade.limit==1"></buttonControl>



      <el-tabs v-model ="activeName" :tab-position="tabPosition"  @tab-click="handleClick">
      
        <el-tab-pane name="status1" label="指派给我" >指派给我</el-tab-pane>
        <el-tab-pane name="status2" label="我创建的" >我创建的</el-tab-pane>
        <el-tab-pane name="status3" label="我关闭的">我关闭的</el-tab-pane>
        <el-tab-pane name="status4" label="我完成的">我完成的</el-tab-pane>
        <el-tab-pane name="status5" label="我取消的">我取消的</el-tab-pane>
        <el-tab-pane name="status6" label="全部">全部</el-tab-pane>
      </el-tabs>


    <router-view :statusid="statusid" ></router-view>

    </div>
    
</template>


<script>
import buttonControl from './windowConmponents/buttoncontrol.vue'
export default {
  // computed:{
  //   limitgrade(){
  //     console.log(this.$store.state.limitgrade.limit)
  //     return this.$store.state.limitgrade.limit
  //   }
  // },
  components:{
    buttonControl
  },
  // components:{
  //   Test
  // },
mounted(){
  this.$router.push({
          name:'status',params:{statusid:'status6'}
        })
},
  methods:{
    handleClick(row) {
      this.$router.push({
        name:'status',params:{statusid:row.name}
        
        })
        // this.$set(this.statusid,row.name)
        this.statusid=row.name.split('')[6]
        console.log(this.statusid,row.name)
     },
  },
  data() {
    
    return {
      statusid:'6',
      tabPosition: 'bottom',
      activeName:'status6',      
      tableData: [],
    }
  }
}
</script>