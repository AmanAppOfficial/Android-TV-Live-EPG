package com.streamly.TVApp.streamly.search_channels;

public class SearchModel {
    String channelTitle;
    String logoUrl;
    String videoUrl;


    public SearchModel() {
    }

    public SearchModel(String channelTitle, String logoUrl) {
        this.channelTitle = channelTitle;
        this.logoUrl = logoUrl;
    }

    public SearchModel(String channelTitle, String logoUrl, String videoUrl) {
        this.channelTitle = channelTitle;
        this.logoUrl = logoUrl;
        this.videoUrl = videoUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
