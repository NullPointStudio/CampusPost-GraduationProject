package com.nullpoint.dao;

import com.nullpoint.domain.AdminThirdMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminThirdMenuDao {
    @Select("select * from admin_third_menu where parentId = #{id}")
    List<AdminThirdMenu> findByParentId(@Param("id")String id);
}
