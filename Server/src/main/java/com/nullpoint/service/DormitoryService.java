package com.nullpoint.service;

import com.nullpoint.domain.Building;
import com.nullpoint.domain.Dormitory;

import java.util.List;

public interface DormitoryService {
    List<Dormitory> findAllByQuery(String query,int PageNum,int PageSize);

    List<Building> getBuildings();

    void addDormitoryByAdmin(int building_id, String dormitory_name);

    void deleteDormitory(int id);

    List<Dormitory> findAllByBuildingId(Integer id);
}
