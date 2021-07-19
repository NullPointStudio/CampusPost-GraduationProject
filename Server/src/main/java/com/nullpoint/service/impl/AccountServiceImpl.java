package com.nullpoint.service.impl;

import com.nullpoint.dao.AccountDao;
import com.nullpoint.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements com.nullpoint.service.AccountService {

    @Autowired
    AccountDao accountDao;

    @Override
    public List<Account> findAll() {
        return accountDao.findAll();
    }

    @Override
    public boolean login(String account_id, String password) {
        Account account = accountDao.findById(account_id);
        if (account==null) return false;
        return account.getAccount_password().equals(password);
    }
}
