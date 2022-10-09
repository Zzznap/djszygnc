
<template>
    <div  style="margin-top:65px">
        <el-tree 
        :data="data" 
        :props="defaultProps"  
        :check-on-click-node="true"
        @node-click="handleNodeClick"></el-tree>
    </div>
</template>

<script>
export default {
    data() {
        return {
            data: [{
                labelid:'0',
                label: '全部',
            }, {
                labelid:'-1',
                label: '系统管理',
                children: [{
                    labelid:'1',
                    label: '系统角色',
                    
                }, {
                    labelid:'2',
                    label: '数据字典',
                  
                }]
            }, {
                labelid:'3',
                label: '项目设置',
            },{                
                labelid:'4',
                label:'项目需求'
            }
            ],
            defaultProps: {
                children: 'children',
                label: 'label'
            }
        };
    },
    methods: {
        handleNodeClick(data) {
            console.log(data);
        }
    }
};
</script>