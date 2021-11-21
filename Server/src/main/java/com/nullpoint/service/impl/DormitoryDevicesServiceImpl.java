package com.nullpoint.service.impl;

import com.github.pagehelper.PageHelper;
import com.nullpoint.dao.DormitoryDevicesDao;
import com.nullpoint.domain.DormitoryDevices;
import com.nullpoint.service.DormitoryDevicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DormitoryDevicesServiceImpl implements DormitoryDevicesService {
    @Autowired
    DormitoryDevicesDao dormitoryDevicesDao;
    @Override
    public List<DormitoryDevices> findDeviceByDormitoryId(Integer dormitory_id) {
        return dormitoryDevicesDao.findDeviceByDormitoryId(dormitory_id);
    }

    @Override
    public List<DormitoryDevices> findAll() {
        return dormitoryDevicesDao.findAll();
    }

    @Override
    public List<DormitoryDevices> findByQuery(String query, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return dormitoryDevicesDao.findByQuery(query);
    }
}
