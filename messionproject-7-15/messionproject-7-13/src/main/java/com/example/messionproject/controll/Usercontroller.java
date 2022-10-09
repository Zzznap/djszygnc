package com.example.messionproject.controll;

import com.alibaba.fastjson.JSONObject;
import com.example.messionproject.service.userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class Usercontroller {
    @Autowired
    private userservice userservice0;
    //登录处理userid，password
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public JSONObject login(@RequestParam(name="login")String message){
        System.out.println("login"+message);
        return userservice0.loginservice(message) ;
    }
    //注册处理userid，password

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public JSONObject register(@RequestParam(name="getmission")String message){
        System.out.println("register"+message);
        return userservice0.registerservice(message);
    }
}
