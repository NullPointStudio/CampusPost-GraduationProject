package com.nullpoint.mqtt.client;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageCallBack implements MqttCallback {
    private static final Logger log = LoggerFactory.getLogger(MessageCallBack.class);

    @Autowired
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
    }

    /**
     * 消息发布者发布完成产生的回调
     * @param token
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        log.info("消息发布完成：messageId = {},topic = {}",token.getMessageId(),token.getTopics());
    }
}
