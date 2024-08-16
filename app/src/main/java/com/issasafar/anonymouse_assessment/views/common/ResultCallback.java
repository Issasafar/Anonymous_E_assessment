package com.issasafar.anonymouse_assessment.views.common;

public interface ResultCallback<T>{
    void onSuccess(T data);
    void onError(Exception e);
}
