package com.issasafar.anonymouse_assessment.viewmodels.student;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.google.android.material.snackbar.Snackbar;
import com.issasafar.anonymouse_assessment.BR;
import com.issasafar.anonymouse_assessment.data.models.common.CourseRequest;
import com.issasafar.anonymouse_assessment.data.models.common.CourseResponse;
import com.issasafar.anonymouse_assessment.data.models.common.TestResult;
import com.issasafar.anonymouse_assessment.views.common.CoursesRepository;
import com.issasafar.anonymouse_assessment.views.common.ResultCallback;
import com.issasafar.anonymouse_assessment.views.student.StudentQuestionRecyclerViewAdapter;


public class StudentQuestionsViewModel extends BaseObservable {
    @Bindable
    private TestResult testResult = null;
    @Bindable
    private StudentQuestionRecyclerViewAdapter questionRecyclerViewAdapter;
    private CoursesRepository coursesRepository;
    private View snackBarView;

    public StudentQuestionsViewModel(StudentQuestionRecyclerViewAdapter questionRecyclerViewAdapter, CoursesRepository coursesRepository, View snackBarView) {
        this.questionRecyclerViewAdapter = questionRecyclerViewAdapter;
        this.coursesRepository = coursesRepository;
        this.snackBarView = snackBarView;
    }

    public CoursesRepository getCoursesRepository() {
        return coursesRepository;
    }

    public View getSnackBarView() {
        return snackBarView;
    }

    @Bindable
    public TestResult getTestResult() {
        return testResult;
    }

    public void setTestResult(TestResult testResult) {
        this.testResult = testResult;
        notifyPropertyChanged(BR.testResult);
    }

    @Bindable
    public StudentQuestionRecyclerViewAdapter getQuestionRecyclerViewAdapter() {
        return questionRecyclerViewAdapter;
    }

    public void setQuestionRecyclerViewAdapter(StudentQuestionRecyclerViewAdapter questionRecyclerViewAdapter) {
        this.questionRecyclerViewAdapter = questionRecyclerViewAdapter;
        notifyPropertyChanged(BR.questionRecyclerViewAdapter);
    }

    public void sendAnswersClicked() {
        getCoursesRepository().postData(new CourseRequest(CourseRequest.CourseAction.POST_RESULT, questionRecyclerViewAdapter.getResult()), new ResultCallback<CourseResponse>() {
            @Override
            public void onSuccess(CourseResponse data) {
                if (data.getSuccess()) {
                    setTestResult(questionRecyclerViewAdapter.getResult());
                } else {
                    setTestResult(null);
                    Snackbar.make(snackBarView, "Unable to post message (Connection invalid request)", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Exception e) {
                showSnackBarError(e);
                setTestResult(null);
            }
        });
        getCoursesRepository().postData(new CourseRequest(CourseRequest.CourseAction.POST_ANSWERS, questionRecyclerViewAdapter.getAnswers()), new ResultCallback<CourseResponse>() {
            @Override
            public void onSuccess(CourseResponse data) {
                if (data.getSuccess()) {
                    setTestResult(questionRecyclerViewAdapter.getResult());
                } else {
                    Snackbar.make(snackBarView, "Unable to post message (Connection invalid request)", Snackbar.LENGTH_SHORT).show();
                    setTestResult(null);
                }
            }
            @Override
            public void onError(Exception e) {
                showSnackBarError(e);
                setTestResult(null);
            }
        });
    }


    private void showSnackBarError(Exception e) {
        if (e.getCause().toString().contains("java.net.ConnectException")) {
            Snackbar.make(snackBarView, "Unable to post message (Connection error)", Snackbar.LENGTH_SHORT).show();
        } else if (e.getCause().toString().contains("java.net.SocketTimeoutException")) {
            Snackbar.make(snackBarView, "Unable to connect to the server", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(snackBarView, "Unknown error when connecting to the server", Snackbar.LENGTH_SHORT).show();
        }
    }
}