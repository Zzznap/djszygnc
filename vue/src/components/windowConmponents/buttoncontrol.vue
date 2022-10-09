<template>
    <div>
        <analysis class="right buttonmargin" ></analysis>
        <addInfos class="right buttonmargin " ></addInfos>
        <addInfo class="right buttonmargin" ></addInfo>
    </div>
</template>
<script>
import analysis from './gongshifenxi.vue'
import addInfo from './xinzengrenwu.vue'
import addInfos from './piliangxinzeng.vue'


export default{
    components:{
        analysis,
        addInfo,
        addInfos
    }
}
</script>
<style>
.right{
    float: right;
}
.buttonmargin{
    margin-left: 10px;
    margin-right: 10px;
}
</style>