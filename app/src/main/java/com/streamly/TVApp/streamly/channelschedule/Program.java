package com.streamly.TVApp.streamly.channelschedule;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Program {

    @SerializedName("end")
    private Long mEnd;
    @SerializedName("start")
    private Long mStart;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("description")
    private String mDescription;

    public Long getEnd() {
        return mEnd;
    }

    public void setEnd(Long end) {
        mEnd = end;
    }

    public Long getStart() {
        return mStart;
    }

    public void setStart(Long start) {
        mStart = start;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }
}
