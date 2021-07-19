package com.nullpoint.service.impl;

import com.nullpoint.dao.AdminSecondMenuDao;
import com.nullpoint.domain.AdminSecondMenu;
import com.nullpoint.service.AdminSecondMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminSecondMenuServiceImpl implements AdminSecondMenuService {
    @Autowired
    AdminSecondMenuDao adminSecondMenuDao;

    @Override
    public List<AdminSecondMenu> findByParentId(String id) {
        return adminSecondMenuDao.findByParentId(id);
    }
}
