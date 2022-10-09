package com.example.messionproject.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.messionproject.entity.User;
import com.example.messionproject.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.example.messionproject.util.token.*;

import java.util.List;
@Service
public class userservice {
    @Autowired
    UserMapper userMapper;//注入数据库接口
    //login userid，password
    public JSONObject loginservice(String message){
        JSONObject jsob=JSON.parseObject(message);
        String userid=jsob.getString("userid");
        String password=jsob.getString("password");
        System.out.println(userid);
        List<User> userList= userMapper.getuserbyid(userid);
        jsob=new JSONObject();
        String token=sign(userid);
        if(userid==null||password==null){
            jsob.put("status", "不能输入空值");
        }
        else if(userList.size()==0) {
            jsob.put("status", "未找到用户");
        }

        else if(userList.get(0).getpsw().equals(password) ) {
            jsob.put("status", "登录成功");
            jsob.put("token",token);
            jsob.put("username",userList.get(0).getname());
        }
        else
            jsob.put("status", "用户名与密码不匹配");
        System.out.println(jsob);
        return jsob;
    }

    //register,，userid，password
    public JSONObject registerservice(String message) {
        JSONObject jsob;
        jsob=JSON.parseObject(message);
        String password =jsob.getString("password");
        String username =jsob.getString("username");
        String userid=jsob.getString("userid");
        List<User> userList_exist = userMapper.getuserbyid(userid);
        jsob=new JSONObject();
        if(password==null||username==null||userid==null||password.equals("")||username.equals("")||userid.equals("")){
            jsob.put("status","不能输入空值");
        }
        else if(userList_exist.size()!=0){
            jsob.put("status","用户id已存在");
        }
            //dbMapper.insertuser(new User(userid,password,userid));//以实体类为参数插入
        else {
            userMapper.insertuser(new User(userid,password,username));
            userMapper.insertlimit(userid);
            jsob.put("status", "注册成功");
        }
        return jsob;
    }
}
