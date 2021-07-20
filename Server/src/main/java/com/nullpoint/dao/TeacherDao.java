package com.nullpoint.dao;

import com.nullpoint.domain.Teacher;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeacherDao {

    @Select("select * from teachers")
    List<Teacher> findAll();

    @Insert("insert into teachers(account_id,turename,username,sex,avatar,college_id,phone) values (#{account_id},#{turename},#{username},#{sex},#{avatar},#{college_id},#{phone})")
    int addTeacher(@Param("account_id") Integer account_id,
                   @Param("turename") String name,
                   @Param("username") String name1,
                   @Param("sex") String sex,
                   @Param("avatar") String s,
                   @Param("college_id") int select_college,
                   @Param("phone") String phone);
}
