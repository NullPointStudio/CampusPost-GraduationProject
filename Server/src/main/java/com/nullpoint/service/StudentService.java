package com.nullpoint.service;

import com.nullpoint.domain.Student;
import com.nullpoint.domain.Teacher;

import java.util.List;

public interface StudentService {

    List<Student> findAll();

    void addAccountByPhone(String name, String phone, String sex, int select_college);

    List<Student> findAllPages(String query, int pageNum, int pageSize);

    void editAccountByPhone(String id, String name, String phone, String sex, Integer college_id);

    void deleteStudent(String id);
}
