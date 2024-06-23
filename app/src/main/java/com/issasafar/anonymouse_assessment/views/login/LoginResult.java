package com.issasafar.anonymouse_assessment.views.login;

import androidx.annotation.Nullable;

import com.issasafar.anonymouse_assessment.data.models.login.LoggedInUser;

/**
 * Authentication result : success (user details) or error message.
 */
public class LoginResult {
    @Nullable
    private LoggedInUser success;
    @Nullable
    private String error;

    public LoginResult(@Nullable String error) {
        this.error = error;
    }

    public LoginResult(@Nullable LoggedInUser success) {
        this.success = success;
    }

    @Nullable
    public LoggedInUser getSuccess() {
        return success;
    }

    @Nullable
    public String getError() {
        return error;
    }
}