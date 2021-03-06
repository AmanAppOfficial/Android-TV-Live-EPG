
package com.streamly.TVApp.streamly.log_in;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Role {

    @SerializedName("created_at")
    private Object mCreatedAt;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("display_name")
    private String mDisplayName;
    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("pivot")
    private Pivot mPivot;
    @SerializedName("updated_at")
    private Object mUpdatedAt;

    public Object getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Object createdAt) {
        mCreatedAt = createdAt;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Pivot getPivot() {
        return mPivot;
    }

    public void setPivot(Pivot pivot) {
        mPivot = pivot;
    }

    public Object getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        mUpdatedAt = updatedAt;
    }

}
