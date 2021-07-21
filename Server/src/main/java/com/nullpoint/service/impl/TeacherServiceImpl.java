package com.nullpoint.service.impl;

import com.github.pagehelper.PageHelper;
import com.nullpoint.dao.AccountDao;
import com.nullpoint.dao.TeacherDao;
import com.nullpoint.domain.Account;
import com.nullpoint.domain.Teacher;
import com.nullpoint.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public void addAccountByPhone(String name, String phone, String sex, int select_college) {
        int i = accountDao.addAccount(name, "123456", phone);
        if (i == 0) throw new RuntimeException("添加失败");
        Account account = accountDao.findByPhone(phone);
        int n = teacherDao.addTeacher(account.getAccount_id(), name, name, sex, "", select_college, phone);
        if (n == 0) throw new RuntimeException("添加失败");
    }

    @Override
    public List<Teacher> findAllPages(String query, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return teacherDao.findAllByQuery(query);
    }

    @Override
    @Transactional
    public void editAccountByPhone(String id, String name, String phone, String sex, Integer college_id) {
        Account account = accountDao.findById(id);
        if (account == null) throw new RuntimeException("找不到此用户");
        int i = accountDao.updateAccount(id, account.getAccount_username(), account.getAccount_password(), phone);
        if (i == 0) throw new RuntimeException("修改失败");
        Teacher teacher = teacherDao.findByAccountId(id);
        if (teacher == null) throw new RuntimeException("找不到此教师");
        int i2 = teacherDao.updateTeacherByAccountId(id, name, teacher.getUsername(), sex, teacher.getAvatar(), college_id, phone);
        if (i2 == 0) throw new RuntimeException("修改失败");
    }

    @Override
    @Transactional
    public void deleteTeacher(String id) {
        int i = teacherDao.deleteByAccountId(id);
        if (i == 0) throw new RuntimeException("删除失败");
        int i2 = accountDao.deleteById(id);
        if (i2 == 0) throw new RuntimeException("删除失败");
    }
}
