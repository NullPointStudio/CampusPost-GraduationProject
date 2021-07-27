package com.nullpoint.controller;

import com.nullpoint.mqtt.client.EmqClient;
import com.nullpoint.mqtt.enums.QosEnum;
import com.nullpoint.properties.MqttProperties;
import com.nullpoint.utils.MVUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/mqttTest")
public class EmqTestController {


    @Autowired
    EmqClient emqClient;

    @Autowired
    MqttProperties mqttProperties;

    @RequestMapping("/init1")
    public void init(){
        // 连接服务端
        emqClient.connect(mqttProperties.getUsername(),mqttProperties.getPassword());
        // 订阅主题
        emqClient.subscribe("/SpringBootTopic/#", QosEnum.QoS2);
        // 开启新线程 每隔5秒去/SpringBootTopic/test发布消息
        new Thread(() -> {
            while (true){
                emqClient.publish("/SpringBootTopic/test",
                        "MSG:"+ LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
                        QosEnum.QoS2,
                        false);
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
