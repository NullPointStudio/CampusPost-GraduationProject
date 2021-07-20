package com.nullpoint.dao;

import com.nullpoint.domain.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Account持久层接口
 */
@Mapper
public interface AccountDao {

    @Select("select * from account")
    List<Account> findAll();

    @Select("select * from account where account_id = #{account_id}")
    Account findById(String account_id);

    @Select("select * from account where account_phone = #{phone}")
    Account findByPhone(@Param("phone") String phone);

    @Insert("insert into account (account_username,account_password,account_phone) values (#{name},#{password},#{phone})")
    int addAccount(@Param("name") String name, @Param("password") String password, @Param("phone") String phone);
}
