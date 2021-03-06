
package com.streamly.TVApp.streamly.epg.channelgenre;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Data {

    @SerializedName("Comedy")
    private List<Comedy> mComedy;
    @SerializedName("Family")
    private List<Family> mFamily;
    @SerializedName("Kids")
    private List<Kid> mKids;
    @SerializedName("Movies")
    private List<Movie> mMovies;
    @SerializedName("News")
    private List<News> mNews;
    @SerializedName("Others")
    private List<Other> mOthers;
    @SerializedName("Sports")
    private List<Sport> mSports;

    public List<Comedy> getComedy() {
        return mComedy;
    }

    public void setComedy(List<Comedy> comedy) {
        mComedy = comedy;
    }

    public List<Family> getFamily() {
        return mFamily;
    }

    public void setFamily(List<Family> family) {
        mFamily = family;
    }

    public List<Kid> getKids() {
        return mKids;
    }

    public void setKids(List<Kid> kids) {
        mKids = kids;
    }

    public List<Movie> getMovies() {
        return mMovies;
    }

    public void setMovies(List<Movie> movies) {
        mMovies = movies;
    }

    public List<News> getNews() {
        return mNews;
    }

    public void setNews(List<News> news) {
        mNews = news;
    }

    public List<Other> getOthers() {
        return mOthers;
    }

    public void setOthers(List<Other> others) {
        mOthers = others;
    }

    public List<Sport> getSports() {
        return mSports;
    }

    public void setSports(List<Sport> sports) {
        mSports = sports;
    }

}
