package com.nullpoint.domain;

import java.io.Serializable;

public class CollegeClass implements Serializable {
    Integer class_id;
    String class_name;
    Integer college_id;
    Integer headteacher_id;
    Integer monitor_id;
    String college_name;
    String headteacher_name;
    String monitor_name;

    public Integer getClass_id() {
        return class_id;
    }

    public void setClass_id(Integer class_id) {
        this.class_id = class_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public Integer getCollege_id() {
        return college_id;
    }

    public void setCollege_id(Integer college_id) {
        this.college_id = college_id;
    }

    public Integer getHeadteacher_id() {
        return headteacher_id;
    }

    public void setHeadteacher_id(Integer headteacher_id) {
        this.headteacher_id = headteacher_id;
    }

    public Integer getMonitor_id() {
        return monitor_id;
    }

    public void setMonitor_id(Integer monitor_id) {
        this.monitor_id = monitor_id;
    }

    public String getCollege_name() {
        return college_name;
    }

    public void setCollege_name(String college_name) {
        this.college_name = college_name;
    }

    public String getHeadteacher_name() {
        return headteacher_name;
    }

    public void setHeadteacher_name(String headteacher_name) {
        this.headteacher_name = headteacher_name;
    }

    public String getMonitor_name() {
        return monitor_name;
    }

    public void setMonitor_name(String monitor_name) {
        this.monitor_name = monitor_name;
    }
}
