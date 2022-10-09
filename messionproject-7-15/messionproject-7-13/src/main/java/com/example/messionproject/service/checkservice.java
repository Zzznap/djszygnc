package com.example.messionproject.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.messionproject.entity.User;
import com.example.messionproject.mapper.checkMapper;
import com.alibaba.fastjson.JSONObject;
import com.example.messionproject.entity.Mession;
import com.example.messionproject.entity.Project;
import com.example.messionproject.entity.Worktime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class checkservice {

    @Autowired
    checkMapper check;
    //getproject，userid
    public JSONObject getprojectservice(String message){
        JSONObject js=new JSONObject();
        List<JSONObject> lis=new ArrayList<JSONObject>();
        if(message.equals("")||message==null){
            System.out.println("getprojectservice:Inputnull");
            js.put("status","Inputnull");
            return js;
        }
        System.out.println("getprojectservice:messsage:"+message);
        JSONObject object =JSONObject.parseObject(message);
        System.out.println("getprojectservice:object已构建");
        if(object.get("userid")==null){
            System.out.println("getprojectservice:useridinputnull");
            js.put("status","useridinputnull");
            return js;
        }
        List<Project> projectList= check.getproject((String)object.get("userid"));
        if(projectList.size()==0){
            js.put("status","Resultnull");
            js.put("result",lis);
            System.out.println("getprojectservice:check返回值为空");
            return js;
        }

        for(int i=0;i<projectList.size();i++){
            JSONObject ob=projectList.get(i).toJson();
            System.out.println("getprojectservice:成功获取proid");
            ob.put("limitgrade",getlimitgrade(projectList.get(i).getProid(),object.getString("userid")));
            System.out.println("getlimitgrade:成功获取权限等级");
            lis.add(ob);
        }
        js.put("status","success");
        js.put("result",lis);
        System.out.println("getprojectservice:运行成功");
        return js;
    }
    //获取任务页面数量
    //Getpage proid，userid，teg, need , moudle
    //查询用户为创建者mession
    public JSONObject getcreaterpageservice(String message){
        JSONObject js=new JSONObject();
        if(message.equals("")||message==null){
            System.out.println("getcreaterpageservice:Inputnull");
            js.put("status","Inputnull");
            return js;
        }
        System.out.println("getcreaterpageservice:message:"+message);
        JSONObject object =JSONObject.parseObject(message);
        if((String)object.get("proid")==null||(String)object.get("userid")==null||(String)object.get("teg")==null||(String)object.get("need")==null||(String)object.get("moudle")==null){
            System.out.println("getcreaterpageservice:Inputvalueexistnull");
            js.put("status","Inputvalueexistnull");
            return js;
        }
        Mession me=new Mession((String)object.get("proid"),(String)object.get("userid"),(String)object.get("teg"),(String)object.get("need"),(String)object.get("moudle"));
        List<Mession> li=check.getcreaterMessionpage(me);
        if(li.size()==0){
            js.put("status","Resultnull");
            js.put("number","0");
            System.out.println("getcreaterpageservice:check返回为空");
            return js;
        }
        js.put("status","success");
        js.put("number",li.size());
        System.out.println("getcreaterpageservice:运行成功");
        return js;
    }
    //其它情况的messionpage
    public JSONObject getassignerpageservice(String message){
        JSONObject js=new JSONObject();
        if(message.equals("")||message==null){
            System.out.println("getassignerpageservice:Inputnull");
            js.put("status","Inputnull");
            return js;
        }
        System.out.println("getassignerpageservice:message:"+message);
        JSONObject object=JSONObject.parseObject(message);
        if((String)object.get("proid")==null||(String)object.get("userid")==null||(String)object.get("teg")==null||(String)object.get("need")==null||(String)object.get("moudle")==null){
            System.out.println("getassignerpageservice:Inputvalueexistnull");
            js.put("status","Inputvalueexistnull");
            return js;
        }
        Mession me=new Mession((String)object.get("proid"),(String)object.get("userid"),(String)object.get("teg"),(String)object.get("need"),(String)object.get("moudle"));
        List<Mession> li=check.getassignerMessionpage(me);
        if(li.size()==0){
            js.put("status","Resultnull");
            js.put("number","0");
            System.out.println("getassignerpageservice:check返回为空");
            return js;
        }
        js.put("status","success");
        js.put("number",li.size());
        System.out.println("getassignerpageservice:运行成功");
        return js;
    }
    //getmession，proid，userid，teg，need, moudle，loc
    //查询全部的mession
    public  JSONObject getallmessionservice(String message){
        JSONObject js=new JSONObject();
        if(message.equals("")||message==null){
            System.out.println("getallmessionservice:Inputnull");
            js.put("status","Inputnull");
            return js;
        }
        JSONObject object =JSONObject.parseObject(message);
        System.out.println("getallmessionservice:message:"+message);
        if((String)object.get("proid")==null||(String)object.get("userid")==null||(String)object.get("teg")==null||(String)object.get("need")==null||(String)object.get("moudle")==null||object.getString("loc")==null){
            System.out.println("getallmessionservice:Inputvalueexistnull");
            js.put("status","Inputvalueexistnull");
            return js;
        }
        Mession me=new Mession((String)object.get("proid"),(String)object.get("userid"),(String)object.get("teg"),(String)object.get("need"),(String)object.get("moudle"));
        me.putloc((String) object.get("loc"));
        System.out.println("getallmessionservice:loc初始化成功");
        List<Mession> li=check.getallMession(me);
        if(li.size()==0){
            js.put("status","Resultnull");
            js.put("result",new JSONArray());
            System.out.println("getcreaterMession:check返回值为空");
            return js;
        }
        List<JSONObject> lis=new ArrayList<JSONObject>();
        for(int i=0;i<li.size();i++){
            JSONObject ob=li.get(i).toJson();
            List<String> list=getmessionservice(li.get(i).getProid(),li.get(i).getMessionname());
            System.out.println("getallmessionservice:"+li.get(i).getMessionname()+"已成功获取assignerid");
            List<String> messionteg=new ArrayList<String>();
            for(int j=0;j<list.size();j++){
                messionteg.add(getmessionteg(li.get(i).getProid(),li.get(i).getMessionname(),list.get(j)));
            }
            System.out.println("getallmessionservice:"+li.get(i).getMessionname()+"已成功获取assignername与teg信息");
            String k="1";
            for(int j=0;j<messionteg.size();j++){
                if(!messionteg.get(j).equals("2")){
                    k="0";
                }
            }
            String[] splitstarttime=ob.getString("starttime").split("%split%");
            String[] splitendtime=ob.getString("endtime").split("%split%");
            ob.put("starttime",splitstarttime[0]);
            ob.put("endtime",splitendtime[0]);
            JSONObject jsob=new JSONObject();
            jsob.put("assignerid",li.get(i).getAssigner());
            jsob.put("assignername",getusername(li.get(i).getAssigner()));
            ob.put("assigner",jsob);
            ob.put("messionteg",k);
            jsob=new JSONObject();
            jsob.put("createrid",ob.getString("creater"));
            jsob.put("creatername",getusername(ob.getString("creater")));
            ob.put("creater",jsob);
            lis.add(ob);
        }
        js.put("status","success");
        js.put("result",lis);
        System.out.println("getallmessionservice:运行成功");
        return js;
    }
    //查询用户为创建者的mession
    public JSONObject getcreatermessionservice(String message){
        JSONObject js=new JSONObject();
        if(message.equals("")||message==null){
            System.out.println("getcreatermessionservice:Inputnull");
            js.put("status","Inputnull");
            return js;
        }
        JSONObject object =JSONObject.parseObject(message);
        System.out.println("getcreatermessionservice:message:"+message);
        if((String)object.get("proid")==null||(String)object.get("userid")==null||(String)object.get("teg")==null||(String)object.get("need")==null||(String)object.get("moudle")==null||object.getString("loc")==null){
            System.out.println("getcreatermessionservice:Inputvalueexistnull");
            js.put("status","Inputvalueexistnull");
            return js;
        }
        Mession me=new Mession((String)object.get("proid"),(String)object.get("userid"),(String)object.get("teg"),(String)object.get("need"),(String)object.get("moudle"));
        me.putloc((String) object.get("loc"));
        System.out.println("getcreatermessionservice:loc初始化成功");
        List<Mession> li=check.getcreaterMession(me);
        if(li.size()==0){
            js.put("status","Resultnull");
            js.put("result",new JSONArray());
            System.out.println("getcreaterMession:check返回值为空");
            return js;
        }
        List<JSONObject> lis=new ArrayList<JSONObject>();
        for(int i=0;i<li.size();i++){
            JSONObject ob=li.get(i).toJson();
            List<String> list=getmessionservice(li.get(i).getProid(),li.get(i).getMessionname());
            System.out.println("getcreatermessionservice:"+li.get(i).getMessionname()+"已成功获取assignerid");
            List<String> messionteg=new ArrayList<String>();
            for(int j=0;j<list.size();j++){
                messionteg.add(getmessionteg(li.get(i).getProid(),li.get(i).getMessionname(),list.get(j)));
            }
            System.out.println("getcreatermessionservice:"+li.get(i).getMessionname()+"已成功获取assignername与teg信息");
            String k="1";
            for(int j=0;j<messionteg.size();j++){
                if(!messionteg.get(j).equals("2")){
                    k="0";
                }
            }
            String[] splitstarttime=ob.getString("starttime").split("%split%");
            String[] splitendtime=ob.getString("endtime").split("%split%");
            ob.put("starttime",splitstarttime[0]);
            ob.put("endtime",splitendtime[0]);
            JSONObject jsob=new JSONObject();
            jsob.put("assignerid",li.get(i).getAssigner());
            jsob.put("assignername",getusername(li.get(i).getAssigner()));
            ob.put("assigner",jsob);
            ob.put("messionteg",k);
            jsob=new JSONObject();
            jsob.put("createrid",ob.getString("creater"));
            jsob.put("creatername",getusername(ob.getString("creater")));
            ob.put("creater",jsob);
            lis.add(ob);
        }
        js.put("status","success");
        js.put("result",lis);
        System.out.println("getcreatermessionservice:运行成功");
        return js;
    }
    //查询用户为被指派者的mession
    public JSONObject getassignermessionservice(String message){
        JSONObject js=new JSONObject();
        if(message.equals("")||message==null){
            System.out.println("getassignermessionservice:Inputnull");
            js.put("status","Inputnull");
            return js;
        }
        JSONObject object =JSONObject.parseObject(message);
        System.out.println("getassignermessionservice:message:"+message);
        if((String)object.get("proid")==null||(String)object.get("userid")==null||(String)object.get("teg")==null||(String)object.get("need")==null||(String)object.get("moudle")==null||object.getString("loc")==null){
            System.out.println("getcreatermessionservice:Inputvalueexistnull");
            js.put("status","Inputvalueexistnull");
            return js;
        }
        Mession me=new Mession((String)object.get("proid"),(String)object.get("userid"),(String)object.get("teg"),(String)object.get("need"),(String)object.get("moudle"));
        me.putloc((String) object.get("loc"));
        System.out.println("getassignermessionservice:loc初始化成功");
        List<Mession> li=check.getassignerMession(me);
        if(li.size()==0){
            js.put("status","Resultnull");
            js.put("result",new JSONArray());
            System.out.println("getassignermessionservice:check返回值为空");
            return js;
        }
        List<JSONObject> lis=new ArrayList<JSONObject>();
        for(int i=0;i<li.size();i++){
            JSONObject ob=li.get(i).toJson();
            List<String> list=getmessionservice(li.get(i).getProid(),li.get(i).getMessionname());
            System.out.println("getassignermessionservice:"+li.get(i).getMessionname()+"已成功获取assignerid");
            List<String> messionteg=new ArrayList<String>();
            for(int j=0;j<list.size();j++){
                messionteg.add(getmessionteg(li.get(i).getProid(),li.get(i).getMessionname(),list.get(j)));
            }
            System.out.println("getcreatermessionservice:"+li.get(i).getMessionname()+"已成功获取assignername与teg信息");
            String k="1";
            for(int j=0;j<messionteg.size();j++){
                if(!messionteg.get(j).equals("2")){
                    k="0";
                }
            }
            String[] splitstarttime=ob.getString("starttime").split("%split%");
            String[] splitendtime=ob.getString("endtime").split("%split%");
            ob.put("starttime",splitstarttime[0]);
            ob.put("endtime",splitendtime[0]);
            JSONObject jsob=new JSONObject();
            jsob.put("assignerid",li.get(i).getAssigner());
            jsob.put("assignername",getusername(li.get(i).getAssigner()));

            ob.put("assigner",jsob);
            ob.put("messionteg",k);
            jsob=new JSONObject();
            jsob.put("createrid",ob.getString("creater"));
            jsob.put("creatername",getusername(ob.getString("creater")));
            ob.put("creater",jsob);
            System.out.println("getassignermessionservice:"+li.get(i).getMessionname()+"已成功获取assignername和creatername");
            lis.add(ob);
        }
        js.put("status","success");
        js.put("result",lis);
        System.out.println("getassignermessionservice:运行成功");
        return js;
    }
    //根据任务名查询任务
    public List<String> getmessionservice(String proid,String messionname){
        Mession me=new Mession(proid,messionname);
        List<Mession> li=check.getMession(me);
        List<String> lis=new ArrayList<String>();
        for(int i=0;i<li.size();i++){
            lis.add(li.get(i).getAssigner());
        }
        return lis;
    }
    //deletemession，proid,messionname
    public JSONObject deletemissonservice(String message) {
        JSONObject js=new JSONObject();
        if(message.equals("")||message==null){
            System.out.println("deletemissonservice:Inputnull");
            js.put("status","Inputnull");
            return js;
        }
        JSONObject object =JSONObject.parseObject(message);
        System.out.println("deletemissonservice:message:"+message);
        if((String) object.get("proid")==null||(String) object.get("messionname")==null){
            js.put("status","Inputvalueexistnull");
            System.out.println("deletemissionservice:Inputvalueexsitnull");
            return js;
        }
        Mession me=new Mession((String) object.get("proid"),(String) object.get("messionname"));
        List<Mession> li=check.getdeleteMession(me);
        if(li.size()==0){
            js.put("status","Resultnull");
            js.put("result",new JSONArray());
            System.out.println("deletemissionservice:check查询结果为空");
            return js;
        }
        check.deleteMession(me);
        js.put("status","success");
        System.out.println("deletemissionservice:运行成功");
        return js;
    }
    //查询工时信息
    public JSONObject getworktimeinformation(String message){
        JSONObject js=new JSONObject();
        JSONObject object =JSONObject.parseObject(message);
        System.out.println("getworktimeinformation:message:"+message);

            Mession me=check.getmessionbyid(object.getString("messionid"));
            JSONObject ob=new JSONObject();
            Worktime wo=new Worktime();
            wo.putworktimeinformation(me.getProid(),me.getMessionname(),me.getAssigner());
            Mession res=check.getworkmessioninformation(me);
            List<String> ti=check.getworktimeinformation(wo);
            int tot=0;
            for(int j=0;j<ti.size();j++){
                tot+=Integer.parseInt(ti.get(j));
            }
            String [] startlist=res.getStarttime().split("%split%");
            String [] endlist=res.getEndtime().split("%split%");
            String [] commendlist=res.getCommend().split("%split%");
            ob.put("starttime0",startlist[0]);
            ob.put("endtime0",endlist[0]);
            ob.put("starttime1",startlist[1]);
            ob.put("endtime1",endlist[1]);
            ob.put("messionid",res.getMessionid());
            ob.put("assigner",getusername(res.getAssigner()));
            ob.put("costtime0",res.getCosttime());
            ob.put("costtime1",Integer.toString(tot));
            ob.put("commend0",commendlist[0]);
            if(commendlist.length<2) {
                ob.put("commend1",commendlist[0]);
            }
            else {
                ob.put("commend1",commendlist[1]);
            }

        System.out.println("getworkinformation:运行成功");
        return ob;
    }

    //根据userid查询username
    public String getusername(String userid){
        List<String>li=check.getnamebyid(userid);
        return li.get(0);
    }
    //根据userid,proid,messionname查询teg
    public String getmessionteg(String proid,String messionname,String assigner){
        List<String>li=check.getmessionteg(proid,messionname,assigner);
        return li.get(0);
    }
    //获取用户权限等级
    public String getlimitgrade(String proid,String userid){
        return check.getlimitgrade(proid,userid).get(0);
    }
    public JSONObject getuserbyproid(String message){
        JSONObject jsob= JSON.parseObject(message);
        String proid=jsob.getString("proid");
        String userid=jsob.getString("userid");
        List<User> userList=check.getuserbyproid(proid);
        jsob=new JSONObject();
        List<JSONObject> lis=new ArrayList<JSONObject>();

        for (int i=0;i<userList.size();i++){
            JSONObject sjsob=new JSONObject();
            if(!userid.equals(userList.get(i).getUserid())) {
                sjsob.put("userid", userList.get(i).getUserid());
                sjsob.put("username", check.getnamebyid(userList.get(i).getUserid()).get(0));
                lis.add(sjsob);
            }
        }
        jsob.put("result",lis);
        jsob.put("status","success");
        return jsob;
    }
}
