package com.example.messionproject.mapper;

import com.example.messionproject.entity.User;
import com.example.messionproject.entity.Worktime;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface chartMapper {
    //根据proid获取messionname
    @Select("select messionname from worktimetable where proid=#{param1}")
    List<String> getmessionname(String proid);
    //根据proid获取allmessionname
    @Select("select messionname from mession where proid=#{param1}")
    List<String> getallmessionname(String proid);
    //查询指定时间下的用户工时信息
    @Select("select * from worktimetable where proid=#{param1} and messionname=#{param2} and date=#{param3}")
    List<Worktime> getchartworktime(String proid, String messoinname, String date);
    //查询任务中的所有人员
    @Select("select assigner from mession where proid=#{param1} and messionname=#{param2}")
    List<String> getallassigner(String proid ,String messionname);
    //获取某时间段的所有工时信息
    @Select("select * from worktimetable where proid=#{param1} and (date between #{param2} and #{param3})")
    List<Worktime> getallworktime(String proid,String starttime,String endtime);
    @Select("select messionname from mession where proid=#{proid}")
    List<String> getmessionnamefrommession(String proid);
}
