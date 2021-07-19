package com.nullpoint.service;

import com.nullpoint.domain.Account;

import java.util.List;

public interface AccountServiceImpl {
    List<Account> findAll();

    boolean login(String account_id,String password);
}
