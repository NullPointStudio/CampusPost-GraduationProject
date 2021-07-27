package com.nullpoint.controller;

import com.nullpoint.service.MQTTUserService;
import com.nullpoint.utils.MD5Utils;
import com.nullpoint.utils.MVUtils;
import com.nullpoint.utils.TextUtils;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("/mqtt")
public class MQTTAuthController {

    private static final Logger log = LoggerFactory.getLogger(MQTTAuthController.class);

    @Autowired
    MQTTUserService mqttUserService;

    @RequestMapping(value = "auth",method = RequestMethod.POST)
    public ResponseEntity auth(@RequestParam("clientid") String clientId,
                               @RequestParam("username") String username,
                               @RequestParam("password") String password) throws NoSuchAlgorithmException {
        log.info("MQTT服务器开始调用此接口进行认证，clientId = {},username = {},password = {}",clientId,username,password);
        String value = mqttUserService.getPassword(username);
        if (TextUtils.isEmpty(value)){
            // 本来应该返回ignore，进行下种方法验证
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        String myPass = MD5Utils.getMd5val(password+"MQTT");
        if (!value.equals(myPass)){
            log.info("value:"+value);
            log.info("password:"+myPass);
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
