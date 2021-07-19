package com.nullpoint.service;

import com.nullpoint.domain.AdminSecondMenu;

import java.util.List;

public interface AdminSecondMenuService {
    List<AdminSecondMenu> findByParentId(String id);
}
