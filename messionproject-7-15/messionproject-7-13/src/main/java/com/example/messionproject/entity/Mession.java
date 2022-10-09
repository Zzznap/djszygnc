package com.example.messionproject.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mession {
    String messionid,messionname,proid,creater,assigner,starttime,endtime,costtime,teg,moudle,need,importance,milepost,description,commend;
    public int loc,loc2;

    @Override
    public String toString() {
        return "Mession{" +
                "messsionid='" + messionid + '\'' +
                ", messionname='" + messionname + '\'' +
                ", proid='" + proid + '\'' +
                ", creater='" + creater + '\'' +
                ", assigner='" + assigner + '\'' +
                ", starttime='" + starttime + '\'' +
                ", endtime='" + endtime + '\'' +
                ", costtime='" + costtime + '\'' +
                ", teg='" + teg + '\'' +
                ", moudle='" + moudle + '\'' +
                ", need='" + need + '\'' +
                ", importance='" + importance + '\'' +
                ", milepost='" + milepost + '\'' +
                ", description='" + description + '\'' +
                ", commend='" + commend + '\'' +
                '}';
    }
    public Mession(String messionid,String messionname,String proid,String creater,String assigner,String starttime,String endtime,String costtime,String teg,String moudle,String need,String importance,String milepost,String description,String commond){
        this.messionid=messionid;
        this.messionname=messionname;
        this.assigner=assigner;
        this.starttime=starttime;
        this.endtime=endtime;
        this.costtime=costtime;
        this.teg=teg;
        this.moudle=moudle;
        this.need=need;
        this.importance=importance;
        this.milepost=milepost;
        this.description=description;
        this.commend=commond;
        this.creater=creater;
        this.proid=proid;
    }
    public  Mession(String proid,String userid,String teg,String need,String moudle){
        this.proid=proid;
        this.creater=userid;
        this.assigner=userid;
        this.teg=teg;
        this.need=need;
        this.moudle=moudle;
    }

    public  Mession(String proid,String messionname)    {
        this.proid=proid;
        this.messionname=messionname;
    }

    public void putloc(String loc){
        this.loc=Integer.parseInt(loc)*10;
        this.loc2=this.loc-10;
    }

    public void putworktimeinfor(String proid,String messionname,String assigner){
        this.messionname=messionname;
        this.proid=proid;
        this.assigner=assigner;
    }

    public  Mession(String proid,String userid,String messionid){
        this.proid=proid;
        this.assigner=userid;
        this.messionid=messionid;
    }

    public JSONObject toJson(){
        JSONObject js=new JSONObject();
        js.put("messionid",this.messionid);
        js.put("messionname",this.messionname);
        js.put("assigner",this.assigner);
        js.put("creater",this.creater);
        js.put("proid",this.proid);
        js.put("starttime",this.starttime);
        js.put("endtime",this.endtime);
        js.put("costtime",this.costtime);
        js.put("teg",this.teg);
        js.put("moudle",this.moudle);
        js.put("need",this.need);
        js.put("importance",this.importance);
        js.put("milepost",this.milepost);
        js.put("description",this.description);
        js.put("commend",this.commend);
        return js;

    }

    public String getid(){
        return this.messionid;
    }
}
