package com.streamly.TVApp.streamly.settings.profile.profilemodel;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Data {

    @SerializedName("address")
    private String mAddress;
    @SerializedName("contact")
    private String mContact;
    @SerializedName("country")
    private String mCountry;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("dob")
    private String mDob;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("email_verified_at")
    private String mEmailVerifiedAt;
    @SerializedName("gender")
    private Object mGender;
    @SerializedName("google_id")
    private Object mGoogleId;
    @SerializedName("id")
    private Long mId;
    @SerializedName("is_active")
    private Long mIsActive;
    @SerializedName("last_login")
    private Object mLastLogin;
    @SerializedName("name")
    private String mName;
    @SerializedName("updated_at")
    private String mUpdatedAt;
    @SerializedName("zipcode")
    private String mZipcode;

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getContact() {
        return mContact;
    }

    public void setContact(String contact) {
        mContact = contact;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getDob() {
        return mDob;
    }

    public void setDob(String dob) {
        mDob = dob;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getEmailVerifiedAt() {
        return mEmailVerifiedAt;
    }

    public void setEmailVerifiedAt(String emailVerifiedAt) {
        mEmailVerifiedAt = emailVerifiedAt;
    }

    public Object getGender() {
        return mGender;
    }

    public void setGender(Object gender) {
        mGender = gender;
    }

    public Object getGoogleId() {
        return mGoogleId;
    }

    public void setGoogleId(Object googleId) {
        mGoogleId = googleId;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public Long getIsActive() {
        return mIsActive;
    }

    public void setIsActive(Long isActive) {
        mIsActive = isActive;
    }

    public Object getLastLogin() {
        return mLastLogin;
    }

    public void setLastLogin(Object lastLogin) {
        mLastLogin = lastLogin;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public String getZipcode() {
        return mZipcode;
    }

    public void setZipcode(String zipcode) {
        mZipcode = zipcode;
    }

}
