<!-- 任务取消弹窗 -->

<template>
  <div id="st">
    
    <el-button type="text" @click="open" style="font-size:20px;padding:0px" ><i class="el-icon-circle-close"></i></el-button>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  
  props:["row"], 
  computed:{
      proid(){
          return  this.$store.state.pro.proid
      }
 },
  data() {
    return {

      fullscreenLoading: false,
      success_dialogVisible: false,
      fail_dialogVisible: false
    }
  },
  methods: {
    open() {
      const h = this.$createElement;
      this.$msgbox({
        title: '确认消息',
        message: h('p', null, [
          h('span', null, '确定要'),
          h('span', { style: 'color:red' }, '取消'),
          h('span', null, '任务吗？'),
        ]),
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        beforeClose: (action, instance, done) => {
          if (action === 'confirm') {
            JSON.stringify({proid:this.proid,
                messionname:this.row.messionname})
            //向后端发送开始任务请求，后端更新任务状态，并获取返回值success 或 fail
            axios({
              url:'/check/deletions',
              method:'POST',
              params:{
                deletions:JSON.stringify({proid:this.proid,
                messionname:this.row.messionname})
                
              }
            })
            instance.confirmButtonLoading = true;
            instance.confirmButtonText = '执行中...';
            setTimeout(() => {
              done();
              setTimeout(() => {
                instance.confirmButtonLoading = false;
              }, 300);
            }, 1000);
          } else {
            done();
          }
        }//.then中放入后端回传的参数success或fail，然后用if判断从而发送相应信息。此处以显示成功消息为例
      }).then(() => {
        this.$message({
          message: '任务状态已更新为‘已取消’',
          type: 'warning'
        });
        // if () {
        //   this.$message({
        //     message: '任务状态已更新为‘已取消’',
        //     type: 'success'
        //   });
        // } else {
        //   this.$message.error('任务状态更新失败，请稍后重试');
        // }


      });
    }
  }
}
</script>
