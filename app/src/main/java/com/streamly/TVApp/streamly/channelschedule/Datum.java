package com.streamly.TVApp.streamly.channelschedule;

import java.util.List;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Datum {

    @SerializedName("CallSign")
    private String mCallSign;
    @SerializedName("programs")
    private List<Program> mPrograms;
    @SerializedName("StationName")
    private String mStationName;

    @SerializedName("id")
    private String mId;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getCallSign() {
        return mCallSign;
    }

    public void setCallSign(String callSign) {
        mCallSign = callSign;
    }

    public List<Program> getPrograms() {
        return mPrograms;
    }

    public void setPrograms(List<Program> programs) {
        mPrograms = programs;
    }

    public String getStationName() {
        return mStationName;
    }

    public void setStationName(String stationName) {
        mStationName = stationName;
    }

}
