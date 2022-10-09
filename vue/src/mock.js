//引入mockjs
import Mock from 'mockjs'

Mock.setup({
  timeout:'500-600'
})
//使用mockjs模拟数据
Mock.mock('http://localhost:8080/getlist', {
        'code':1,'data|100':[
          {
            "proid:":/[A-Z][a-z][a-z][a-z][a-z][a-z]/,
            "messionname":/[A-Z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][0-9]/,
            "need|0-10":10,
            "moudle|0-10":10,
            "messId|10000000-99999999":99999999,
            "starttime": "@datetime",//随机生成日期时间
            "endtime":"@datetime",
            "importance|0-5":5,
            "teg|0-4":4,
            "creater":"@cname",
            "assigner":"@cname",
            "costtime|0-24" :24,
            "description" :/[A-Z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][0-9]/,
            "milepost":/[A-Z][a-z][a-z][a-z]/,
            "commend":/[A-Z][a-z][a-z][a-z]/
          }
        ]
});
Mock.mock('http://localhost:8080/getlist2',
      function(option){
        return Mock.mock(
          {
            'code|+1':1,'data|10':[
              {
                "proid:":/[A-Z][a-z][a-z][a-z][a-z][a-z]/,
                "messionname":/[A-Z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][0-9]/,
                "need|0-10":10,
                "moudle|0-10":10,
                "messId|10000000-99999999":99999999,
                "starttime": "@datetime",//随机生成日期时间
                "endtime":"@datetime",
                "importance|0-5":5,
                "teg|0-4":4,
                "creater":"@cname",
                "assigner":"@cname",
                "costtime|0-24" :24,
                "description" :/[A-Z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][0-9]/,
                "milepost":/[A-Z][a-z][a-z][a-z]/,
                "commend":/[A-Z][a-z][a-z][a-z]/
              }
            ]
    }
        )
      }
       
);
Mock.mock('http://localhost:8080/getproject',{
       'code':0,'data':[
         {
           "title": "项目",
           "key":/[0-9][0-9][0-9][0-9][0-9][0-9][0-9]/,
            "items|5":[{
              
                "title": /[A-Z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][0-9]/,
                "key":/[0-9][0-9][0-9][0-9][0-9][0-9][0-9]/,
                 "items":[]
            }]
          }
        ]
 });
 Mock.mock('http://localhost:8080/getTotalPagesize',{
        "number|0-1000":1000
 })
/**
 *        messName:'',
         messId:'',
         startTime:'',
         endTime:'',
         status:'',
         startPeople:'',
         tokenPeople:'',
         focastTime:'',
 * 
 */