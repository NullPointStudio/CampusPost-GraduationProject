package com.nullpoint.controller;

import com.nullpoint.mqtt.client.EmqClient;
import com.nullpoint.mqtt.enums.QosEnum;
import com.nullpoint.properties.MqttProperties;
import com.nullpoint.utils.MVUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("print")
public class PrintController {

    @Autowired
    EmqClient emqClient;

    @Autowired
    MqttProperties mqttProperties;

    @RequestMapping(value = "uploadFile",method = RequestMethod.POST)
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile uploadFile, HttpServletRequest request) throws IOException {
        ModelAndView mv = MVUtils.getJsonMV();
        if (uploadFile.isEmpty())return new ResponseEntity("没有选择上传的文件", HttpStatus.INTERNAL_SERVER_ERROR);
        String realPath = request.getSession().getServletContext().getRealPath("/static/printFiles/");
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdirs();
        }
        String filename = "";
        String[] suffixs = {".png",".PNG",".JPG",".jpg",".JPEG",".jpeg",".docx",".DOCX",".pdf","PDF",".xlsx",".XLSX"};
        filename = uploadFile.getOriginalFilename();
        String suffix = filename.substring(filename.indexOf('.')!=-1?filename.indexOf('.'):0);
        if (!Arrays.asList(suffixs).contains(suffix)){
            return new ResponseEntity("上传文件类型不支持", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        filename = UUID.randomUUID()+suffix;
        uploadFile.transferTo(new File(realPath,filename));
        log.info("/static/printFiles/"+filename);
        emqClient.connect(mqttProperties.getUsername(), mqttProperties.getPassword(), new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
                log.info("MQTT服务器连接丢失");
                //清理资源
                //重新连接
                emqClient.reConnect();
            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                log.info("消息发布完成：messageId = {},topic = {}",token.getMessageId(),token.getTopics());
            }
        });
        emqClient.publish("/printClient/123","/static/printFiles/"+filename, QosEnum.QoS2,false);
//        emqClient.disConnect();
        return new ResponseEntity(realPath+filename, HttpStatus.OK);

    }
}
