
<template>
  <div>
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="vertical" @select="handleSelect"  >
      <template v-for="item in NavigateItem">
        <el-submenu v-if="item.items.length" :index="item.key" :key="item.key">
          <template slot="title">
            {{item.proid}}
          </template>
          <el-menu-item v-for="(items) in item.items" :key="items.key" :index="items.key">
            {{ items.proid }}
          </el-menu-item>
        </el-submenu>
        <el-menu-item v-else :index="item.key" :key="item.key">
          {{ item.proid }}
        </el-menu-item>
      </template>
    </el-menu>
  </div>
</template>
<script>
import axios from 'axios';
export default {
  created() {

    this.getproject()
      // console.log(this.NavigateItem)
    // this.nowTitle.proid = this.NavigateItem[0].items[0].proid

  },

  data(){
    return {
     userid:this.$store.state.userid,
      activeIndex:'',
      
      NavigateItem: [
        {
          proid:'项目',
          key:'1',
          limitgrade:'',
          items:[
          ]
        }
      ]
    };
  },


  methods: {
    handleSelect(key) {
      // console.log(key, keyPath);
      console.log(key)
      var query =this.NavigateItem[0].items.filter(
        function(_data){
          return _data.proid==key
        }
      )
      console.log(query[0].limitgrade)
      this.$store.commit('changeproid',key)
      this.$store.commit('changelimit',query[0].limitgrade)

      // this.$store.commit('changelimit')
      console.log(this.$store.state.limitgrade.limit)



      this.$router.push({
        params: { projectid: key }
      })
      var i = 0
      var list=this.NavigateItem[0].items
  

    },
    getproject() {
      axios({
        url:'/check/getproject',
        method:'POST',
        params:{getproject:JSON.stringify({userid:this.userid})}
        })
        .then(res => {
          console.log(res)
          this.NavigateItem[0].items=res.data.result
          for(var i=0;i<res.data.result.length;i++){
            // this.NavigateItem[0].items.push(res.data.result[i])
            this.NavigateItem[0].items[i].key=this.NavigateItem[0].items[i].proid
            this.NavigateItem[0].items[i].items=[]
          }           

          this.activeIndex = this.NavigateItem[0].items[0].key
          this.$store.commit('changeproid', this.NavigateItem[0].items[0].proid)
          this.$store.commit('changelimit', this.NavigateItem[0].items[0].limitgrade)
          console.log( this.NavigateItem[0].items)
           this.$router.push({
          path: '/home/project/' + this.NavigateItem[0].items[0].key + '/status/status6'
    })
          // console.log(this.NavigateItem);
        }).catch(err => {
          console.log(err)
        })
    },

  },
}
</script>