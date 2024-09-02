package com.issasafar.anonymouse_assessment.views.teacher.ui.main.teacher;


import static com.issasafar.anonymouse_assessment.viewmodels.teacher.AddQuestionsViewModel.QUESTIONS_KEY;
import static com.issasafar.anonymouse_assessment.views.teacher.TeacherMainActivity.COURSES_KEY;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.issasafar.anonymouse_assessment.data.models.common.Course;
import com.issasafar.anonymouse_assessment.data.models.common.CourseResponse;
import com.issasafar.anonymouse_assessment.databinding.TeacherFragmentMainBinding;
import com.issasafar.anonymouse_assessment.viewmodels.teacher.TeacherMainViewModel;
import com.issasafar.anonymouse_assessment.data.models.common.QuestionDeserializer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class TeacherMainFragment extends Fragment {

    private TeacherMainViewModel teacherMainViewModel;
    private ArrayList<Course> courses;
    public static final String TARGET_COURSE_KEY = "targetCourse";
    private TeacherFragmentMainBinding teacherFragmentMainBinding;
    private Course targetCourse;

    public static TeacherMainFragment newInstance() {
        return new TeacherMainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        teacherFragmentMainBinding = TeacherFragmentMainBinding.inflate(getLayoutInflater());
        teacherFragmentMainBinding.executePendingBindings();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        teacherMainViewModel = new TeacherMainViewModel(teacherFragmentMainBinding, fragmentManager,getActivity());
        teacherFragmentMainBinding.setTeacherMainFragment(teacherMainViewModel);
        fragmentManager.setFragmentResultListener(QUESTIONS_KEY, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                teacherFragmentMainBinding.getTeacherMainFragment().setQuestions(QuestionDeserializer.parseQuestionsJson(result.getString(QUESTIONS_KEY)));
            }
        });
        if (getArguments() != null && !getArguments().getString(COURSES_KEY).equals("empty")) {
            Bundle coursesBundle = getArguments();
            Type courseResponseType = new TypeToken<CourseResponse<ArrayList<Course>>>() {
            }.getType();
            CourseResponse<ArrayList<Course>> courseResponse = new Gson().fromJson(coursesBundle.getString(COURSES_KEY), courseResponseType);
            courses = courseResponse.getData();
            teacherMainViewModel.setAvailableCourses(courses);
            List<String> availableCourses = courses.stream().map(Course::getDescription).collect(Collectors.toList());
            Collections.reverse(availableCourses);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, availableCourses);
            AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) teacherFragmentMainBinding.courseNameInputLayout.getEditText();
            assert autoCompleteTextView != null;
            autoCompleteTextView.setAdapter(adapter);
//            teacherFragmentMainBinding.courseNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                    String courseName = adapterView.getItemAtPosition(position).toString();
//                    Objects.requireNonNull(teacherFragmentMainBinding.courseNameInputLayout.getEditText()).setText(courseName);
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
//                    Objects.requireNonNull(teacherFragmentMainBinding.courseNameInputLayout.getEditText()).setText("");
//                }
//            });
//        } else {
//            teacherFragmentMainBinding.courseNameSpinner.setEnabled(false);
//        }
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return this.teacherFragmentMainBinding.getRoot();
    }
}