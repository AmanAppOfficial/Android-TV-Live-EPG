package com.streamly.TVApp.streamly.settings.registerdevice;

public class RegisterDeviceType {

    private String deviceModel;
    private String deviceId;


    public RegisterDeviceType() {
    }

    public RegisterDeviceType(String deviceModel, String deviceId) {
        this.deviceModel = deviceModel;
        this.deviceId = deviceId;
    }


    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
