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

    @Select("select * from students s " +
            "LEFT JOIN dormitory d on s.dormitory_id = d.id" +
            " LEFT JOIN college_classes cc on s.college_class_id = cc.class_id"+
            " where s.turename like concat('%',#{query},'%')"+
            " order by s.college_id,s.college_class_id,s.dormitory_id")
    List<Student> findAllByQuery(String query);

    @Update("update students set turename = #{name},username = #{username},sex = #{sex},avatar = #{avatar},college_id = #{college_id},phone = #{phone} where account_id = #{id}")
    int updateStudentByAccountId(@Param("id") String id,
                      @Param("name")String name,
                      @Param("username")String username,
                      @Param("sex")String sex,
                      @Param("avatar")String avatar,
                      @Param("college_id")Integer college_id,
                      @Param("phone")String phone);

    @Select("select * from students s " +
            "LEFT JOIN dormitory d on s.dormitory_id = d.id" +
            " LEFT JOIN college_classes cc on s.college_class_id = cc.class_id" +
            " where s.account_id = #{id}")
    Student findByAccountId(String id);

    @Delete("delete from students where account_id = #{id}")
    int deleteByAccountId(String id);

    @Select("select * from students where college_class_id = #{class_id}")
    List<Student> getClassStudentList(Integer class_id);

//    @Select("select * from students where college_class_id = #{class_id} and where account_id not in #{ids}")
//    List<Student> getClassStudentListNotIn(@Param("class_id") Integer class_id,@Param("ids") List<Integer> ids);
//
    @Select("<script>" +
            "select * from students where college_class_id = #{class_id} and account_id not in" +
            "<foreach collection='ids' open='(' separator=',' close=')' item='id'>" +
            "#{id}"+
            "</foreach>"+
            "</script>")
    List<Student> getClassStudentListNotIn(@Param("class_id") Integer class_id,@Param("ids") List<Integer> ids);

    @Select("select * from students where dormitory_id = #{dormitory_id}")
    List<Student> getDormitoryStudentList(Integer dormitory_id);

    @Update("update students set dormitory_id = #{dormitory_id} where account_id = #{account_id}")
    void setDormitoryId(@Param("account_id") Integer account_id, @Param("dormitory_id") Integer i);

    @Select("select * from students where college_id = #{college_id} and college_class_id is null")
    List<Student> getCollegeStudentList(Integer college_id);

    @Select("<script>" +
            "select * from students where college_id = #{college_id} and college_class_id is null and account_id not in" +
            "<foreach collection='ids' open='(' separator=',' close=')' item='id'>" +
            "#{id}"+
            "</foreach>"+
            "</script>")
    List<Student> getCollegeStudentListNotIn(@Param("college_id") Integer college_id, @Param("ids") List<Integer> ids);

    @Update("update students set college_class_id = #{class_id} where account_id = #{account_id}")
    void setClassId(@Param("account_id") Integer account_id, @Param("class_id") Integer i);
}
