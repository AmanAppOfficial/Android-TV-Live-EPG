
package com.streamly.TVApp.streamly.log_in;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Pivot {

    @SerializedName("role_id")
    private Long mRoleId;
    @SerializedName("user_id")
    private Long mUserId;

    public Long getRoleId() {
        return mRoleId;
    }

    public void setRoleId(Long roleId) {
        mRoleId = roleId;
    }

    public Long getUserId() {
        return mUserId;
    }

    public void setUserId(Long userId) {
        mUserId = userId;
    }

}
