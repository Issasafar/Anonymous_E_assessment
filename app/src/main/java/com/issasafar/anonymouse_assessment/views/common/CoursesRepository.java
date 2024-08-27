package com.issasafar.anonymouse_assessment.views.common;


import com.issasafar.anonymouse_assessment.data.models.common.Course;
import com.issasafar.anonymouse_assessment.data.models.common.CourseRequest;
import com.issasafar.anonymouse_assessment.data.models.common.CourseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoursesRepository {
    private CoursesDataSource coursesDataSource;

    public CoursesRepository() {
        this.coursesDataSource = new CoursesDataSource();
    }

    public void postData(CourseRequest courseRequest, final ResultCallback<CourseResponse> callback) {
        coursesDataSource.postData(courseRequest).enqueue(new Callback<CourseResponse>() {
            @Override
            public void onResponse(Call<CourseResponse> call, Response<CourseResponse> response) {

                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Request failed"));
                }
            }

            @Override
            public void onFailure(Call<CourseResponse> call, Throwable t) {
                callback.onError(new Exception(t));
            }
        });
    }

    public void getData(CourseRequest courseRequest, final ResultCallback<CourseResponse> callback) {
        coursesDataSource.getData(courseRequest).enqueue(new Callback<CourseResponse>() {
            @Override
            public void onResponse(Call<CourseResponse> call, Response<CourseResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Request failed"));
                }
            }

            @Override
            public void onFailure(Call<CourseResponse> call, Throwable t) {
                callback.onError(new Exception(t));
            }
        });
    }
}
