package com.streamly.TVApp.streamly;

import com.google.gson.annotations.SerializedName;

import org.threeten.bp.Instant;

public class HomeBean {

    @SerializedName("CallSign")
    public String headerTitle;
    @SerializedName("end")
    public Instant end_time;
    @SerializedName("start")
    public Instant start_time;
    @SerializedName("title")
    public String title;

    @SerializedName("description")
    public String description;

    public HomeBean() {
    }

    public HomeBean(String headerTitle, Instant end_time, Instant start_time, String title, String description) {
        this.headerTitle = headerTitle;
        this.end_time = end_time;
        this.start_time = start_time;
        this.title = title;
        this.description=description;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public Instant getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Instant end_time) {
        this.end_time = end_time;
    }

    public Instant getStart_time() {
        return start_time;
    }

    public void setStart_time(Instant start_time) {
        this.start_time = start_time;
    }

/*  public Long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Long end_time) {
        this.end_time = end_time;
    }

    public Long getStart_time() {
        return start_time;
    }

    public void setStart_time(Long start_time) {
        this.start_time = start_time;
    }*/

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
