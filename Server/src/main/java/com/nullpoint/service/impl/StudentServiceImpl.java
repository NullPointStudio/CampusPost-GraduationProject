package com.nullpoint.service.impl;

import com.github.pagehelper.PageHelper;
import com.nullpoint.dao.AccountDao;
import com.nullpoint.dao.StudentDao;
import com.nullpoint.dao.TeacherDao;
import com.nullpoint.domain.Account;
import com.nullpoint.domain.Student;
import com.nullpoint.domain.Teacher;
import com.nullpoint.service.StudentService;
import com.nullpoint.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentDao studentDao;

    @Autowired
    AccountDao accountDao;

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    @Transactional
    public void addAccountByPhone(String name, String phone, String sex, int select_college) {
        int i = accountDao.addAccount(name, "123456", phone);
        if (i == 0) throw new RuntimeException("添加失败");
        Account account = accountDao.findByPhone(phone);
        int n = studentDao.addStudent(account.getAccount_id(), name, name, sex, "", select_college, phone);
        if (n == 0) throw new RuntimeException("添加失败");
    }

    @Override
    public List<Student> findAllPages(String query, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return studentDao.findAllByQuery(query);
    }

    @Override
    @Transactional
    public void editAccountByPhone(String id, String name, String phone, String sex, Integer college_id) {
        Account account = accountDao.findById(id);
        if (account == null) throw new RuntimeException("找不到此用户");
        int i = accountDao.updateAccount(id, account.getAccount_username(), account.getAccount_password(), phone);
        if (i == 0) throw new RuntimeException("修改失败");
        Student student = studentDao.findByAccountId(id);
        if (student == null) throw new RuntimeException("找不到此学生");
        int i2 = studentDao.updateStudentByAccountId(id, name, student.getUsername(), sex, student.getAvatar(), college_id, phone);
        if (i2 == 0) throw new RuntimeException("修改失败");
    }

    @Override
    @Transactional
    public void deleteStudent(String id) {
        int i = studentDao.deleteByAccountId(id);
        if (i == 0) throw new RuntimeException("删除失败");
        int i2 = accountDao.deleteById(id);
        if (i2 == 0) throw new RuntimeException("删除失败");
    }

    @Override
    public List<Student> getClassStudentList(Integer class_id) {
        return studentDao.getClassStudentList(class_id);
    }

    @Override
    public List<Student> getClassStudentListNotIn(Integer class_id, List<Integer> ids) {
        return studentDao.getClassStudentListNotIn(class_id, ids);
    }

    @Override
    public List<Student> getDormitoryStudentList(Integer dormitory_id) {
        return studentDao.getDormitoryStudentList(dormitory_id);
    }

    @Override
    public void changeDormitory(Integer dormitory_id, List<Integer> dropIds, List<Integer> addIds) {
        if (dropIds.size() != 0) {
            for (Integer account_id : dropIds) {
                studentDao.setDormitoryId(account_id,0);
            }
        }
        if (addIds.size() != 0){
            for(Integer account_id : addIds){
                studentDao.setDormitoryId(account_id,dormitory_id);
            }
        }
    }

    @Override
    public List<Student> getCollegeStudentList(Integer college_id) {
        return studentDao.getCollegeStudentList(college_id);
    }

    @Override
    public Student getStudentByAccountId(Integer account_id) {
        return studentDao.findByAccountId(account_id+"");
    }

    @Override
    public List<Student> getCollegeStudentListNotIn(Integer college_id, List<Integer> ids) {
        return studentDao.getCollegeStudentListNotIn(college_id,ids);
    }

    @Override
    public void changeClass(Integer class_id, List<Integer> dropIds, List<Integer> addIds) {
        if (dropIds.size() != 0) {
            for (Integer account_id : dropIds) {
                studentDao.setClassId(account_id,null);
            }
        }
        if (addIds.size() != 0){
            for(Integer account_id : addIds){
                studentDao.setClassId(account_id,class_id);
            }
        }
    }
}
