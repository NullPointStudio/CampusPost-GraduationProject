package com.nullpoint.service.impl;

import com.github.pagehelper.PageHelper;
import com.nullpoint.dao.AccountDao;
import com.nullpoint.dao.TeacherDao;
import com.nullpoint.domain.Account;
import com.nullpoint.domain.Teacher;
import com.nullpoint.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    TeacherDao teacherDao;

    @Autowired
    AccountDao accountDao;

    @Override
    public List<Teacher> findAll() {
        return teacherDao.findAll();
    }

    @Override
    @Transactional
    public void addAccountByPhone(String name, String phone,String sex,int select_college) throws Exception {
        int i = accountDao.addAccount(name,"123456",phone);
        if (i==0) throw new Exception("添加失败");
        Account account = accountDao.findByPhone(phone);
        int n = teacherDao.addTeacher(account.getAccount_id(),name,name,sex,"",select_college,phone);
        if (n==0) throw new Exception("添加失败");
    }

    @Override
    public List<Teacher> findAllPages(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return teacherDao.findAll();
    }
}
