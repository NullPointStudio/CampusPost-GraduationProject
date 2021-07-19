package com.nullpoint.dao;

import com.nullpoint.domain.AdminFirstMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminFirstMenuDao {
    @Select("select * from admin_first_menu")
    List<AdminFirstMenu> findAll();
}
