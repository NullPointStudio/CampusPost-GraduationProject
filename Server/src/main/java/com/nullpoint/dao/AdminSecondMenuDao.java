package com.nullpoint.dao;

import com.nullpoint.domain.AdminSecondMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminSecondMenuDao {
    @Select("select * from admin_second_menu where parentId = #{id}")
    List<AdminSecondMenu> findByParentId(@Param("id")String id);
}
