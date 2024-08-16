package com.issasafar.anonymouse_assessment.views.common;


import com.issasafar.anonymouse_assessment.data.models.Result;
import com.issasafar.anonymouse_assessment.data.models.common.Test;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoursesRepository {
    private CoursesDataSource coursesDataSource;
    public CoursesRepository(){
        this.coursesDataSource = new CoursesDataSource();
    }
    public void createTest(Test test, final ResultCallback<Result> callback){
        coursesDataSource.createTest(test).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful()){
                    callback.onSuccess(response.body());
                }else{
                    callback.onError(new Exception("Request failed"));
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                callback.onError(new Exception(t));
            }
        });
    }
}
