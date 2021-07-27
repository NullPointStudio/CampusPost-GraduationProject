package com.nullpoint.service.impl;

import com.nullpoint.dao.MQTTUserDao;
import com.nullpoint.service.MQTTUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQTTUserServiceImpl implements MQTTUserService {
    @Autowired
    MQTTUserDao mqttUserDao;
    @Override
    public String getPassword(String username) {
        return mqttUserDao.getPasswordByUsername(username);
    }
}
