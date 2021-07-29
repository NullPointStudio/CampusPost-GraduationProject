package com.nullpoint;

import com.nullpoint.mqtt.client.EmqClient;
import com.nullpoint.mqtt.enums.QosEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication
public class PrintEmqClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrintEmqClientApplication.class, args);
    }

    @Autowired
    static EmqClient emqClient;

//    @PostConstruct
//    public static void init(){
//        emqClient.connect("PrintEmqClient","123456");
//        emqClient.subscribe("/printClient/#", QosEnum.QoS2);
//        log.info("订阅成功");
//    }
}
