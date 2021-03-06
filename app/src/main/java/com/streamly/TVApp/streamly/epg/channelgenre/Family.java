
package com.streamly.TVApp.streamly.epg.channelgenre;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Family {

    @SerializedName("callSign")
    private String mCallSign;
    @SerializedName("genrename")
    private String mGenrename;
    @SerializedName("id")
    private Long mId;
    @SerializedName("image")
    private Object mImage;
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

    public String getGenrename() {
        return mGenrename;
    }

    public void setGenrename(String genrename) {
        mGenrename = genrename;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public Object getImage() {
        return mImage;
    }

    public void setImage(Object image) {
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

}
