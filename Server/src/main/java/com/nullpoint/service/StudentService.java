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

    List<Student> getClassStudentList(Integer class_id);

    List<Student> getClassStudentListNotIn(Integer class_id, List<Integer> ids);

    List<Student> getDormitoryStudentList(Integer dormitory_id);

    void changeDormitory(Integer dormitory_id, List<Integer> dropIds, List<Integer> addIds);

    List<Student> getCollegeStudentList(Integer college_id);

    Student getStudentByAccountId(Integer account_id);

    List<Student> getCollegeStudentListNotIn(Integer college_id, List<Integer> ids);

    void changeClass(Integer class_id, List<Integer> dropIds, List<Integer> addIds);
}
