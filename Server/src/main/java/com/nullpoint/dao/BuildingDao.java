package com.nullpoint.dao;

import com.nullpoint.domain.Building;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BuildingDao {

    @Select("select * from building")
    List<Building> findAll();

    @Select("select building_name from building where building_id = #{building_id}")
    String getNameById(int building_id);
}
