package com.example.messionproject.mapper;
import com.alibaba.fastjson.JSONObject;
import com.example.messionproject.entity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface messionMapper {
    @Select("select *  from mession where messionname=#{param1} and proid=#{param2}")
    List<Mession> getmessionbyname(String messionname,String proid);
    @Select("select *  from mession where messionid=#{messionid}")
    List<Mession> getmessionbyid(String messionid);
    @Select("select top 1 messionid from mession order by messionid desc")
    List<Mession> GetMaxID();
    @Insert("insert into mession(messionid,messionname,proid,creater,assigner,starttime,endtime,costtime,teg,moudle,need,importance,milepost,description,commend)\n" +
            "         values (#{messionid},#{messionname},#{proid},#{creater},#{assigner},#{starttime},\n" +
            "                 #{endtime},#{costtime},#{teg},#{moudle},#{need},#{importance},#{milepost},#{description}\n" +
            "                    ,#{commend})")
    boolean insertmession(Mession mession);
    @Update("update mession set teg=#{param2},starttime=#{param3},endtime=#{param4} where messionid=#{param1}")
    boolean changeteg(String messionid,String changedteg,String newtime1,String newtime2);
    @Update("update mession set " +
            "starttime=#{starttime},endtime=#{endtime},costtime=#{costtime},creater=#{creater}" +
            ",teg=#{teg},moudle=#{moudle},need=#{need},importance=#{importance},milepost=#{milepost}," +
            "description=#{description},commend=#{commend} where messionname=#{messionname} and " +
            "proid=#{proid}" )
    boolean updatemession(Mession mession);
    @Delete("delete from mession where messionname=#{param1} and assigner=#{param2} and proid=#{param3}")
    boolean deletebyassigner(String param1,String param2,String param3);
    @Update("update mession set messionname=#{param3} where messionname=#{param1} and proid=#{param2}")
    boolean changemessionname(String oldname,String proid,String name);
    @Update("update mession set commend=#{param1},costtime=#{param2} where messionid=#{param3}")
    boolean commend(String commend,String costtime,String messionid);
    @Delete("delete from worktimetable where proid=#{param1} and messionname=#{param2} and " +
            "assigner=#{param3} and date=#{param4}")
    boolean deleteworktime(String proid,String messionname,String assigner,String date);
    @Update("update mession set teg=1 where messionname=#{param1} and proid=#{param2}")
    boolean colsemession(String messionname,String proid);
    @Insert("insert into worktimetable(messionname,assigner,date,proid,worktime,remark) values" +
            "(#{messionname},#{assigner},#{date},#{proid},#{worktime},#{remark})")
    boolean addtime(Worktime worktime);
}
