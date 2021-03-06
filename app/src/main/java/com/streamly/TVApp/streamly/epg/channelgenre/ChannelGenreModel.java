
package com.streamly.TVApp.streamly.epg.channelgenre;

import java.util.List;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class ChannelGenreModel {

    @SerializedName("allGenre")
    private List<String> mAllGenre;
    @SerializedName("data")
    private Data mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("response_code")
    private Long mResponseCode;
    @SerializedName("success")
    private Boolean mSuccess;

    public List<String> getAllGenre() {
        return mAllGenre;
    }

    public void setAllGenre(List<String> allGenre) {
        mAllGenre = allGenre;
    }

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
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
