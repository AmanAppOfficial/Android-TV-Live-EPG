
package com.streamly.TVApp.streamly.log_in;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class User {

    @SerializedName("address")
    private String mAddress;
    @SerializedName("avatar")
    private Object mAvatar;
    @SerializedName("card_brand")
    private String mCardBrand;
    @SerializedName("card_last_four")
    private String mCardLastFour;
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
    @SerializedName("provider_id")
    private Object mProviderId;
    @SerializedName("provider_name")
    private Object mProviderName;
    @SerializedName("roles")
    private List<Role> mRoles;
    @SerializedName("stripe_id")
    private String mStripeId;
    @SerializedName("trial_ends_at")
    private Object mTrialEndsAt;
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

    public Object getAvatar() {
        return mAvatar;
    }

    public void setAvatar(Object avatar) {
        mAvatar = avatar;
    }

    public String getCardBrand() {
        return mCardBrand;
    }

    public void setCardBrand(String cardBrand) {
        mCardBrand = cardBrand;
    }

    public String getCardLastFour() {
        return mCardLastFour;
    }

    public void setCardLastFour(String cardLastFour) {
        mCardLastFour = cardLastFour;
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

    public Object getProviderId() {
        return mProviderId;
    }

    public void setProviderId(Object providerId) {
        mProviderId = providerId;
    }

    public Object getProviderName() {
        return mProviderName;
    }

    public void setProviderName(Object providerName) {
        mProviderName = providerName;
    }

    public List<Role> getRoles() {
        return mRoles;
    }

    public void setRoles(List<Role> roles) {
        mRoles = roles;
    }

    public String getStripeId() {
        return mStripeId;
    }

    public void setStripeId(String stripeId) {
        mStripeId = stripeId;
    }

    public Object getTrialEndsAt() {
        return mTrialEndsAt;
    }

    public void setTrialEndsAt(Object trialEndsAt) {
        mTrialEndsAt = trialEndsAt;
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
