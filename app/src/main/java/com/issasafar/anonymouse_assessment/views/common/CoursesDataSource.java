package com.issasafar.anonymouse_assessment.views.common;

import com.issasafar.anonymouse_assessment.data.models.common.Course;
import com.issasafar.anonymouse_assessment.data.models.common.CourseRequest;
import com.issasafar.anonymouse_assessment.data.models.common.CourseResponse;

import retrofit2.Call;
import retrofit2.Retrofit;

public class CoursesDataSource {
    private CoursesApiService apiService;
    public CoursesDataSource(){
        Retrofit retrofit = CoursesApiClient.getClient();
        apiService = retrofit.create(CoursesApiService.class);
    }

    public Call<CourseResponse> postData(CourseRequest courseRequest){
       return apiService.postData(courseRequest);
    }

    public Call<CourseResponse> getData(CourseRequest courseRequest) {
        return apiService.getData(courseRequest);
    }

    public void setApiService(CoursesApiService coursesApiService) {
        this.apiService = coursesApiService;
    }
}
