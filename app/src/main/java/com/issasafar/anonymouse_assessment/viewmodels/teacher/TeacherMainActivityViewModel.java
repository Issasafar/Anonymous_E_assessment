package com.issasafar.anonymouse_assessment.viewmodels.teacher;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.issasafar.anonymouse_assessment.databinding.ActivityTeacherMainBinding;

public class TeacherMainActivityViewModel extends BaseObservable {
    private ActivityTeacherMainBinding activityTeacherMainBinding;

    public TeacherMainActivityViewModel(ActivityTeacherMainBinding activityTeacherMainBinding) {
        this.activityTeacherMainBinding = activityTeacherMainBinding;
    }

    @Bindable
    public int getNotificationVisibility() {
        return notificationVisibility;
    }

    public void setNotificationVisibility(int notificationVisibility) {
        this.notificationVisibility = notificationVisibility;
        notifyPropertyChanged(BR.notificationCount);
    }
    @Bindable
    public String getNotificationCount() {
        return notificationCount;
    }

    public void setNotificationCount(int notificationCount) {
        this.notificationCount = String.valueOf(notificationCount);
        if (notificationCount > 0) {
            setNotificationVisibility(View.VISIBLE);
            notifyPropertyChanged(BR.notificationVisibility);
        } else {
            setNotificationVisibility(View.GONE);
            notifyPropertyChanged(BR.notificationVisibility);
        }
    }

    @Bindable
    private int notificationVisibility = View.GONE;
    @Bindable
    private String notificationCount = "";
}
