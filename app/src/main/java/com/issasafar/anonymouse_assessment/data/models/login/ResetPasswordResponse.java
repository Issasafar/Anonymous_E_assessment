package com.issasafar.anonymouse_assessment.data.models.login;

import com.google.gson.annotations.SerializedName;

public class ResetPasswordResponse {
    public String getMessage() {
        return this.message;
    }

    @SerializedName("message")
    private String message;
 @SerializedName("success")
    private Boolean success;

    public Boolean getSuccess() {
        return this.success;
    }
}
