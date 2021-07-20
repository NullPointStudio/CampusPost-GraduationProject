package com.nullpoint.service;

import com.nullpoint.domain.Account;

import java.util.List;

public interface AccountService {
    List<Account> findAll();

    boolean login(String account_id,String password);

    Integer loginByPhone(String phone,String password);
}
