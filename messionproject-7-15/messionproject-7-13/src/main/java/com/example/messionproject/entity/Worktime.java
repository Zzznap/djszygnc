package com.example.messionproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Worktime {
    String messionname,assigner,date,proid,worktime,remark;

    @Override
    public String toString() {
        return "Worktime{" +
                "messionname='" + messionname + '\'' +
                ", assigner='" + assigner + '\'' +
                ", date='" + date + '\'' +
                ", proid='" + proid + '\'' +
                ", worktime='" + worktime + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
    public void putworktimeinformation(String proid,String messionname,String assigner){
        this.proid=proid;
        this.messionname=messionname;
        this.assigner=assigner;
    }
}
