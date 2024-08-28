package com.issasafar.anonymouse_assessment.data.models.common;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class CourseResponse<T> {
    private String message;
    private boolean success;
    private T data;

    public CourseResponse(String message, boolean success, @Nullable T data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData( T data) {
        this.data = data;
    }
}
