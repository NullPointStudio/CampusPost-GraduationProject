package com.nullpoint.mqtt.client;

import cn.hutool.http.HttpUtil;
import com.nullpoint.utils.PrintUtils;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class MessageCallBack implements MqttCallback {

    EmqClient emqClient;

    /**
     * 与服务端失去连接的回调方法
     * @param throwable
     */
    @Override
    public void connectionLost(Throwable throwable) {
        log.info("MQTT服务器连接丢失");
        //清理资源
        //重新连接
        emqClient.reConnect();
    }

    /**
     * 应用收到消息后出发的回调方法
     * @param s
     * @param mqttMessage
     * @throws Exception
     */
    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        log.info("收到消息，topic={},messageId={},qos={},payload={}",
                s,
                mqttMessage.getId(),
                mqttMessage.getQos(),
                new String(mqttMessage.getPayload()));

        //从SpringBoot服务器上下载文件
        downloadImage("http://127.0.0.1:8181" + new String(mqttMessage.getPayload()));
    }

    /**
     * 消息发布者发布完成产生的回调
     * @param token
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        log.info("消息发布完成：messageId = {},topic = {}",token.getMessageId(),token.getTopics());
    }

    public static String downloadImage(String fileUrl ) {
        long l = 0L;
        String path = null;
        String staticAndMksDir = null;
        String finalPath= null;
        if (fileUrl != null) {
            //下载时文件名称
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("."));
            try {
                String dataStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
                String uuidName = UUID.randomUUID().toString();
                path = "resources\\files\\"+dataStr+"\\"+uuidName+fileName;
                log.info(Paths.get(ResourceUtils.getURL("classpath:").toURI()).toString());
                staticAndMksDir = Paths.get(ResourceUtils.getURL("classpath:").toURI()).toString()+"/resources/files/"+dataStr+"/";
                HttpUtil.downloadFile(fileUrl, staticAndMksDir + File.separator + uuidName + fileName);
                finalPath = Paths.get(ResourceUtils.getURL("classpath:").toURI()).toString()+"\\"+path;
                log.info("本地文件地址："+finalPath);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        }
        System.out.println(System.currentTimeMillis()-l);
        try {
            PrintUtils.doPrint(finalPath);
        } catch (Exception e) {
            log.error("打印失败");
        }
        return finalPath;
    }
}
