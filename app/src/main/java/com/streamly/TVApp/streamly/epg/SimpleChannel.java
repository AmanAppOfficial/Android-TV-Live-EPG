package com.streamly.TVApp.streamly.epg;

import android.text.Spanned;
import com.streamly.TVApp.streamly.entity.ProgramGuideChannel;

public class SimpleChannel implements ProgramGuideChannel {
     String  id;
    Spanned  name;
    String imageUrl;
    String callSign;

    public SimpleChannel() {
    }

    public SimpleChannel(String id, String callSign, Spanned name, String imageUrl) {
        this.id = id;
        this.callSign = callSign;
        this.name = name;
        this.imageUrl = imageUrl;

    }

    public String getCallSign() {
        return callSign;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Spanned getName() {
        return name;
    }

    public void setName(Spanned name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
