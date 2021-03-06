package com.streamly.TVApp.streamly.log_in;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class LogInModel {

    @SerializedName("access_token")
    private String mAccessToken;
    @SerializedName("connected_device")
    private String mConnectedDevice;
    @SerializedName("device_info")
    private String mDeviceInfo;
    @SerializedName("expires_in")
    private Long mExpiresIn;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("response_code")
    private Long mResponseCode;
    @SerializedName("success")
    private Boolean mSuccess;
    @SerializedName("termandcondition")
    private Boolean mTermandcondition;
    @SerializedName("token_type")
    private String mTokenType;
    @SerializedName("user")
    private User mUser;

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }

    public String getConnectedDevice() {
        return mConnectedDevice;
    }

    public void setConnectedDevice(String connectedDevice) {
        mConnectedDevice = connectedDevice;
    }

    public String getDeviceInfo() {
        return mDeviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        mDeviceInfo = deviceInfo;
    }

    public Long getExpiresIn() {
        return mExpiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        mExpiresIn = expiresIn;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Long getResponseCode() {
        return mResponseCode;
    }

    public void setResponseCode(Long responseCode) {
        mResponseCode = responseCode;
    }

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean success) {
        mSuccess = success;
    }

    public Boolean getTermandcondition() {
        return mTermandcondition;
    }

    public void setTermandcondition(Boolean termandcondition) {
        mTermandcondition = termandcondition;
    }

    public String getTokenType() {
        return mTokenType;
    }

    public void setTokenType(String tokenType) {
        mTokenType = tokenType;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }


   /* @SerializedName("access_token")
    private String mAccessToken;
    @SerializedName("connected_device")
    private String mConnectedDevice;
    @SerializedName("device_info")
    private String mDeviceInfo;
    @SerializedName("expires_in")
    private Long mExpiresIn;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("response_code")
    private Long mResponseCode;
    @SerializedName("success")
    private Boolean mSuccess;
    @SerializedName("token_type")
    private String mTokenType;

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }

    public String getConnectedDevice() {
        return mConnectedDevice;
    }

    public void setConnectedDevice(String connectedDevice) {
        mConnectedDevice = connectedDevice;
    }

    public String getDeviceInfo() {
        return mDeviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        mDeviceInfo = deviceInfo;
    }

    public Long getExpiresIn() {
        return mExpiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        mExpiresIn = expiresIn;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Long getResponseCode() {
        return mResponseCode;
    }

    public void setResponseCode(Long responseCode) {
        mResponseCode = responseCode;
    }

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean success) {
        mSuccess = success;
    }

    public String getTokenType() {
        return mTokenType;
    }

    public void setTokenType(String tokenType) {
        mTokenType = tokenType;
    }*/

}
