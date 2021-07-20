package com.nullpoint.service;

import com.nullpoint.domain.Teacher;

import java.util.List;

public interface TeacherService {
    List<Teacher> findAll();

    void addAccountByPhone(String name, String phone, String sex, int select_college) throws Exception;

    List<Teacher> findAllPages(int pageNum, int pageSize);
}
