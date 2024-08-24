package com.issasafar.anonymouse_assessment.views.common;

import com.issasafar.anonymouse_assessment.data.models.Result;
import com.issasafar.anonymouse_assessment.data.models.common.Course;
import com.issasafar.anonymouse_assessment.data.models.common.CourseRequest;

import retrofit2.Call;
import retrofit2.Retrofit;

public class CoursesDataSource {
    private CoursesApiService apiService;
    public CoursesDataSource(){
        Retrofit retrofit = CoursesApiClient.getClient();
        apiService = retrofit.create(CoursesApiService.class);
    }

    public Call<Result> createTest(CourseRequest<Course> course){
       return apiService.createTest(course);
    }

    public void setApiService(CoursesApiService coursesApiService) {
        this.apiService = coursesApiService;
    }
}
