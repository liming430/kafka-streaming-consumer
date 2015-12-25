package com.caishi.kafka.model;

/**
 * 评论model
 * Created by root on 15-10-27.
 */
public class CommentEvent {
    private String userId;
    private String app;
    private String createTime;
    private String data;
    private String deviceId;
    private String deviceType;
    private String logType;
    private String messageId;
    private String messageType;
    private String referentType;
    private String topic;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

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

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getReferentType() {
        return referentType;
    }

    public void setReferentType(String referentType) {
        this.referentType = referentType;
    }

    @Override
    public String toString() {
        return "CommentEvent{" +
                "userId='" + userId + '\'' +
                ", app='" + app + '\'' +
                ", createTime='" + createTime + '\'' +
                ", data='" + data + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", logType='" + logType + '\'' +
                ", messageId='" + messageId + '\'' +
                ", messageType='" + messageType + '\'' +
                ", referentType='" + referentType + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }
}
