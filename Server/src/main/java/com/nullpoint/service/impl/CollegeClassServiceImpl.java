package com.nullpoint.service.impl;

import com.github.pagehelper.PageHelper;
import com.nullpoint.dao.CollegeClassDao;
import com.nullpoint.domain.CollegeClass;
import com.nullpoint.service.CollegeClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollegeClassServiceImpl implements CollegeClassService {

    @Autowired
    CollegeClassDao collegeClassDao;

    @Override
    public List<CollegeClass> getCollegeClassList(String query, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return collegeClassDao.findAllByQuery(query);
    }

    @Override
    public void editClassTeacherAndMonitor(Integer headTeacherId, Integer monitorId,Integer classID) {
        collegeClassDao.editClassTeacherAndMonitor(headTeacherId,monitorId,classID);
    }

    @Override
    public void addClass(String name, Integer college_id, Integer headteacher_id) {
        collegeClassDao.addClass(name,college_id,headteacher_id);
    }

    @Override
    public void deleteClass(Integer id) {
        collegeClassDao.deleteClass(id);
    }
}
