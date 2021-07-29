package com.nullpoint.mqtt.client;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Arrays;

public class MessageCallBack implements MqttCallback {

    EmqClient emqClient;

    /**
     * 与服务端失去连接的回调方法
     * @param throwable
     */
    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("MQTT服务器连接丢失");
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
        System.out.println("收到消息，topic="+s+",messageId="+mqttMessage.getId()+",qos="+mqttMessage.getQos()+",payload="+new String(mqttMessage.getPayload()));

    }

    /**
     * 消息发布者发布完成产生的回调
     * @param token
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("消息发布完成：messageId = "+token.getMessageId()+",topic = "+ Arrays.toString(token.getTopics()));
    }
}
