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
        Log.d("mainteacher act","drop down clicked");
        ImageView imageView = fragmentMainBinding.dropdownIcon;
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(imageView,"rotation",190f);
        rotateAnimator.setDuration(10);
        rotateAnimator.start();
        fragmentMainBinding.courseNameInputLayout.getEditText().setText("hhhh");
    }
}