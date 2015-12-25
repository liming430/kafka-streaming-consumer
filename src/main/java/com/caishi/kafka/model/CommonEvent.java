package com.caishi.kafka.model;

/**
 * Created by root on 15-10-27.
 */
public class CommonEvent {
    private String app;
    private String createTime;
    private String data;
    private String deviceId;
    private String deviceType;
    private String userId;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return userId + '\t' +
        deviceType + '\t' +
        deviceId + '\t' +
                createTime + '\t' +
                data;
    }
}
