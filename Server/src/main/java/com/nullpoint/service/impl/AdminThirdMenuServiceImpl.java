package com.nullpoint.service.impl;

import com.nullpoint.dao.AdminThirdMenuDao;
import com.nullpoint.domain.AdminThirdMenu;
import com.nullpoint.service.AdminThirdMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminThirdMenuServiceImpl implements AdminThirdMenuService {
    @Autowired
    AdminThirdMenuDao adminThirdMenuDao;

    @Override
    public List<AdminThirdMenu> findByParentId(String id) {
        return adminThirdMenuDao.findByParentId(id);
    }
}
