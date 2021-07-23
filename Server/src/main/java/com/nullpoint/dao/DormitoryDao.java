package com.nullpoint.dao;

import com.nullpoint.domain.Dormitory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DormitoryDao {
    @Select("SELECT * FROM dormitory d LEFT JOIN building b on d.building_id = b.building_id where d.dormitory_name like concat('%',#{query},'%') and d.id >= 1 ORDER BY d.building_id,d.dormitory_name")
    List<Dormitory> findAllByQuery(String query);

    @Insert("insert into dormitory(dormitory_name,building_id) values(#{dormitory_name},#{building_id})")
    int addDormitory(@Param("dormitory_name") String s,
                     @Param("building_id")int building_id);

    @Delete("delete from dormitory where id = #{id}")
    int deleteDormitoryById(int id);

    @Select("select * from dormitory where building_id = #{id}")
    List<Dormitory> findByBuildingId(Integer id);
}
