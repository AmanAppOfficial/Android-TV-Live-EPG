package com.streamly.TVApp.streamly.settings.registerdevice;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Datum {

    @SerializedName("connected_devices")
    private Long mConnectedDevices;
    @SerializedName("device_id")
    private String mDeviceId;
    @SerializedName("device_name")
    private String mDeviceName;

    public Long getConnectedDevices() {
        return mConnectedDevices;
    }

    public void setConnectedDevices(Long connectedDevices) {
        mConnectedDevices = connectedDevices;
    }

    public String getDeviceId() {
        return mDeviceId;
    }

    public void setDeviceId(String deviceId) {
        mDeviceId = deviceId;
    }

    public String getDeviceName() {
        return mDeviceName;
    }

    public void setDeviceName(String deviceName) {
        mDeviceName = deviceName;
    }

}
