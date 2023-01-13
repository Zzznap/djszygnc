<template slot-scope="scope">
    <div>
        
        <el-button-group class="group" >
            <CancelTask class="left buttonicon" :row="row" ></CancelTask>
            <accomplish class="left buttonicon" :row="row"></accomplish>
            <Hours class="left buttonicon" :row="row"></Hours>
            <Start class="left buttonicon" :row="row"></Start>
            <Modification class="left buttonicon" :row="row"></Modification>
            <WindowEvaluate class="left buttonicon" :row="row"></WindowEvaluate>
        </el-button-group>
    </div>
</template>
<script>
import CancelTask from './CancelTask.vue'
import Accomplish from './WorkingAccomplish.vue'
import Hours from './WindowWorkingHours.vue'
import Start from './WorkingStart.vue'
import Modification from './TaskModification.vue'
import WindowEvaluate from './Evaluate.vue'
export default{
  props:['row'],
  
    components:{
    WindowEvaluate,
    CancelTask,
    Accomplish,
    Hours,
    Start,
    Modification
},
    methods:{
        h2click(){
        }
    }
}
</script>
<style>
.left{
    float:left
}
.buttonicon{
    font-size: 20px;
    height: 20px;
}
.group{
    text-align: center;
    vertical-align: middle;
}

</style>