
package com.streamly.TVApp.streamly.channelschedule;

import java.util.List;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class ChannelScheduleModel {

    @SerializedName("data")
    private List<Datum> mData;
    @SerializedName("date")
    private String mDate;
    @SerializedName("message")
    private String mMessage;
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

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
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

}
