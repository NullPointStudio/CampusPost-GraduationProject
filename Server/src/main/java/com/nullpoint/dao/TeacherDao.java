package com.nullpoint.dao;

import com.nullpoint.domain.Teacher;
import org.apache.ibatis.annotations.*;

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

    @Select("select * from teachers where turename like  concat('%',#{query},'%')")
    List<Teacher> findAllByQuery(String query);

    @Update("update teachers set turename = #{name},username = #{username},sex = #{sex},avatar = #{avatar},college_id = #{college_id},phone = #{phone} where account_id = #{id}")
    int updateTeacherByAccountId(@Param("id") String id,
                      @Param("name")String name,
                      @Param("username")String username,
                      @Param("sex")String sex,
                      @Param("avatar")String avatar,
                      @Param("college_id")Integer college_id,
                      @Param("phone")String phone);

    @Select("select * from teachers where account_id = #{id}")
    Teacher findByAccountId(String id);

    @Delete("delete from teachers where account_id = #{id}")
    int deleteByAccountId(String id);

    @Select("select * from teachers where college_id = #{college_id}")
    List<Teacher> getTeacherByCollege(Integer college_id);
}
