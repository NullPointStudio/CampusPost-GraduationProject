package com.nullpoint.dao;

import com.nullpoint.domain.DormitoryDevices;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DormitoryDevicesDao {
    @Select("select * from dormitory_devices where dormitory_id = #{dormitory_id}")
    List<DormitoryDevices> findDeviceByDormitoryId(@Param("dormitory_id") Integer dormitory_id);

    @Select("select * from dormitory_devices")
    List<DormitoryDevices> findAll();

    @Select("select * from dormitory_devices dd " +
            "LEFT JOIN dormitory d on dd.dormitory_id = d.id" +
            " where dd.device_name like concat('%',#{query},'%')"+
            " order by dd.device_id")
    List<DormitoryDevices> findByQuery(String query);
}
