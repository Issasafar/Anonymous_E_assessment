package com.issasafar.anonymouse_assessment.viewmodels.common;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.issasafar.anonymouse_assessment.data.models.Result;
import com.issasafar.anonymouse_assessment.data.models.common.Course;
import com.issasafar.anonymouse_assessment.data.models.common.CourseRequest;
import com.issasafar.anonymouse_assessment.views.common.CoursesRepository;
import com.issasafar.anonymouse_assessment.views.common.ResultCallback;

public class CoursesViewModel extends ViewModel {
    private CoursesRepository coursesRepository;
    public CoursesViewModel(){
        coursesRepository = new CoursesRepository();
    }
    public void createTest(CourseRequest<Course> course){
        coursesRepository.createTest(course, new ResultCallback<Result>() {
            @Override
            public void onSuccess(Result data) {
                Log.d("CoursesViewModel", "onSuccess: " + data.toString());
            }

            @Override
            public void onError(Exception e) {
                Log.d("CoursesViewModel", "onError: " + e.getMessage());
            }
        });

    }
}
