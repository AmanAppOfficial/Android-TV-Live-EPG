package com.streamly.TVApp.streamly;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VerticalHomeBean {

    @SerializedName("callSign")
    public String headerTitle;
    List<HomeBean> allItemsInSection;


    public VerticalHomeBean() {
    }

    public VerticalHomeBean(String headerTitle) {
        this.headerTitle = headerTitle;
    }


    public List<HomeBean> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(List<HomeBean> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }
}
