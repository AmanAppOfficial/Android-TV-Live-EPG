package com.streamly.TVApp.streamly.epg;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Datum {

    @SerializedName("callSign")
    private String mCallSign;
    @SerializedName("genrename")
    private List<String> mGenrename;
    @SerializedName("id")
    private Long mId;
    @SerializedName("image")
    private String mImage;
    @SerializedName("logo")
    private String mLogo;
    @SerializedName("url")
    private String mUrl;

    public String getCallSign() {
        return mCallSign;
    }

    public void setCallSign(String callSign) {
        mCallSign = callSign;
    }

    public List<String> getGenrename() {
        return mGenrename;
    }

    public void setGenrename(List<String> genrename) {
        mGenrename = genrename;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getLogo() {
        return mLogo;
    }

    public void setLogo(String logo) {
        mLogo = logo;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }


    @Override
    public String toString() {
        return "{" +
                "mCallSign='" + mCallSign + '\'' +
                ", mGenrename=" + mGenrename +
                ", mId=" + mId +
                ", mImage='" + mImage + '\'' +
                ", mLogo='" + mLogo + '\'' +
                ", mUrl='" + mUrl + '\'' +
                '}';
    }
}
