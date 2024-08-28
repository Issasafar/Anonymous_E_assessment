package com.issasafar.anonymouse_assessment.viewmodels.teacher;

import android.animation.ObjectAnimator;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.issasafar.anonymouse_assessment.BR;
import com.issasafar.anonymouse_assessment.R;
import com.issasafar.anonymouse_assessment.data.models.common.Course;
import com.issasafar.anonymouse_assessment.databinding.TeacherFragmentMainBinding;
import com.issasafar.anonymouse_assessment.generated.callback.OnClickListener;
import com.issasafar.anonymouse_assessment.views.teacher.ui.main.teacher.AddQuestionsFragment;
import com.issasafar.anonymouse_assessment.views.teacher.ui.main.teacher.ShowTrackFragment;

public class TeacherMainViewModel extends BaseObservable {
    private FragmentManager fragmentManager;
    public final MutableLiveData<String> courseName = new MutableLiveData<>();
    private TeacherFragmentMainBinding fragmentMainBinding;

    @Bindable
    private String courseNameError;

    public TeacherMainViewModel(TeacherFragmentMainBinding teacherFragmentMainBinding, FragmentManager fragmentManager) {
        this.fragmentMainBinding = teacherFragmentMainBinding;
        this.fragmentManager = fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public String getCourseName() {
        return courseName.getValue();
    }

    public void setCourseName(String name) {
        checkEmptyCourseName(name);
        this.courseName.setValue(name);
    }

    private void checkEmptyCourseName(@Nullable String name) {
        if (!"".equals(name)) {
            setCourseNameError(null);
        } else {
            setCourseNameError("No course name provided");
        }
        if (name == null) {
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
    public void addQuestionClicked() {
        checkEmptyCourseName(null);
        //TODO() use bundle to send the things
        if (getCourseNameError() == null) {
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.teacher_fragment_container, AddQuestionsFragment.newInstance())
                    .commit();
        }
    }


    public void generateTestClicked() {
    }

    public void getPrevResultsClicked() {
        checkEmptyCourseName(null);
        //TODO() use bundle to send the things
        if (getCourseNameError() == null) {
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.teacher_fragment_container, ShowTrackFragment.newInstance())
                    .commit();
        }

    }

    public void dropDownClicked() {
        ImageView imageView = fragmentMainBinding.dropdownIcon;
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(imageView, "rotation", (imageView.getRotation() + 180f) % 360f);
        rotateAnimator.setDuration(10);
        rotateAnimator.start();
    }

}