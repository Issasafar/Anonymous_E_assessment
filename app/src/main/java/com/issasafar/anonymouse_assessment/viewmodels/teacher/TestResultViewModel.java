package com.issasafar.anonymouse_assessment.viewmodels.teacher;


import android.content.Context;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.issasafar.anonymouse_assessment.data.models.common.Course;
import com.issasafar.anonymouse_assessment.data.models.common.TestResult;
import com.issasafar.anonymouse_assessment.databinding.TestResultRecyclerItemBinding;
import com.issasafar.anonymouse_assessment.views.teacher.ShowTrackRecyclerAdapter;

public class TestResultViewModel extends BaseObservable {
    public Course getCourse() {
        return course;
    }

    public TestResult getTestResult() {
        return testResult;
    }

    private TestResult testResult;

    public void setCourse(Course course) {
        this.course = course;
        if (getAdapterType() == ShowTrackRecyclerAdapter.AdapterType.GENERAL_TEACHER) {
            setStartTextValue(course.getDescription());
        }
    }

    public void setTestResult(TestResult testResult) {
        this.testResult = testResult;
        setStartTextValue(testResult.getOwner_name());
        setScoreTextValue(String.valueOf(testResult.getScore())+"/100");
    }

    private Course course;
    private TestResultRecyclerItemBinding testResultRecyclerItemBinding;

    public void setScoreTextVisibility(int scoreTextVisibility) {
        this.scoreTextVisibility = scoreTextVisibility;
    }

    @Bindable
    private int scoreTextVisibility = View.VISIBLE;

    public void setStartTextValue(String startTextValue) {
        this.startTextValue = startTextValue;
    }

    public void setScoreTextValue(String scoreTextValue) {
        this.scoreTextValue = scoreTextValue;
    }

    @Bindable
    public String getStartTextValue() {
        return startTextValue;
    }

    @Bindable
    public int getScoreTextVisibility() {
        return scoreTextVisibility;
    }

    @Bindable
    public String getScoreTextValue() {
        return scoreTextValue;
    }

    @Bindable
    public String getLinkTextValue() {
        return linkTextValue;
    }

    public void setLinkTextValue(String linkTextValue) {
        this.linkTextValue = linkTextValue;
        notifyPropertyChanged(BR.linkTextValue);
    }

    @Bindable
    private String startTextValue;
    @Bindable
    private String scoreTextValue;
    @Bindable
    private String linkTextValue;
    private String[] linkTextValues = {"View Result","View Result", "View Answers", "View Answers"};
    private ShowTrackRecyclerAdapter.AdapterType adapterType;

    public TestResultViewModel(TestResultRecyclerItemBinding testResultRecyclerItemBinding, ShowTrackRecyclerAdapter.AdapterType adapterType) {
        setAdapterType(adapterType);
        this.testResultRecyclerItemBinding = testResultRecyclerItemBinding;
    }



    public ShowTrackRecyclerAdapter.AdapterType getAdapterType() {
        return adapterType;
    }

    public void setAdapterType(ShowTrackRecyclerAdapter.AdapterType adapterType) {
        this.adapterType = adapterType;
        if (adapterType == ShowTrackRecyclerAdapter.AdapterType.GENERAL_STUDENT || adapterType == ShowTrackRecyclerAdapter.AdapterType.GENERAL_TEACHER) {
            setScoreTextVisibility(View.GONE);
        }
        setLinkTextValue(linkTextValues[getAdapterType().getValue()]);
    }



}
