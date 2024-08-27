package com.issasafar.anonymouse_assessment.data.models.common;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class CourseResponse<T> {
    private String message;
    private int success;
    private T data;

    public CourseResponse(String message, int success, @Nullable T data) {
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

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData( T data) {
        this.data = data;
    }
}
