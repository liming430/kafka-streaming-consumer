package com.caishi.kafka.model;

/**
 * Created by root on 15-10-27.
 */
public class NewsBehavior {
//    userId,deviceType,deviceId,createTime,messageId,messageType,logType,referentId,
// referentType,data
    private String app;
    private String userId;
    private String deviceType;
    private String deviceId;
    private String createTime;
    private String messageId;
    private String messageType;
    private String logType;
    private String referentId;
    private String referentType;
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getReferentId() {
        return referentId;
    }

    public void setReferentId(String referentId) {
        this.referentId = referentId;
    }

    public String getReferentType() {
        return referentType;
    }

    public void setReferentType(String referentType) {
        this.referentType = referentType;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    @Override
    public String toString() {
        return  userId  + '\t' +
                deviceType  + '\t' +
                deviceId  + '\t' +
                createTime  + '\t' +
                messageId  + '\t' +
                messageType  + '\t' +
                logType  + '\t' +
                referentId  + '\t' +
                referentType + '\t' +
                data;
    }
}
