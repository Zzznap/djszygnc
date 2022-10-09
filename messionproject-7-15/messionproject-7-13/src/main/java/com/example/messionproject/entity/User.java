package com.example.messionproject.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.security.PublicKey;

@Data
public class User {
    String userid;
    String password;
    String username;
    public String ToS(){
        return userid+"%split%"+password;
    }
    public User(String userid0, String password0,String username0) {
        this.userid = userid0;
        this.password = password0;
        username=username0;
    }

    public String getpsw(){
        return this.password;
    }

    public String getname(){
        return username;
    }
    public String getid(){
        return userid;
    }

    public JSONObject toJSON(){
        JSONObject jsob=new JSONObject();
        jsob.put("userid",userid);
        jsob.put("password",password);
        return jsob;
    }

    public User(String userid0){
        this.userid=userid0;
    }
    @Override
    public String toString() {
        return "User{" +
                "userid='" + userid + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

}
