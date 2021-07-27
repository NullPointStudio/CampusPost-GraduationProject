package com.nullpoint.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MQTTUserDao {
    @Select("select user_password from mqtt_user where user_name = #{username}")
    String getPasswordByUsername(String username);
}
