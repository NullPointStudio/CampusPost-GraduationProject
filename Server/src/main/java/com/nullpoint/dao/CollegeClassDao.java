package com.nullpoint.dao;

import com.nullpoint.domain.CollegeClass;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CollegeClassDao {

    @Select("select cc.*,c.college_name,t.turename as headteacher_name,s.turename as monitor_name from college_classes cc\n" +
            "LEFT JOIN college c on cc.college_id = c.college_id\n" +
            "LEFT JOIN teachers t on cc.headteacher_id = t.account_id\n" +
            "LEFT JOIN students s on cc.monitor_id = s.account_id\n" +
            "where cc.class_name like concat('%',#{query},'%')\n" +
            "ORDER BY c.college_id,cc.class_name")
    List<CollegeClass> findAllByQuery(String query);

    @Update("UPDATE college_classes set headteacher_id = #{headTeacherId}, monitor_id = #{monitorId} where class_id = #{classId}")
    int editClassTeacherAndMonitor(@Param("headTeacherId") Integer headTeacherId,
                                   @Param("monitorId") Integer monitorId,
                                   @Param("classId") Integer class_id);

    @Insert("insert into college_classes(class_name,college_id,headteacher_id) values(#{name},#{college_id},#{headteacher_id})")
    int addClass(@Param("name") String name,
                 @Param("college_id") Integer college_id,
                 @Param("headteacher_id") Integer headteacher_id);

    @Delete("delete from college_classes where class_id = #{id}")
    int deleteClass(Integer id);
}
