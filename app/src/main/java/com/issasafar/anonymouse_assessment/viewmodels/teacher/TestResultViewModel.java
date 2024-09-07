package com.issasafar.anonymouse_assessment.viewmodels.teacher;


import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.issasafar.anonymouse_assessment.data.models.common.Course;
import com.issasafar.anonymouse_assessment.data.models.common.TestResult;
import com.issasafar.anonymouse_assessment.databinding.TestResultRecyclerItemBinding;
import com.issasafar.anonymouse_assessment.views.teacher.ShowTrackRecyclerAdapter;

public class TestResultViewModel extends BaseObservable {
    private TestResult testResult;
    private Course course;
    private TestResultRecyclerItemBinding testResultRecyclerItemBinding;
    @Bindable
    private int scoreTextVisibility = View.VISIBLE;
    @Bindable
    private String startTextValue;
    @Bindable
    private String scoreTextValue;
    @Bindable
    private String linkTextValue;
    private String[] linkTextValues = {"View Result", "View Result", "View Answers", "View Answers"};
    private ShowTrackRecyclerAdapter.AdapterType adapterType;

    public TestResultViewModel(TestResultRecyclerItemBinding testResultRecyclerItemBinding, ShowTrackRecyclerAdapter.AdapterType adapterType) {
        setAdapterType(adapterType);
        this.testResultRecyclerItemBinding = testResultRecyclerItemBinding;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
        if (getAdapterType() == ShowTrackRecyclerAdapter.AdapterType.GENERAL_TEACHER || getAdapterType() == ShowTrackRecyclerAdapter.AdapterType.GENERAL_STUDENT) {
            setStartTextValue(course.getDescription());
        }
    }

    public TestResult getTestResult() {
        return testResult;
    }

    public void setTestResult(TestResult testResult) {
        this.testResult = testResult;
        setStartTextValue(testResult.getOwner_name());
        setScoreTextValue(testResult.getScore() + "/100");
    }

    public void setTestResultForStudentView(TestResult testResult) {
        this.testResult = testResult;
        setScoreTextValue(testResult.getScore() + "/100");
    }

    @Bindable
    public String getStartTextValue() {
        return startTextValue;
    }

    public void setStartTextValue(String startTextValue) {
        this.startTextValue = startTextValue;
        notifyPropertyChanged(BR.startTextValue);
    }

    @Bindable
    public int getScoreTextVisibility() {
        return scoreTextVisibility;
    }

    public void setScoreTextVisibility(int scoreTextVisibility) {
        this.scoreTextVisibility = scoreTextVisibility;
        notifyPropertyChanged(BR.scoreTextVisibility);
    }

    @Bindable
    public String getScoreTextValue() {
        return scoreTextValue;
    }

    public void setScoreTextValue(String scoreTextValue) {
        this.scoreTextValue = scoreTextValue;
        notifyPropertyChanged(BR.scoreTextValue);
    }

    @Bindable
    public String getLinkTextValue() {
        return linkTextValue;
    }

    public void setLinkTextValue(String linkTextValue) {
        this.linkTextValue = linkTextValue;
        notifyPropertyChanged(BR.linkTextValue);
    }

    public ShowTrackRecyclerAdapter.AdapterType getAdapterType() {
        return adapterType;
    }

    public void setAdapterType(ShowTrackRecyclerAdapter.AdapterType adapterType) {
        this.adapterType = adapterType;
        if (adapterType == ShowTrackRecyclerAdapter.AdapterType.GENERAL_STUDENT || adapterType == ShowTrackRecyclerAdapter.AdapterType.GENERAL_TEACHER) {
            setScoreTextVisibility(View.GONE);
        } else {
            setScoreTextVisibility(View.VISIBLE);
        }
        setLinkTextValue(linkTextValues[getAdapterType().getValue()]);
    }


}
