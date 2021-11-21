package com.nullpoint.service;

import com.nullpoint.domain.DormitoryDevices;

import java.util.List;

public interface DormitoryDevicesService {
    List<DormitoryDevices> findDeviceByDormitoryId(Integer dormitory_id);

    List<DormitoryDevices> findAll();

    List<DormitoryDevices> findByQuery(String query, int pageNum, int pageSize);
}
