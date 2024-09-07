package com.issasafar.anonymouse_assessment.views.student.ui.main.student;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.issasafar.anonymouse_assessment.data.models.common.Course;
import com.issasafar.anonymouse_assessment.data.models.common.CourseRequest;
import com.issasafar.anonymouse_assessment.data.models.common.CourseResponse;
import com.issasafar.anonymouse_assessment.databinding.FragmentStudentMainBinding;
import com.issasafar.anonymouse_assessment.viewmodels.student.StudentMainFragViewModel;
import com.issasafar.anonymouse_assessment.views.common.CoursesRepository;
import com.issasafar.anonymouse_assessment.views.common.ResultCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StudentMainFragment extends Fragment {

   private FragmentStudentMainBinding fragmentStudentMainBinding;
    public String COURSES_ARRAY_KEY = "courses_array_list_key";
    private String COURSES_KEY = "courses_key";
    public static StudentMainFragment newInstance() {
        return new StudentMainFragment();
    }

    private Type coursesArrayType = new TypeToken<ArrayList<Course>>() {
    }.getType();

private CoursesRepository coursesRepository;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        coursesRepository = new CoursesRepository();
        fragmentStudentMainBinding = FragmentStudentMainBinding.inflate(getLayoutInflater());
        StudentMainFragViewModel studentMainFragViewModel = new StudentMainFragViewModel(fragmentStudentMainBinding, getParentFragmentManager(),coursesRepository,getActivity());
        fragmentStudentMainBinding.setStudentMainFragViewModel(studentMainFragViewModel);
        fragmentStudentMainBinding.courseNameInputLayout.getEditText().setOnClickListener((view) -> {
            coursesRepository.postData(new CourseRequest(CourseRequest.CourseAction.GET_ALL_COURSES, null), getCourseResponseCallback());
                }
        );
        coursesRepository.postData(new CourseRequest(CourseRequest.CourseAction.GET_ALL_COURSES, null), getCourseResponseCallback());
    }

    private void storeCourses(ArrayList<Course> courses) {
        SharedPreferences sp = getActivity().getSharedPreferences(COURSES_ARRAY_KEY, Context.MODE_PRIVATE);
        sp.edit().putString(COURSES_KEY, new Gson().toJson(courses)).apply();
    }

    private ArrayList<Course> getStoredCourses() {
        SharedPreferences sp = getActivity().getSharedPreferences(COURSES_ARRAY_KEY, Context.MODE_PRIVATE);
        String json = sp.getString(COURSES_KEY, "empty");
        if (!"empty".equals(json)) {
            return new Gson().fromJson(json, coursesArrayType);
        } else {
            return new ArrayList<>();
        }
    }

    private  @NonNull ResultCallback<CourseResponse> getCourseResponseCallback() {
        return new ResultCallback<CourseResponse>() {
            @Override
            public void onSuccess(CourseResponse data) {
                ArrayList<Course> courses;
                if (data.getSuccess()) {
                     courses = new Gson().fromJson(new Gson().toJson(data.getData()), coursesArrayType);
                    Collections.reverse(courses);
                    storeCourses(courses);
                } else {
                    courses = getStoredCourses();
                }
                fragmentStudentMainBinding.getStudentMainFragViewModel().setCourses(courses);
                List<String> availableCoursesNamesList = courses.stream().map(Course::getDescription).collect(Collectors.toList());
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,availableCoursesNamesList);
                AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) fragmentStudentMainBinding.courseNameInputLayout.getEditText();
                assert autoCompleteTextView != null;
                getActivity().runOnUiThread(()-> {
                    autoCompleteTextView.setAdapter(adapter);

                });

            }

            @Override
            public void onError(Exception e) {
                if (e.getCause().toString().contains("java.net.ConnectException")) {
                    Snackbar.make(fragmentStudentMainBinding.getRoot(), "Unable to fetch courses (Connection error)", Snackbar.LENGTH_SHORT).show();
                } else if (e.getCause().toString().contains("java.net.SocketTimeoutException")) {
                    Snackbar.make(fragmentStudentMainBinding.getRoot(), "Unable to connect to the server", Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(fragmentStudentMainBinding.getRoot(), "Unknown error", Snackbar.LENGTH_SHORT).show();
                }

            }
        };
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return fragmentStudentMainBinding.getRoot();
    }


}