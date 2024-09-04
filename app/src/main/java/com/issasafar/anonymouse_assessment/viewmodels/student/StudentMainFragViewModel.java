package com.issasafar.anonymouse_assessment.viewmodels.student;

import android.os.Bundle;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.issasafar.anonymouse_assessment.R;
import com.issasafar.anonymouse_assessment.data.models.common.Course;
import com.issasafar.anonymouse_assessment.databinding.FragmentStudentMainBinding;
import com.issasafar.anonymouse_assessment.views.student.ui.main.student.SendLackMessageFragment;

import java.util.ArrayList;

public class StudentMainFragViewModel extends BaseObservable {
    public static String SELECTED_COURSE_KEY = "selected course key";
    private FragmentStudentMainBinding fragmentStudentMainBinding;
    private FragmentManager fragmentManager;
    private String courseName = "";

    public ArrayList<Course> getCourses() {
        return courses;
    }

    private ArrayList<Course> courses = new ArrayList<>();
    private Course selected = null;
    public StudentMainFragViewModel(FragmentStudentMainBinding fragmentStudentMainBinding, FragmentManager fragmentManager) {
        this.fragmentStudentMainBinding = fragmentStudentMainBinding;
        this.fragmentManager = fragmentManager;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public Course getSelected() {
        if (!"".equals(getCourseName()) && courses.stream().anyMatch(item -> item.getDescription().equals(getCourseName()))) {
            setSelected(courses.stream().filter(item -> item.getDescription().equals(getCourseName())).findFirst().get());
        } else {
            setSelected(null);
        }
        return selected;
    }

    public void setSelected(Course selected) {
        this.selected = selected;
    }

    @Bindable
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void getQuestionsClicked() {
        if (getSelected() == null) {
            Snackbar.make(fragmentStudentMainBinding.getRoot(), "Please select an existing course", Snackbar.LENGTH_SHORT).show();
        } else {

        }
    }

    public void getResultsClicked() {

    }

    public void sendLackMessageClicked() {
        if (getSelected() == null) {
            Snackbar.make(fragmentStudentMainBinding.getRoot(), "Please select an existing course", Snackbar.LENGTH_SHORT).show();
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(SELECTED_COURSE_KEY,new Gson().toJson(getSelected()));
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack("send_frag")
                    .replace(R.id.student_main_fragment_container, SendLackMessageFragment.class,bundle)
                    .commit();
        }
    }


}