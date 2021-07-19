package com.nullpoint.service.impl;

import com.nullpoint.dao.AdminFirstMenuDao;
import com.nullpoint.domain.AdminFirstMenu;
import com.nullpoint.service.AdminFirstMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminFirstMenuServiceImpl implements AdminFirstMenuService {
    @Autowired
    AdminFirstMenuDao adminFirstMenuDao;

    @Override
    public List<AdminFirstMenu> findAll() {
        return adminFirstMenuDao.findAll();
    }
}
