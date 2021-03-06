package com.streamly.TVApp.streamly.playervideo.drm;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DrmKeyModel {

    @SerializedName("data")
    private String mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("playreadylicense")
    private String mPlayreadylicense;
    @SerializedName("response_code")
    private Long mResponseCode;
    @SerializedName("success")
    private Boolean mSuccess;
    @SerializedName("widevinelicense")
    private String mWidevinelicense;

    public String getData() {
        return mData;
    }


    public void setData(String data) {
        mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getPlayreadylicense() {
        return mPlayreadylicense;
    }

    public void setPlayreadylicense(String playreadylicense) {
        mPlayreadylicense = playreadylicense;
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

    public String getWidevinelicense() {
        return mWidevinelicense;
    }

    public void setWidevinelicense(String widevinelicense) {
        mWidevinelicense = widevinelicense;
    }

}
