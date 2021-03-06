package com.streamly.TVApp.streamly.playervideo.zipcode;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Data {

    @SerializedName("address")
    private String mAddress;
    @SerializedName("device_info")
    private String mDeviceInfo;
    @SerializedName("latitude")
    private String mLatitude;
    @SerializedName("longitude")
    private String mLongitude;
    @SerializedName("timestamp")
    private String mTimestamp;
    @SerializedName("zipcode")
    private String mZipcode;

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getDeviceInfo() {
        return mDeviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        mDeviceInfo = deviceInfo;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public void setLatitude(String latitude) {
        mLatitude = latitude;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public void setLongitude(String longitude) {
        mLongitude = longitude;
    }

    public String getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(String timestamp) {
        mTimestamp = timestamp;
    }

    public String getZipcode() {
        return mZipcode;
    }

    public void setZipcode(String zipcode) {
        mZipcode = zipcode;
    }

}
