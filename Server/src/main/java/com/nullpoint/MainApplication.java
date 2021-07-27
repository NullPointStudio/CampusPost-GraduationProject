package com.nullpoint;

import com.nullpoint.mqtt.client.EmqClient;
import com.nullpoint.mqtt.enums.QosEnum;
import com.nullpoint.properties.MqttProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@EnableTransactionManagement
@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class,args);
    }
//    @Autowired
//    EmqClient emqClient;
//
//    @Autowired
//    MqttProperties mqttProperties;
//
//    public static void init(){
//        // 连接服务端
//        emqClient.connect(mqttProperties.getUsername(),mqttProperties.getPassword());
//        // 订阅主题
//        emqClient.subscribe("/SpringBootTopic/#", QosEnum.QoS2);
//        // 开启新线程 每隔5秒去/SpringBootTopic/test发布消息
//        new Thread(() -> {
//            while (true){
//                emqClient.publish("/SpringBootTopic/test",
//                        "MSG:"+ LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
//                        QosEnum.QoS2,
//                        false);
//                try {
//                    TimeUnit.SECONDS.sleep(5);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
}
