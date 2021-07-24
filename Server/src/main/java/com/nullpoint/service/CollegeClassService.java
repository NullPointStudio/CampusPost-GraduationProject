package com.nullpoint.service;

import com.nullpoint.domain.CollegeClass;

import java.util.List;

public interface CollegeClassService {
    List<CollegeClass> getCollegeClassList(String query, int pageNum, int pageSize);

    void editClassTeacherAndMonitor(Integer headTeacherId, Integer monitorId,Integer classID);

    void addClass(String name, Integer college_id, Integer headteacher_id);

    void deleteClass(Integer id);

    List<CollegeClass> getCollegeClassByCollegeId(Integer college_id);
}
