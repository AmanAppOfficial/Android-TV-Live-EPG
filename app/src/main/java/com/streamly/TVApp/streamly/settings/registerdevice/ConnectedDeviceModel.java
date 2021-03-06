package com.streamly.TVApp.streamly.settings.registerdevice;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ConnectedDeviceModel {

    @SerializedName("data")
    private List<Datum> mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("numofdevice")
    private Long mNumofdevice;
    @SerializedName("response_code")
    private Long mResponseCode;
    @SerializedName("success")
    private Boolean mSuccess;

    public List<Datum> getData() {
        return mData;
    }

    public void setData(List<Datum> data) {
        mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Long getNumofdevice() {
        return mNumofdevice;
    }

    public void setNumofdevice(Long numofdevice) {
        mNumofdevice = numofdevice;
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
