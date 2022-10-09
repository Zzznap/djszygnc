package com.example.messionproject.controll;

import com.alibaba.fastjson.JSONObject;
import com.example.messionproject.service.chartservice;
import com.example.messionproject.service.checkservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@CrossOrigin("*")
@RestController
@RequestMapping("/chart")
public class chartcontroller {
    //图表处理
    @Autowired
    private chartservice chartservice0;
    //获取时间段工时填报信息
    @RequestMapping(value = "/getworktimeinfor",method = RequestMethod.POST)
    public JSONObject getworktimeinfor(@RequestParam(name="getworktimeinfor")String message) throws ParseException {
        return chartservice0.getdateworktimeinforservice(message);
    }
    //获取时间段员工到勤情况
    @RequestMapping(value = "/getabsenceinfor",method = RequestMethod.POST)
    public JSONObject getabenseinfor(@RequestParam(name="getabsence")String message) throws ParseException {
        return chartservice0.getabsenceinforservice(message);
    }
}
