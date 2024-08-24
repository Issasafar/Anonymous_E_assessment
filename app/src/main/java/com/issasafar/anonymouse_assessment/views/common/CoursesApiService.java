package com.issasafar.anonymouse_assessment.views.common;

import com.issasafar.anonymouse_assessment.data.models.Result;
import com.issasafar.anonymouse_assessment.data.models.common.Course;
import com.issasafar.anonymouse_assessment.data.models.common.CourseRequest;

import retrofit2.http.POST;
import retrofit2.Call;
import retrofit2.http.Body;

public interface CoursesApiService {
    @POST("Anonymous_e_assessment/courses/course_service.php?XDEBUG_SESSION_START=PHPSTORM")
    Call<Result> createTest(@Body CourseRequest<Course> course);
}
