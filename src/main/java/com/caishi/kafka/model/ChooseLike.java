package com.caishi.kafka.model;

/**
 * 冷启动用户选择个人喜好 model
 * Created by root on 15-11-16.
 */
public class ChooseLike {

    private String userId;
    private String app;
    private String createTime;
    private String data;
    private String deviceId;
    private String deviceType;
    private String osType;
    private String topic;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "ChooseLike{" +
                "userId='" + userId + '\'' +
                ", app='" + app + '\'' +
                ", createTime='" + createTime + '\'' +
                ", data='" + data + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", osType='" + osType + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }
}
