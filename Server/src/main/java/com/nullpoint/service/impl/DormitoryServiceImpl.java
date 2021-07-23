package com.nullpoint.service.impl;

import com.github.pagehelper.PageHelper;
import com.nullpoint.dao.BuildingDao;
import com.nullpoint.dao.DormitoryDao;
import com.nullpoint.domain.Building;
import com.nullpoint.domain.Dormitory;
import com.nullpoint.service.DormitoryService;
import com.nullpoint.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DormitoryServiceImpl implements DormitoryService {

    @Autowired
    DormitoryDao dormitoryDao;

    @Autowired
    BuildingDao buildingDao;


    @Override
    public List<Dormitory> findAllByQuery(String query,int PageNum,int PageSize) {
        PageHelper.startPage(PageNum, PageSize);
        return dormitoryDao.findAllByQuery(query);
    }

    @Override
    public List<Building> getBuildings() {
        return buildingDao.findAll();
    }

    @Override
    public void addDormitoryByAdmin(int building_id, String dormitory_name) {
        String buildingName = buildingDao.getNameById(building_id);
        if (TextUtils.isEmpty(buildingName)) throw new RuntimeException("找不到该楼号");
        int i = dormitoryDao.addDormitory(buildingName+dormitory_name,building_id);
        if (i==0)throw new RuntimeException("添加失败");
    }

    @Override
    public void deleteDormitory(int id) {
        int i = dormitoryDao.deleteDormitoryById(id);
        if (i == 0) throw new RuntimeException("删除失败");
    }

    @Override
    public List<Dormitory> findAllByBuildingId(Integer id) {
        return dormitoryDao.findByBuildingId(id);
    }
}
