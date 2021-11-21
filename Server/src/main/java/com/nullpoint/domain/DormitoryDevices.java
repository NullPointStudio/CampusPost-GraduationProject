package com.nullpoint.domain;

public class DormitoryDevices {
    Integer device_id;
    String device_name;
    Integer dormitory_id;
    Integer device_state;
    String device_sub_path;
    String deviceIcon;
    String dormitory_name;

    public String getDormitory_name() {
        return dormitory_name;
    }

    public void setDormitory_name(String dormitory_name) {
        this.dormitory_name = dormitory_name;
    }

    public String getDeviceIcon() {
        return deviceIcon;
    }

    public void setDeviceIcon(String deviceIcon) {
        this.deviceIcon = deviceIcon;
    }

    public Integer getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Integer device_id) {
        this.device_id = device_id;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public Integer getDormitory_id() {
        return dormitory_id;
    }

    public void setDormitory_id(Integer dormitory_id) {
        this.dormitory_id = dormitory_id;
    }

    public Integer getDevice_state() {
        return device_state;
    }

    public void setDevice_state(Integer device_state) {
        this.device_state = device_state;
    }

    public String getDevice_sub_path() {
        return device_sub_path;
    }

    public void setDevice_sub_path(String device_sub_path) {
        this.device_sub_path = device_sub_path;
    }
}
