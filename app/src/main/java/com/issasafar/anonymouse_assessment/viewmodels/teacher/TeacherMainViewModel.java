package com.issasafar.anonymouse_assessment.viewmodels.teacher;

import android.animation.ObjectAnimator;
import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.issasafar.anonymouse_assessment.BR;
import com.issasafar.anonymouse_assessment.databinding.TeacherFragmentMainBinding;
import com.issasafar.anonymouse_assessment.generated.callback.OnClickListener;

public class TeacherMainViewModel extends BaseObservable {
 public final MutableLiveData<String> courseName = new MutableLiveData<>();
 private TeacherFragmentMainBinding fragmentMainBinding;

 @Bindable
 private String courseNameError;

    public TeacherMainViewModel(TeacherFragmentMainBinding teacherFragmentMainBinding) {
        this.fragmentMainBinding = teacherFragmentMainBinding;
        fragmentMainBinding.addButton.setOnClickListener((view)->{
            dropDownClicked();
        });
    }

    public void setFragmentMainBinding(TeacherFragmentMainBinding teacherFragmentMainBinding){
    this.fragmentMainBinding = teacherFragmentMainBinding;
}
    public String getCourseNameError() {
        return courseNameError;
    }

    public void setCourseNameError(String courseNameError) {
        this.courseNameError = courseNameError;
        notifyPropertyChanged(BR.courseNameError);
    }
    public void dropDownClicked(){
        ImageView imageView = fragmentMainBinding.dropdownIcon;
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(imageView,"rotation",imageView.getRotation() + 180f);
        rotateAnimator.setDuration(10);
        rotateAnimator.start();
        if(!rotateAnimator.isRunning()){
            imageView.setImageDrawable();
        }
    }
}