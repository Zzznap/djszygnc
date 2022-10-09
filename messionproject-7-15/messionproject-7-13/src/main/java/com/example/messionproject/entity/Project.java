package com.example.messionproject.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Project {
    String proid,proname;
    public JSONObject toJson(){
        JSONObject js=new JSONObject();
        js.put("proid",proid);
        return js;
    }
}
