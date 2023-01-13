import axios from 'axios'
// 标签
// 对于
//待开始
const TO_BEGIN='0'
//进行中
const IN_PROGRESS='1'
//已关闭
const CLOSED='2'
//已完成
const FINISHED='3'
//已取消
const CANCELED='4'
//全部
const ALL='%'

// 指派给我的
const ASSIGEN_TO_ME =1
const MINE=2
const MINE_CLOSED=3
const MINE_FINISHED=4
const MINE_CANCELED=5
const ALL_MISSION=6

function isCreater(status){
    if(status = MINE || status== MINE_CLOSED || MINE_CANCELED || ALL_MISSION)
        return 'getcreatermessionpage'
    else
        return 'getassignermessionpage'
}

function isCreaterList(status){
    if(status = MINE || status== MINE_CLOSED || MINE_CANCELED || ALL_MISSION)
        return 'getcreatermession'
    else
        return 'getassignermession'
}

function getTag(status){
    if(status == MINE_CLOSED)
        return CLOSED
    else if(status == MINE_FINISHED)
        return FINISHED
    else if(status == MINE_CANCELED)
        return CANCELED
    else if(status ==1 || status ==2 || status ==6)
        return ALL
}

async function PageAxios(userid,proid,url,tag){
    await axios({
        url: url,
        method: 'POST',
        params: {
          getpage: JSON.stringify({
            proid: proid,
            userid: userid,
            teg: tag,
            need: '%',
            moudle: '%',
          })
        }
      })
        .then(res => {
            var totalrow = res.data.number
            return totalrow
        })
}

async function ListAxios(userid,proid,url,tag,pagenum){
    await axios({
        url: url,
        method: 'POST',
        params: {
          getmession: JSON.stringify({
            proid: proid,
            userid: userid,
            teg: tag,
            need: '%',
            moudle: '%',
            loc: pagenum.toString()
          })
        }
      })
        .then(res => {
            var result = res.data.result
            return result
        })
}

async function totalPage(userid,status,proid){
    if(status == ALL_MISSION){
        //获取全部时url为特殊情况。
        var url="/check/getcreatermessionpage"
        var tag=getTag(status)
        var totalrow=PageAxios(userid,proid,url,tag)
        url ="getassignermessionpage"
        totalrow+=PageAxios(userid,proid,url,tag)
        return totalrow
        
    }
    else{
        var url="/check/"+isCreater(status)
        var tag=getTag(status)
        var totalrow=PageAxios(userid,proid,url,tag)
        return totalrow
    }
}

async function List(userid,status,proid,pagenum){
    if(status == ALL_MISSION){
        //获取全部时url为特殊情况。
        var url="/check/getallmession"
        var tag=getTag(status)
        var result=ListAxios(userid,proid,url,tag,pagenum)
        return result
    }
    else{
        //否则进行正常处理
        var url=isCreaterList(status)
        var tag=getTag(status)
        var result=ListAxios(userid,proid,url,tag,pagenum)
        return result
    }
    
}
export default{
     totalPage,
     List,
}
