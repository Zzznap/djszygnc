package com.example.messionproject.mapper;

import com.example.messionproject.entity.Mession;
import com.example.messionproject.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select(" select * from usertable where userid=#{userid}")
    List<User> getuserbyid(String userid);
    @Insert("insert into usertable(userid,password,username) values (#{userid},#{password},#{username})")
    boolean insertuser(User user);
    @Insert("insert into limitgradetable(userid,proid,limitgrade) values(#{userid},'未分配','0')")
    boolean insertlimit(String userid);
}
