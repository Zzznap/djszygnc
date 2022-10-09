package com.example.messionproject.controll;

import com.alibaba.fastjson.JSONObject;
import com.example.messionproject.service.messionPro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/mession",method = RequestMethod.POST)
public class messioncontroller {
    @Autowired
    private messionPro messionpro;
    @RequestMapping("/addmession")           //前端POST的URL
    public JSONObject Addmession(@RequestParam(name="addmession")String message){
        System.out.println("Addmession"+message);
        return messionpro.addmession(message);
    }
    @RequestMapping("/updateteg")           //前端POST的URL
    public JSONObject Updateteg(@RequestParam(name="updateteg")String message){
        System.out.println("Updateteg"+message);
        return messionpro.updateteg(message);
    }
    //修改工时proid，messionid，userid，time，text
    @RequestMapping("/addtime")           //前端POST的URL
    public JSONObject Updatetime(@RequestParam(name="addtime")String message){
        System.out.println("Updatetime"+message);
        return messionpro.updatetime(message);
    }
    //修改任务信息：proid，messionid，infor任务详细信息
    @RequestMapping("/updatemession")           //前端POST的URL
    public JSONObject Updatemession(@RequestParam(name="updatemession")String message){
        System.out.println("Updatemession"+message);
        return messionpro.updatemession(message);
    }
    @RequestMapping("/commend")
    public JSONObject Commend(@RequestParam(name = "commendmsg")String message)
    {   System.out.println("Commend"+message);
        return  messionpro.commend(message);
    }
    @RequestMapping(value = "/closemession")
    public JSONObject closemession(@RequestParam(name = "closemession")String message){
        System.out.println("closemession"+message);
        return messionpro.close(message);
    }
}
