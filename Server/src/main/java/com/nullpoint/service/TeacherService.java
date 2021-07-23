package com.nullpoint.service;

import com.nullpoint.domain.Teacher;

import java.util.List;

public interface TeacherService {

    List<Teacher> findAll();

    void addAccountByPhone(String name, String phone, String sex, int select_college) throws Exception;

    List<Teacher> findAllPages(String query, int pageNum, int pageSize);

    void editAccountByPhone(String id, String name, String phone, String sex, Integer college_id);

    void deleteTeacher(String id);

    List<Teacher> getTeacherByCollege(Integer college_id);
}
