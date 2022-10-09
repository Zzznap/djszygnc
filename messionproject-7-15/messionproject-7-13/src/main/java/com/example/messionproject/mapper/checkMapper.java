package com.example.messionproject.mapper;

import com.example.messionproject.entity.Mession;
import com.example.messionproject.entity.Project;
import com.example.messionproject.entity.User;
import com.example.messionproject.entity.Worktime;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface checkMapper {

    //查询项目
    @Select("select proid from limitgradetable where userid=#{userid}")
    List<Project> getproject(String userid);
    //查询页面数量
    //查询我创建的，我取消的
    @Select("select * from mession where proid like #{proid} and creater like #{creater} and teg like #{teg} and need like #{need} and moudle like #{moudle}")
    List<Mession> getcreaterMessionpage(Mession mession);
    //查询其它情况
    @Select("select * from mession where proid like #{proid} and assigner like #{assigner} and teg like #{teg} and need like #{need} and moudle like #{moudle}")
    List<Mession> getassignerMessionpage(Mession mession);
    //查询任务
    //查询我创建的,我取消的
    @Select("select top ${loc} *\n" +
            "from mession where messionid not in (select top ${loc2} messionid from mession where proid like #{proid} and creater like #{creater} and teg like #{teg} and need like #{need} and moudle like #{moudle} order by messionid desc) and proid like #{proid} and creater like #{creater} and teg like #{teg} and need like #{need} and moudle like #{moudle} order by messionid desc ")
    List<Mession> getcreaterMession(Mession mession);
    //查询其它情况
    @Select("select top ${loc} *\n" +
            "from mession where messionid not in (select top ${loc2} messionid from mession where proid like #{proid} and assigner like #{assigner} and teg like #{teg} and need like #{need} and moudle like #{moudle} order by messionid desc) and proid like #{proid} and assigner like #{assigner} and teg like #{teg} and need like #{need} and moudle like #{moudle} order by messionid desc")
    List<Mession> getassignerMession(Mession mession);
    //查询所有任务
    @Select("select top ${loc} *\n" +
            "from mession where messionid not in (select top ${loc2} messionid from mession where (proid like #{proid} and assigner like #{assigner} and teg like #{teg} and need like #{need} and moudle like #{moudle}) or (proid like #{proid} and creater like #{creater} and teg like #{teg} and need like #{need} and moudle like #{moudle}) order by messionid desc) and ((proid like #{proid}"+
            " and assigner like #{assigner} and teg like #{teg} and need like #{need} and moudle like #{moudle}) or (proid like #{proid} and creater like #{creater} and teg like #{teg} and need like #{need} and moudle like #{moudle})) order by messionid desc")
    List<Mession> getallMession(Mession mession);
    //根据任务名查询任务
    @Select("select * from mession where proid=#{proid} and messionname=#{messionname}")
    List<Mession> getMession(Mession mession);

    //取消任务
    @Select("select * from mession where messionname=#{messionname} and proid=#{proid}")
    List<Mession> getdeleteMession(Mession mession);

    @Update("update mession set teg=4 where messionname=#{messionname} and proid=#{proid}")
    void deleteMession(Mession mession);

    //工时查询
    //查询开始时间，结束时间
    @Select("select * from mession where messionname=#{messionname} and proid=#{proid} and assigner=#{assigner}")
    Mession  getworkmessioninformation(Mession mession);
    //查询assigner工时
    @Select("select worktime from worktimetable where messionname=#{messionname} and proid=#{proid} and assigner=#{assigner}")
    List<String> getworktimeinformation(Worktime wo);
    //根据userid查询username
    @Select("select username from usertable where userid= #{userid}")
    List<String> getnamebyid(String userid);

    //查询用户teg信息
    @Select("select teg from mession where proid=#{param1} and messionname=#{param2} and assigner=#{param3}")
    List<String> getmessionteg(String proid,String messionname,String assigner);
    //查询用户权限信息
    @Select("select limitgrade from limitgradetable where proid=#{param1} and userid=#{param2}")
    List<String> getlimitgrade(String proid,String userid);
    //查询项目中的assigner
    @Select("select userid from limitgradetable where proid=#{proid}")
    List<User> getuserbyproid(String proid);

    @Select("select * from mession where messionid=#{messionid}")
    Mession getmessionbyid(String messionid);
}

