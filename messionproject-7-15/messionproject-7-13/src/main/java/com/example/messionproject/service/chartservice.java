package com.example.messionproject.service;

import com.alibaba.fastjson.JSONObject;
import com.example.messionproject.entity.Worktime;
import com.example.messionproject.mapper.chartMapper;
import com.example.messionproject.mapper.checkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class chartservice {
    @Autowired
    chartMapper chart;
    //获取时间段的工时信息
    public  JSONObject getdateworktimeinforservice(String message) throws ParseException {
        JSONObject js=new JSONObject();
        if(message.equals("")||message==null){
            System.out.println("getdateworktimeinforservice:Inputnull");
            js.put("status","Inputnull");
            return js;
        }
        System.out.println("getdateworktimeinforservice:message:"+message);
        JSONObject object=JSONObject.parseObject(message);
        List<String> list=splitdate(object.getString("starttime"), object.getString("endtime") );
        //任务名筛选
        List<List<String>> workli=new ArrayList<>();
        List<String> lis=new ArrayList<String>();
        //筛除重复任务名
        List<String> li=chart.getallmessionname(object.getString("proid"));
        Map<String,String>ke=new HashMap<>();
        for(int i=0;i<li.size();i++){
            if(ke.containsKey(li.get(i))){
                continue;
            }
            ke.put(li.get(i),"1");
            lis.add(li.get(i));
        }
        lis.add("平均时间");
        workli.add(lis);
        workli.add(list);
        System.out.println("getdateworktimeinforservice:已筛除重复任务名");
        //获取每日工时信息
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
            workli.add(getperdayworktime(object.getString("proid"),list.get(i)));
        }
        System.out.println("getdateworktimeinforservice:已成功获取每日的工时信息");
        js.put("worktimelist",workli);
        js.put("status","success");
        System.out.println("getdateworktimeinforservice:成功运行");
        return js;
    }
    //获取时间段内缺勤情况
    public JSONObject getabsenceinforservice(String message) throws ParseException {
        JSONObject js=new JSONObject();
        if(message.equals("")||message==null){
            System.out.println("getabsenceinforservice:Inputnull");
            js.put("status","Inputnull");
            return js;
        }
        System.out.println("getabsenceinforservice:message:"+message);
        JSONObject object=JSONObject.parseObject(message);
        //统计每人工时填报情况
        List<Worktime> wo=getallworktime(object.getString("proid"),object.getString("starttime"),object.getString("endtime"));
        System.out.println(object.getString("starttime"));
        System.out.println(object.getString("endtime"));
        System.out.println("getabsenceinforservice:已成功获取时间段内所有工时信息");
        //获取项目任务情况
        List<String> li=chart.getmessionnamefrommession(object.getString("proid"));
        List<String> lis=new ArrayList<String>();
        //筛除重复任务名
        Map<String,String>ke=new HashMap<>();
        for(int i=0;i<li.size();i++){
            if(ke.containsKey(li.get(i))){
                continue;
            }
            ke.put(li.get(i),"1");
            lis.add(li.get(i));
        }
        System.out.println("getabsenceinforservice:已筛除重复任务名");
        //按日期初始化工时填报情况,筛除重复被指派者
        List<String>assi=new ArrayList<String>();
        for(int i=0;i<lis.size();i++){
            List<String> assignerlis=getallassigner(object.getString("proid"),lis.get(i));
            for(int j=0;j<assignerlis.size();j++){
                if(assi.contains(assignerlis.get(j))){
                    continue;
                }
                assi.add(assignerlis.get(j));
                System.out.println("getabsenceinforservice:当前获取assigner为:"+assignerlis.get(j));
            }
        }
        System.out.println("getabsenceinforservice:已成功筛选出assigner");
        //分割日期
        List<String> datelist=splitdate(object.getString("starttime"), object.getString("endtime"));
        System.out.println("getabsenceinforservice:已成功获取日期信息");
        //初始化工时统计
        Map<String,Map<String,String>>absencemap=new HashMap<>();
        for(int i=0;i<datelist.size();i++){
            absencemap.put(datelist.get(i),new HashMap<String,String>());
            for (int j=0;j<assi.size();j++){
                absencemap.get(datelist.get(i)).put(assi.get(j),"0");
            }
            System.out.println("getabsenceinforservice:已成功初始化"+datelist.get(i));
        }
        System.out.println("getabsenceinforservice:absencemap已成功初始化");
        //工时填报统计
        for(int i=0;i<wo.size();i++){
            String date=getStringbyDate(getDatebyString(wo.get(i).getDate()));
            System.out.println(date);
            Map<String,String>map=absencemap.get(date);
            if(!map.containsKey(wo.get(i).getAssigner())){
                continue;
            }
            String value=map.get(wo.get(i).getAssigner());
            System.out.println("getabsenceinforservice:此次获取的值为："+wo.get(i).getAssigner()+" :"+wo.get(i).getWorktime()+" "+value);
            if(value.equals("1")){
                continue;
            }
            value="1";
            System.out.println("res="+value);
            map.put(wo.get(i).getAssigner(),value);
            absencemap.put(date,map);
            System.out.println("getabsenceinforservice:已成功处理"+date+" "+wo.get(i).getAssigner());
        }
        System.out.println("getabsenceinforservice:出勤情况完毕");
        //计算出勤率
        List<List<String>>result=new ArrayList<>();
        List<String>ratelist=new ArrayList<>();
        for(int i=0;i<assi.size();i++){
            Double re=0.0;
            for(int j=0;j<datelist.size();j++){
                //获取每日该指派者的到寝情况
                re+=Double.parseDouble(absencemap.get(datelist.get(j)).get(assi.get(i)));
            }
            re=re/datelist.size();
            ratelist.add(Double.toString(re));
        }
        System.out.println("getabsenceinforservice:已成功计算出出勤率");
        List<String> num=new ArrayList<>();
        num.add(Integer.toString(assi.size()));
        result.add(num);
        result.add(assi);
        result.add(ratelist);
        js.put("status","success");
        js.put("rasult",result);
        System.out.println(js);
        System.out.println("getabsenceinforservice:运行成功");
        return js;
    }
    //日期转化函数
    public  List<String> splitdate(String starttime,String endtime) throws ParseException {
        //获取日期信息
        Date startDate = getDatebyString(starttime);
        Date endDate = getDatebyString(endtime);
        List<String> list = new ArrayList<>();
        while(startDate.getTime()<=endDate.getTime()){
            list.add(getStringbyDate(startDate));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.DAY_OF_MONTH,1);
            if(calendar.getTime().getTime()>endDate.getTime()){
                if(!startDate.equals(endDate)){
                    list.add(getStringbyDate(endDate));
                }
                startDate = calendar.getTime();
            }else{
                startDate = calendar.getTime();
            }
        }
        System.out.println("getchartservice:日期转换完毕");
        return list;
    }
    //获取某时间段的工时信息
    public List<Worktime> getallworktime(String proid,String starttime,String endtime){
        return chart.getallworktime(proid,starttime,endtime);
    }
    //按mession任务顺序获取每日的工时信息
    public List<String> getperdayworktime(String proid,String date){
        List<String> melis=new ArrayList<String>();
        List<String> li=chart.getallmessionname(proid);
        System.out.println("getperdayworktime:已成功获取项目任务");
        if(li.size()==0){
            System.out.println("getperdayworktime:当前项目任务为空");
            return melis;
        }
        List<String> lis=new ArrayList<String>();
        //筛除重复任务名
        Map<String,String>ke=new HashMap<>();
        for(int i=0;i<li.size();i++){
            if(ke.containsKey(li.get(i))){
                continue;
            }
            ke.put(li.get(i),"1");
            lis.add(li.get(i));
        }
        System.out.println("getperdayworktime:已筛除重复任务名");
        DecimalFormat df = new DecimalFormat("#.0");
        double ans=0.0;
        System.out.println(lis.size());
        for(int j=0;j<lis.size();j++){
            List<Worktime> wo=chart.getchartworktime(proid,lis.get(j),date);
            double tot=0.0;
            System.out.println("in the worktime count");
            for(int k=0;k<wo.size();k++){
                tot+= Double.parseDouble(wo.get(k).getWorktime());
                System.out.println(tot);
            }
            System.out.println("getperdayworktime:每日各任务填报信息获取完毕");
            ans+=tot;
            melis.add(df.format(tot));
            System.out.println("已获取任务"+lis.get(j)+"工时信息");
        }
        ans=ans/lis.size();
        melis.add(df.format(ans));
        System.out.println("getperdayworktime:已成功运行");
        return melis;
    }
    //String转为指定日期格式
    public Date getDatebyString(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date da=simpleDateFormat.parse(date);
        return da;
    }
    //Date转为String格式
    public String getStringbyDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String da=simpleDateFormat.format(date);
        return da;
    }
    //根据proid获取messionname
    public List<String> getmessionname(String proid){
        return chart.getmessionname(proid);
    }

    //查询特定日期下指定任务的人员工时信息
    public List<Worktime> getchartworktime(String proid,String messoinname,String date){
        return chart.getchartworktime(proid,messoinname,date);
    }

    //查询任务所有人员
    public List<String> getallassigner(String proid,String messionname){
        return chart.getallassigner(proid,messionname);
    }
}
