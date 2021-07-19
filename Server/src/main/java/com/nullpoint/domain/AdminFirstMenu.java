package com.nullpoint.domain;


import java.io.Serializable;

public class AdminFirstMenu implements Serializable {
    Integer menuId;
    String menuName;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
}
