package com.nullpoint.dao;

import com.nullpoint.domain.Account;
import org.apache.ibatis.annotations.*;

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

    @Update("update account set account_username = #{account_username},account_password = #{account_password},account_phone = #{phone} where account_id = #{id}")
    int updateAccount(@Param("id")String id,
                      @Param("account_username")String account_username,
                      @Param("account_password")String account_password,
                      @Param("phone")String phone);

    @Delete("delete from account where account_id = #{id}")
    int deleteById(String id);
}
