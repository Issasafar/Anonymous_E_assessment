package com.issasafar.anonymouse_assessment.views.login;

import com.issasafar.anonymouse_assessment.data.models.Result;

 public interface RepositoryCallBack<T> {
    void onComplete(Result<T> result);
}