package com.nullpoint.dao;

import com.nullpoint.domain.Student;
import com.nullpoint.domain.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentDao {

    @Select("select * from students")
    List<Student> findAll();

    @Insert("insert into students(account_id,turename,username,sex,avatar,college_id,phone) values (#{account_id},#{turename},#{username},#{sex},#{avatar},#{college_id},#{phone})")
    int addStudent(@Param("account_id") Integer account_id,
                   @Param("turename") String name,
                   @Param("username") String name1,
                   @Param("sex") String sex,
                   @Param("avatar") String s,
                   @Param("college_id") int select_college,
                   @Param("phone") String phone);

    @Select("select * from students where turename like  concat('%',#{query},'%')")
    List<Student> findAllByQuery(String query);

    @Update("update students set turename = #{name},username = #{username},sex = #{sex},avatar = #{avatar},college_id = #{college_id},phone = #{phone} where account_id = #{id}")
    int updateStudentByAccountId(@Param("id") String id,
                      @Param("name")String name,
                      @Param("username")String username,
                      @Param("sex")String sex,
                      @Param("avatar")String avatar,
                      @Param("college_id")Integer college_id,
                      @Param("phone")String phone);

    @Select("select * from students where account_id = #{id}")
    Student findByAccountId(String id);

    @Delete("delete from students where account_id = #{id}")
    int deleteByAccountId(String id);
}
