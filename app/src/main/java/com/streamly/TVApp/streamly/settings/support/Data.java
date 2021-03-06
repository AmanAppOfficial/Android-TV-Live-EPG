package com.streamly.TVApp.streamly.settings.support;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Data {

    @SerializedName("email")
    private String mEmail;
    @SerializedName("num1")
    private String mNum1;
    @SerializedName("num2")
    private String mNum2;

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getNum1() {
        return mNum1;
    }

    public void setNum1(String num1) {
        mNum1 = num1;
    }

    public String getNum2() {
        return mNum2;
    }

    public void setNum2(String num2) {
        mNum2 = num2;
    }

}
