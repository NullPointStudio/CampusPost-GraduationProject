package com.nullpoint.service;

import com.nullpoint.domain.AdminThirdMenu;

import java.util.List;

public interface AdminThirdMenuService {
    List<AdminThirdMenu> findByParentId(String id);
}
