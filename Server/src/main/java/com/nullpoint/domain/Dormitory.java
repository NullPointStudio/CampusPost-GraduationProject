package com.nullpoint.domain;

import java.io.Serializable;

public class Dormitory implements Serializable {
    Integer id;
    String dormitory_name;
    String building_id;
    String building_name;
    Integer dormitory_num;

    public String getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(String building_id) {
        this.building_id = building_id;
    }

    public String getBuilding_name() {
        return building_name;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDormitory_name() {
        return dormitory_name;
    }

    public void setDormitory_name(String dormitory_name) {
        this.dormitory_name = dormitory_name;
    }
    public Integer getDormitory_num() {
        return dormitory_num;
    }

    public void setDormitory_num(Integer dormitory_num) {
        this.dormitory_num = dormitory_num;
    }
}
