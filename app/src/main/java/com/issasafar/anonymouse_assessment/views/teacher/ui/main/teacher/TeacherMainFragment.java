package com.issasafar.anonymouse_assessment.views.teacher.ui.main.teacher;


import static com.issasafar.anonymouse_assessment.views.teacher.TeacherMainActivity.COURSES_KEY;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.issasafar.anonymouse_assessment.R;
import com.issasafar.anonymouse_assessment.data.models.common.Course;
import com.issasafar.anonymouse_assessment.data.models.common.CourseResponse;
import com.issasafar.anonymouse_assessment.databinding.TeacherFragmentMainBinding;
import com.issasafar.anonymouse_assessment.viewmodels.teacher.TeacherMainViewModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


public class TeacherMainFragment extends Fragment {

    private TeacherMainViewModel mViewModel;
    private ArrayList<Course> courses;
    public static final String TARGET_COURSE_KEY = "targetCourse";
    private TeacherFragmentMainBinding teacherFragmentMainBinding;

    public static TeacherMainFragment newInstance() {
        return new TeacherMainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        teacherFragmentMainBinding = TeacherFragmentMainBinding.inflate(getLayoutInflater());
        teacherFragmentMainBinding.executePendingBindings();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        mViewModel = new TeacherMainViewModel(teacherFragmentMainBinding, fragmentManager);
        teacherFragmentMainBinding.setTeacherMainFragment(mViewModel);
        if (getArguments() != null) {
            teacherFragmentMainBinding.courseNameSpinner.setEnabled(true);
            Bundle coursesBundle = getArguments();
            Type courseResponseType = new TypeToken<CourseResponse<ArrayList<Course>>>() {
            }.getType();
            CourseResponse<ArrayList<Course>> courseResponse = new Gson().fromJson(coursesBundle.getString(COURSES_KEY), courseResponseType);
            courses = courseResponse.getData();
            List<String> availableCourses = courses.stream().map(Course::getDescription).collect(Collectors.toList());
            Collections.reverse(availableCourses);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, availableCourses);
            teacherFragmentMainBinding.courseNameSpinner.setAdapter(adapter);
            teacherFragmentMainBinding.courseNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                    String courseName = adapterView.getItemAtPosition(position).toString();
                    Objects.requireNonNull(teacherFragmentMainBinding.courseNameInputLayout.getEditText()).setText(courseName);
                    Optional<Course> courseSearch = courses
                            .stream()
                            .filter(item->item.getDescription().equals(courseName))
                            .findFirst();
                    if(courseSearch.isPresent()){
                        Course  targetCourse = courseSearch.get();
                        String courseJson = new Gson().toJson(targetCourse);
                        Bundle courseBundle = new Bundle();
                        courseBundle.putString(TARGET_COURSE_KEY,courseJson);
                        getParentFragmentManager().setFragmentResult(TARGET_COURSE_KEY,courseBundle);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    Objects.requireNonNull(teacherFragmentMainBinding.courseNameInputLayout.getEditText()).setText("");
                }
            });
        } else {
            teacherFragmentMainBinding.courseNameSpinner.setEnabled(false);
        }
        // TODO: Use the ViewModel
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return this.teacherFragmentMainBinding.getRoot();
    }
}