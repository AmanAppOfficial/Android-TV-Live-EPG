package com.streamly.TVApp.streamly.playervideo.geofence;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class GeofenceModel {

    @SerializedName("data")
    private String mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("popupmsg")
    private String mPopupmsg;
    @SerializedName("response_code")
    private Long mResponseCode;
    @SerializedName("success")
    private Boolean mSuccess;

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

    public String getPopupmsg() {
        return mPopupmsg;
    }

    public void setPopupmsg(String popupmsg) {
        mPopupmsg = popupmsg;
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

}
