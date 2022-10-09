package com.example.messionproject.controll;

import com.alibaba.fastjson.JSONObject;
import com.example.messionproject.service.checkservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/check")
public class checkcontroller {
    @Autowired
    private checkservice checkservice0;
    //获取项目userid
    @RequestMapping( value = "/getproject",method = RequestMethod.POST)
    public JSONObject getproject(@RequestParam(name="getproject")String message){
        return checkservice0.getprojectservice(message);
    }
    //查看任务页面数量
    @RequestMapping(value = "/getcreatermessionpage",method = RequestMethod.POST)
    public  JSONObject getcreatermissonpage(@RequestParam(name ="getpage") String message){
        return checkservice0.getcreaterpageservice(message);
    }
    @RequestMapping(value = "/getassignermessionpage",method = RequestMethod.POST)
    public  JSONObject getassignermissonpage(@RequestParam(name ="getpage") String message){
        return checkservice0.getassignerpageservice(message);
    }
    //查看任务proid，userid，teg，reqname, modelname
    @RequestMapping(value = "/getcreatermession",method = RequestMethod.POST)
    public JSONObject getcreatermission(@RequestParam(name="getmession") String message){
        System.out.println(message);
        return checkservice0.getcreatermessionservice(message);
    }
    @RequestMapping(value = "/getassignermession",method = RequestMethod.POST)
    public JSONObject getassignermission(@RequestParam(name="getmession") String message){
        return checkservice0.getassignermessionservice(message);
    }
    //查询全部任务
    @RequestMapping(value = "/getallmession",method = RequestMethod.POST)
    public JSONObject getallmission(@RequestParam(name="getmession") String message){
        return checkservice0.getallmessionservice(message);
    }
    //取消任务proid，userid，messsionid

    @RequestMapping(value = "/deletions",method = RequestMethod.POST)
    public JSONObject deletemession(@RequestParam(name="deletions")String message){
        return checkservice0.deletemissonservice(message);
    }

    //查询工时信息proid,messionid
    @RequestMapping(value = "/getworktimeinformation",method = RequestMethod.POST)
    public JSONObject getworktimeinformation(@RequestParam(name = "getworktimeinfor") String message){
        return checkservice0.getworktimeinformation(message);
    }
    //查看任务中的能被指派的人
    @RequestMapping(value ="/getuserbyproid",method = RequestMethod.POST)
    public JSONObject getassignname(@RequestParam(name="getuserbyproid")String message){
        System.out.println(message);
        return checkservice0.getuserbyproid(message);
    }
}

