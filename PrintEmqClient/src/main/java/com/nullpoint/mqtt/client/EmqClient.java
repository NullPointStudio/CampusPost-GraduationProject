package com.nullpoint.mqtt.client;

import com.nullpoint.mqtt.enums.QosEnum;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class EmqClient {

    private IMqttClient mqttClient;

    private final MqttCallback mqttCallback = new MessageCallBack();

    @PostConstruct
    public void init() {
        log.info("EmqClient开始初始化");
        MqttClientPersistence mempersitence = new MemoryPersistence();
        try {
            mqttClient = new MqttClient("tcp://127.0.0.1:1883", "printEmqClient", mempersitence);
            connect("PrintEmqClient","123456");
            subscribe("/printClient/#", QosEnum.QoS2);
            log.info("订阅成功");
        } catch (MqttException e) {
            log.error("初始化客户端MQTT-Client失败,ErrorMsg = {}", e.getMessage());
        }
    }

    /**
     * 客户端连接MQTT服务器的方法
     *
     * @param username 连接的用户名
     * @param password 连接的密码
     */
    public void connect(String username, String password) {
        MqttConnectOptions options = new MqttConnectOptions();
        // 自动重连
        options.setAutomaticReconnect(true);
        // 设置用户名
        options.setUserName(username);
        // 设置密码
        options.setPassword(password.toCharArray());
        // 开启临时会话
        options.setCleanSession(true);

        mqttClient.setCallback(mqttCallback);

        try {
            mqttClient.connect(options);
        } catch (MqttException e) {
            log.error("MQTT客户端连接失败：error = "+ e.getMessage());
        }
    }

    /**
     * 断开连接
     */
    public void disConnect() {
        try {
            mqttClient.disconnect();
        } catch (MqttException e) {
            log.error("MQTT客户端断开连接出现异常：" + e.getMessage());
        }
    }

    /**
     * 重连
     */
    public void reConnect() {
        try {
            mqttClient.reconnect();
            log.error("MQTT客户端重连成功");
        } catch (MqttException e) {
            log.error("MQTT客户端重连失败：" + e.getMessage());
        }
    }

    /**
     * 发布消息
     *
     * @param topic  主题
     * @param msg    消息体
     * @param qos    Qos 0，1，2
     * @param retain 是否为保留消息
     */
    public void publish(String topic, String msg, QosEnum qos, boolean retain) {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(msg.getBytes(StandardCharsets.UTF_8));
        mqttMessage.setQos(qos.value());
        mqttMessage.setRetained(retain);
        try {
            mqttClient.publish(topic, mqttMessage);
        } catch (MqttException e) {
            log.error("MQTT客户端发布消息失败：ErrorMsg = {}"+e.getMessage());
        }
    }

    /**
     * 订阅主题
     *
     * @param topicFilter 主题
     * @param qos         Qos
     */
    public void subscribe(String topicFilter, QosEnum qos) {
        try {
            mqttClient.subscribe(topicFilter, qos.value());
        } catch (MqttException e) {
            log.error("订阅主题失败：topicFilter={}，qos={},ErrorMsg="+e.getMessage());
        }
    }

    /**
     * 取消订阅的主题
     *
     * @param topicFilter
     */
    public void unSubscribe(String topicFilter) {
        try {
            mqttClient.unsubscribe(topicFilter);
        } catch (MqttException e) {
            log.error("取消订阅主题失败：topicFilter={}，ErrorMsg={}"+e.getMessage());
        }
    }
}
