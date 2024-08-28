package com.issasafar.anonymouse_assessment.views.common;

import com.issasafar.anonymouse_assessment.data.models.common.Course;
import com.issasafar.anonymouse_assessment.data.models.common.CourseRequest;
import com.issasafar.anonymouse_assessment.data.models.common.CourseResponse;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Query;

public interface CoursesApiService {
    @POST("Anonymous_e_assessment/courses/course_service.php?XDEBUG_SESSION_START=PHPSTORM")
    Call<CourseResponse> postData(@Body CourseRequest course);
    @POST("Anonymous_e_assessment/courses/course_service.php?XDEBUG_SESSION_START=PHPSTORM")
    Call<CourseResponse> getData(@Body CourseRequest courseRequest);
}
