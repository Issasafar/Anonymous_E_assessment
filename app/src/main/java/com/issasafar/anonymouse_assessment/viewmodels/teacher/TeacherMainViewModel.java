package com.issasafar.anonymouse_assessment.viewmodels.teacher;

import android.animation.ObjectAnimator;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.issasafar.anonymouse_assessment.BR;
import com.issasafar.anonymouse_assessment.R;
import com.issasafar.anonymouse_assessment.databinding.TeacherFragmentMainBinding;
import com.issasafar.anonymouse_assessment.generated.callback.OnClickListener;

public class TeacherMainViewModel extends BaseObservable {
 public final MutableLiveData<String> courseName = new MutableLiveData<>();
 private TeacherFragmentMainBinding fragmentMainBinding;

 @Bindable
 private String courseNameError;

    public TeacherMainViewModel(TeacherFragmentMainBinding teacherFragmentMainBinding) {
        this.fragmentMainBinding = teacherFragmentMainBinding;
    }

    public String getCourseName(){
        return courseName.getValue();
    }
    public void setCourseName(String name){
        checkEmptyCourseName(name);
        this.courseName.setValue(name);
    }

    private void checkEmptyCourseName(@Nullable String name) {
        if(!"".equals(name)) {
            setCourseNameError(null);
        }else{
            setCourseNameError("No course name provided");
        }
        if(name == null){
            checkEmptyCourseName(fragmentMainBinding.courseNameInputLayout.getEditText().getText().toString());
        }
    }

    public String getCourseNameError() {
        return courseNameError;
    }

    public void setCourseNameError(String courseNameError) {
        this.courseNameError = courseNameError;
        notifyPropertyChanged(BR.courseNameError);
    }
    // todo() implement those methods
    public void addQuestionClicked(){
        checkEmptyCourseName(null);

    }


    public void generateTestClicked(){

    }
    public void getPrevResultsClicked(){

    }
    public void dropDownClicked(){
        ImageView imageView = fragmentMainBinding.dropdownIcon;
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(imageView,"rotation",(imageView.getRotation() + 180f) % 360f);
        rotateAnimator.setDuration(10);
        rotateAnimator.start();
    }

}