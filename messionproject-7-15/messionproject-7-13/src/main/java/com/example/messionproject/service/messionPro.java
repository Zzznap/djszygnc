package com.example.messionproject.service;
import com.alibaba.fastjson.JSONArray;
import com.example.messionproject.entity.Worktime;
import com.example.messionproject.mapper.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.messionproject.entity.Mession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class messionPro {
    @Autowired
    messionMapper messionmapper;
    //updatetime，proid，messionid，userid，time，text
    public JSONObject updatetime(String message){
        JSONObject jsob=JSON.parseObject(message);
        String messionname=jsob.getString("messionname");
        String assigner=jsob.getString("assigner");
        String date=jsob.getString("date");
        String proid=jsob.getString("proid");
        String worktime=jsob.getString("worktime");
        String remark=jsob.getString("remark");
        jsob=new JSONObject();
        Worktime wtobj=new Worktime(messionname,assigner,date,proid,worktime,remark);
        messionmapper.deleteworktime(proid,messionname,assigner,date);
        messionmapper.addtime(wtobj);
        jsob.put("status","success");
        System.out.println("添加工时："+wtobj.toString());
        return jsob;
    }

    //updateteg，proid，messionid，teg
    public JSONObject updateteg(String message){
        JSONObject jsob=JSON.parseObject(message);
        String messionid=jsob.getString("messionid");
        String changedteg=jsob.getString("teg");
        jsob=new JSONObject();
        Mession m= messionmapper.getmessionbyid(messionid).get(0);
        String starttime=m.getStarttime();
        String endtime=m.getEndtime();
        if(changedteg.equals("1")){
            String firsttime=starttime.split("%split%")[0];
            SimpleDateFormat form=new SimpleDateFormat("yyyy-MM-dd");
            String secondtime=form.format(new Date());
            starttime=firsttime+"%split%"+secondtime;
        }
        else if(changedteg.equals("3")){
            String firsttime=endtime.split("%split%")[0];
            SimpleDateFormat form=new SimpleDateFormat("yyyy-MM-dd");
            String secondtime=form.format(new Date());
            endtime=firsttime+"%split%"+secondtime;
        }
        messionmapper.changeteg(messionid,changedteg,starttime,endtime);

        System.out.println("将"+messionid+"任务修改为"+changedteg+"状态");
        jsob.put("status","success");
        return jsob;
    }



    public JSONObject addmession(String message){
        JSONObject jsob=new JSONObject();
        try {
            String dec=URLDecoder.decode(message,"UTF-8");
            System.out.println(dec);
            jsob= JSON.parseObject(dec);
        }
        catch (Exception e ){e.printStackTrace();}
        String messionname=jsob.getString("messionname");
        String proid=jsob.getString("proid");
        String creater=jsob.getString("creater");
        String starttime=jsob.getString("starttime");
        String endtime=jsob.getString("endtime");
        String costtime=jsob.getString("costtime");
        String teg=jsob.getString("teg");
        String moudle=jsob.getString("moudle");
        String need=jsob.getString("need");
        String importance=jsob.getString("importance");
        String milepost=jsob.getString("milepost");
        String description=jsob.getString("description");
        String commend=jsob.getString("commend");
        JSONArray jsar=jsob.getJSONArray("assigner");
        boolean b;
        List<Mession> maxlist=messionmapper.GetMaxID();
        List<Mession> existlist=messionmapper.getmessionbyname(messionname,proid);
        String maxid="0";
        if (maxlist.size()!=0)
            maxid=maxlist.get(0).getid();
        else
            maxid="0";
        maxid=String.valueOf(Integer.valueOf(maxid)+1);
        jsob=new JSONObject();
        if(existlist.size()!=0) {
            jsob.put("status", "false");
            System.out.println("插入任务失败：该项目已经存在该任务");
        }
        else {
            if(jsar==null||jsar.size()==0){
                jsar=new JSONArray();
                jsar.add("");
            }
            for(int i=0;i<jsar.size();i++) {
                Mession mession = new Mession(maxid, messionname, proid, creater, jsar.getString(i), starttime, endtime, costtime,
                        teg, moudle, need, importance, milepost, description, commend,0,0);
                System.out.println("要插入的任务" + mession.toString());
                messionmapper.insertmession(mession);
                jsob.put("status", "success");
                jsob.put("name",messionname);
                maxid=String.valueOf(Integer.valueOf(maxid)+1) ;
            }
        }
        return jsob;
    }

    //addmesssion ，proid，userid，infor任务相关信息
    public JSONObject updatemession(String message){
        JSONObject jsob=new JSONObject();
        try {
            String dec=URLDecoder.decode(message,"UTF-8");
            System.out.println(dec);
            jsob= JSON.parseObject(dec);
        }
        catch (Exception e ){e.printStackTrace();}
        String oldmessionname=jsob.getString("oldmessionname");
        String creater=jsob.getString("creater");
        String messionname=jsob.getString("messionname");
        String proid=jsob.getString("proid");
        String starttime=jsob.getString("starttime");
        String endtime=jsob.getString("endtime");
        String costtime=jsob.getString("costtime");
        String teg=jsob.getString("teg");
        String moudle=jsob.getString("moudle");
        String need=jsob.getString("need");
        String importance=jsob.getString("importance");
        String milepost=jsob.getString("milepost");
        String description=jsob.getString("description");
        String commend=jsob.getString("commend");
        JSONArray jsar=jsob.getJSONArray("assigner");
        List<Mession> existmission=messionmapper.getmessionbyname(oldmessionname,proid);
        ArrayList<String> existassigner=new ArrayList<String>();
        ArrayList<String> newassigner=new ArrayList<String>();
        boolean b=true;

        List<Mession> existname=messionmapper.getmessionbyname(messionname,proid);
        jsob=new JSONObject();

        if (existname.size()==0||oldmessionname.equals(messionname)) {
            for(int i=0;i<existmission.size();i++){
                existassigner.add(existmission.get(i).getAssigner());
            }
            if(existassigner.size()==0) {
                jsob.put("status","未找到该任务");
                return jsob;
            }
            if(jsar==null||jsar.size()==0){
                b=false;
            }
            else {
            for (int i = 0; i < jsar.size(); i++) {
                newassigner.add(jsar.getString(i));
                }
            }

            ArrayList<String> existassigner0=new ArrayList<>(existassigner);
            existassigner0.removeAll(newassigner);
            messionmapper.deletebyassigner(oldmessionname,"",proid);
            for (int i=0;i<existassigner0.size();i++){
                messionmapper.deletebyassigner(oldmessionname,existassigner0.get(i),proid);
                System.out.println("删除assigner及其工时"+existassigner0.get(i));
            }
            newassigner.removeAll(existassigner);
            String maxid="0";List<Mession>maxlist=messionmapper.GetMaxID();
            if (maxlist.size()!=0)
                maxid=maxlist.get(0).getid();
            maxid=String.valueOf(Integer.valueOf(maxid)+1);
            if (!b){
                newassigner.add("");
            }
            for (int i=0;i<newassigner.size();i++){
                Mession mession = new Mession(maxid, oldmessionname, proid, creater, newassigner.get(i), starttime, endtime, costtime,
                        teg, moudle, need, importance, milepost, description, commend,0,0);
                System.out.println("要插入的任务" + mession.toString());
                messionmapper.insertmession(mession);
                maxid=String.valueOf(Integer.valueOf(maxid)+1);
            }
            Mession mession = new Mession("该任务的所有id", oldmessionname, proid, creater, "所有被指派人", starttime, endtime, costtime,
                    teg, moudle, need, importance, milepost, description, commend,0,0);
            System.out.println("修改任务："+mession.toString());
            messionmapper.updatemession(mession);
            System.out.println("将"+oldmessionname+"改为"+messionname);
            messionmapper.changemessionname(oldmessionname, proid, messionname);
            jsob = new JSONObject();
            jsob.put("status", "success");
        }
        else{
            System.out.println("已有重复的任务名");
            jsob.put("status","已有重复的任务名");
        }
        return jsob;
    }
    public JSONObject commend(String message){
        JSONObject jsob=JSON.parseObject(message);
        String commend=jsob.getString("commend0")+"%split%"+jsob.getString("commend1");
        messionmapper.commend(commend,
                jsob.getString("costtime"),
                jsob.getString("messionid"));
        jsob=new JSONObject();
        jsob.put("status","success");
        return jsob;
    }
    public JSONObject close(String message){
        JSONObject jsob=JSON.parseObject(message);
        messionmapper.colsemession(jsob.getString("messionname"),jsob.getString("proid"));
        jsob=new JSONObject();
        jsob.put("status","success");
        return jsob;
    }
}
