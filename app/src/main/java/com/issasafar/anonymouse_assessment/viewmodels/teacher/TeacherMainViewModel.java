package com.issasafar.anonymouse_assessment.viewmodels.teacher;

import static com.issasafar.anonymouse_assessment.viewmodels.teacher.AddQuestionsViewModel.QUESTIONS_KEY;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.issasafar.anonymouse_assessment.BR;
import com.issasafar.anonymouse_assessment.R;
import com.issasafar.anonymouse_assessment.data.models.common.Course;
import com.issasafar.anonymouse_assessment.data.models.common.Question;
import com.issasafar.anonymouse_assessment.databinding.TeacherFragmentMainBinding;
import com.issasafar.anonymouse_assessment.generated.callback.OnClickListener;
import com.issasafar.anonymouse_assessment.views.login.LoginViewModel;
import com.issasafar.anonymouse_assessment.views.teacher.ui.main.teacher.AddQuestionsFragment;
import com.issasafar.anonymouse_assessment.views.teacher.ui.main.teacher.ShowTrackFragment;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TeacherMainViewModel extends BaseObservable {
    private Context appContext;
    private FragmentManager fragmentManager;
    public final MutableLiveData<String> courseName = new MutableLiveData<>();
    private TeacherFragmentMainBinding fragmentMainBinding;
    private ArrayList<Question> questions = new ArrayList<>();
    private String questionsJson;

    public Course getTargetCourse() {
        return targetCourse;
    }

    public void setTargetCourse(Course targetCourse) {
        this.targetCourse = targetCourse;
    }

    private Course targetCourse;
    public static final Type questionsArrayType = new TypeToken<ArrayList<Question>>(){}.getType();

    @Bindable
    private String courseNameError;

    public TeacherMainViewModel(TeacherFragmentMainBinding teacherFragmentMainBinding, FragmentManager fragmentManager,Context appContext) {
        this.fragmentMainBinding = teacherFragmentMainBinding;
        this.fragmentManager = fragmentManager;
        this.appContext = appContext;
    }

    public void setQuestionsJson(String json) {
        this.questionsJson = json;
    }
    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public ArrayList<Question> getQuestions() {
        if (questions.isEmpty() && questionsJson != null) {
            questions = new Gson().fromJson(questionsJson,questionsArrayType);
        }
        return questions;
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

            Bundle bundle = new Bundle();
            bundle.putString(QUESTIONS_KEY,questionsJson);
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.teacher_fragment_container, AddQuestionsFragment.class,bundle)
                    .addToBackStack("add_questions_frag")
                    .commit();
        }
    }


    public void generateTestClicked() {
        if(getTargetCourse() == null){
            targetCourse = new Course(Integer.parseInt(LoginViewModel.getUserId(appContext)),getCourseName());
        }
        //todo()
        // post data to the api
    }

    public void getPrevResultsClicked() {
        checkEmptyCourseName(null);
        //TODO() use bundle to send the things
        if (getCourseNameError() == null) {
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.teacher_fragment_container, ShowTrackFragment.newInstance())
                    .addToBackStack("show_track_frag")
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