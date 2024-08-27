package com.issasafar.anonymouse_assessment.viewmodels.common;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.issasafar.anonymouse_assessment.data.models.common.Course;
import com.issasafar.anonymouse_assessment.data.models.common.CourseRequest;
import com.issasafar.anonymouse_assessment.data.models.common.CourseResponse;
import com.issasafar.anonymouse_assessment.views.common.CoursesRepository;
import com.issasafar.anonymouse_assessment.views.common.ResultCallback;

public class CoursesViewModel extends ViewModel {
    private CoursesRepository coursesRepository;
    public CoursesViewModel(){
        coursesRepository = new CoursesRepository();
    }
    public void postData(CourseRequest<Course> course){
        coursesRepository.postData(course, new ResultCallback<CourseResponse>() {
            @Override
            public void onSuccess(CourseResponse  data) {
                Log.d("CoursesViewModel", "onSuccess: " + data.toString());
            }

            @Override
            public void onError(Exception e) {
                Log.d("CoursesViewModel", "onError: " + e.getMessage());
            }
        });

    }
}
