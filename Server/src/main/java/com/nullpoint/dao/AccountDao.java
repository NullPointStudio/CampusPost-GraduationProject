package com.nullpoint.dao;

import com.nullpoint.domain.Account;
import org.apache.ibatis.annotations.Mapper;
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
}
