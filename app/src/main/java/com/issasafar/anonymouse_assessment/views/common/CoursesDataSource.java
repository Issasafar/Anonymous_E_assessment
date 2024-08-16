package com.issasafar.anonymouse_assessment.views.common;

import com.issasafar.anonymouse_assessment.data.models.Result;
import com.issasafar.anonymouse_assessment.data.models.common.Test;

import retrofit2.Call;
import retrofit2.Retrofit;

public class CoursesDataSource {
    private CoursesApiService apiService;
    public CoursesDataSource(){
        Retrofit retrofit = CoursesApiClient.getClient();
        apiService = retrofit.create(CoursesApiService.class);
    }
    public Call<Result> createTest(Test test){
       return apiService.createTest(test);
    }
}
