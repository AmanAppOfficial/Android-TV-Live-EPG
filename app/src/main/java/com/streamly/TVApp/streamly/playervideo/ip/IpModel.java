package com.streamly.TVApp.streamly.playervideo.ip;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class IpModel {

    @SerializedName("as")
    private String mAs;
    @SerializedName("city")
    private String mCity;
    @SerializedName("country")
    private String mCountry;
    @SerializedName("countryCode")
    private String mCountryCode;
    @SerializedName("isp")
    private String mIsp;
    @SerializedName("lat")
    private Double mLat;
    @SerializedName("lon")
    private Double mLon;
    @SerializedName("org")
    private String mOrg;
    @SerializedName("query")
    private String mQuery;
    @SerializedName("region")
    private String mRegion;
    @SerializedName("regionName")
    private String mRegionName;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("timezone")
    private String mTimezone;
    @SerializedName("zip")
    private String mZip;
    @SerializedName("message")
    private String mMessage;

    public String getAs() {
        return mAs;
    }

    public void setAs(String as) {
        mAs = as;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public String getCountryCode() {
        return mCountryCode;
    }

    public void setCountryCode(String countryCode) {
        mCountryCode = countryCode;
    }

    public String getIsp() {
        return mIsp;
    }

    public void setIsp(String isp) {
        mIsp = isp;
    }

    public Double getLat() {
        return mLat;
    }

    public void setLat(Double lat) {
        mLat = lat;
    }

    public Double getLon() {
        return mLon;
    }

    public void setLon(Double lon) {
        mLon = lon;
    }

    public String getOrg() {
        return mOrg;
    }

    public void setOrg(String org) {
        mOrg = org;
    }

    public String getQuery() {
        return mQuery;
    }

    public void setQuery(String query) {
        mQuery = query;
    }

    public String getRegion() {
        return mRegion;
    }

    public void setRegion(String region) {
        mRegion = region;
    }

    public String getRegionName() {
        return mRegionName;
    }

    public void setRegionName(String regionName) {
        mRegionName = regionName;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getTimezone() {
        return mTimezone;
    }

    public void setTimezone(String timezone) {
        mTimezone = timezone;
    }

    public String getZip() {
        return mZip;
    }

    public void setZip(String zip) {
        mZip = zip;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}
