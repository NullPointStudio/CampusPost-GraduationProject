package com.nullpoint.domain;

import java.io.Serializable;

public class Student implements Serializable {
    Integer id;
    Integer account_id;
    String turename;
    String username;
    String sex;
    String avatar;
    Integer college_id;
    String phone;
    Integer dormitory_id;
    String dormitory_name;
    Integer college_class_id;
    String class_name;

    public Integer getCollege_class_id() {
        return college_class_id;
    }

    public void setCollege_class_id(Integer college_class_id) {
        this.college_class_id = college_class_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public Integer getDormitory_id() {
        return dormitory_id;
    }

    public void setDormitory_id(Integer dormitory_id) {
        this.dormitory_id = dormitory_id;
    }

    public String getDormitory_name() {
        return dormitory_name;
    }

    public void setDormitory_name(String dormitory_name) {
        this.dormitory_name = dormitory_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Integer account_id) {
        this.account_id = account_id;
    }

    public String getTurename() {
        return turename;
    }

    public void setTurename(String turename) {
        this.turename = turename;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getCollege_id() {
        return college_id;
    }

    public void setCollege_id(Integer college_id) {
        this.college_id = college_id;
    }
}
