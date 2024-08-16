package com.issasafar.anonymouse_assessment.views.common;

import com.issasafar.anonymouse_assessment.data.models.Result;
import com.issasafar.anonymouse_assessment.data.models.common.Test;

import retrofit2.http.POST;
import retrofit2.Call;
import retrofit2.http.Body;

public interface CoursesApiService {
    @POST("Anonymous_e_assessment/tests.php?XDEBUG_SESSION_START=PHPSTORM")
    Call<Result> createTest(@Body Test test);
}
