package com.issasafar.anonymouse_assessment.views.teacher.ui.main.teacher;


import static android.content.Context.MODE_PRIVATE;
import static com.issasafar.anonymouse_assessment.viewmodels.teacher.AddQuestionsViewModel.QUESTIONS_KEY;
import static com.issasafar.anonymouse_assessment.views.teacher.TeacherMainActivity.COURSES_KEY;
import static com.issasafar.anonymouse_assessment.views.teacher.TeacherMainActivity.COURSES_PREF_FILE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.issasafar.anonymouse_assessment.data.models.common.Course;
import com.issasafar.anonymouse_assessment.data.models.common.CourseRequest;
import com.issasafar.anonymouse_assessment.data.models.common.CourseResponse;
import com.issasafar.anonymouse_assessment.data.models.common.QuestionDeserializer;
import com.issasafar.anonymouse_assessment.databinding.TeacherFragmentMainBinding;
import com.issasafar.anonymouse_assessment.viewmodels.teacher.TeacherMainViewModel;
import com.issasafar.anonymouse_assessment.views.common.CoursesRepository;
import com.issasafar.anonymouse_assessment.views.common.ResultCallback;
import com.issasafar.anonymouse_assessment.views.login.LoginViewModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class TeacherMainFragment extends Fragment {

    public static final String TARGET_COURSE_KEY = "targetCourse";
    private TeacherMainViewModel teacherMainViewModel;
    private ArrayList<Course> courses;
    private TeacherFragmentMainBinding teacherFragmentMainBinding;
    private Course targetCourse;
    private String userId;
    private CoursesRepository coursesRepository;

    public static TeacherMainFragment newInstance() {
        return new TeacherMainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        teacherFragmentMainBinding = TeacherFragmentMainBinding.inflate(getLayoutInflater());
        teacherFragmentMainBinding.executePendingBindings();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        teacherMainViewModel = new TeacherMainViewModel(teacherFragmentMainBinding, fragmentManager, getActivity());
        teacherFragmentMainBinding.setTeacherMainFragment(teacherMainViewModel);
        fragmentManager.setFragmentResultListener(QUESTIONS_KEY, this, (requestKey, result) -> teacherFragmentMainBinding.getTeacherMainFragment().setQuestions(QuestionDeserializer.getQuestionsFromJson(result.getString(QUESTIONS_KEY))));
        ResultCallback<CourseResponse> courseCallback = getCourseResponseResultCallback();
        teacherFragmentMainBinding.courseNameInputLayout.getEditText().setOnClickListener((view) -> {
            userId = LoginViewModel.getUserId(getActivity().getApplicationContext());
            coursesRepository = new CoursesRepository();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("owner_id", userId);
            coursesRepository.getData(new CourseRequest(CourseRequest.CourseAction.GET_COURSES, jsonObject), courseCallback);
        });
        teacherFragmentMainBinding.courseNameInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = teacherFragmentMainBinding.courseNameInputLayout.getEditText().getText().toString();
                teacherFragmentMainBinding.courseNameInputLayout.setEndIconVisible(!"".equals(str));
            }
        });
        teacherFragmentMainBinding.courseNameInputLayout.getEditText().performClick();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return this.teacherFragmentMainBinding.getRoot();
    }

    private @NonNull ResultCallback<CourseResponse> getCourseResponseResultCallback() {
        ResultCallback<CourseResponse> courseCallback = new ResultCallback<CourseResponse>() {
            @Override
            public void onSuccess(CourseResponse data) {
                Type courseResponseType = new TypeToken<CourseResponse<ArrayList<Course>>>() {
                }.getType();
                CourseResponse<ArrayList<Course>> courseResponse;
                CourseResponse<ArrayList<Course>> prev = null;
                if (!"empty".equals(getCourseResponseString())) {
                     prev = new Gson().fromJson(getCourseResponseString(), courseResponseType);
                }
                CourseResponse<ArrayList<Course>> fetched = new Gson().fromJson(new Gson().toJson(data), courseResponseType);
                if (data.getSuccess() && prev != fetched) {
                    saveCourseResponse(data);
                    courses = fetched.getData();
                    List<String> availableCourses = courses.stream().map(Course::getDescription).collect(Collectors.toList());
                    Collections.reverse(availableCourses);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, availableCourses);
                    AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) teacherFragmentMainBinding.courseNameInputLayout.getEditText();
                    teacherMainViewModel.setAvailableCourses(courses);
                    assert autoCompleteTextView != null;
                    getActivity().runOnUiThread(() -> {
                        autoCompleteTextView.setAdapter(adapter);
                    });
                } else {
                    if (!"empty".equals(getCourseResponseString())) {
                        courseResponse = new Gson().fromJson(getCourseResponseString(), courseResponseType);
                        courses = courseResponse.getData();
                        List<String> availableCourses = courses.stream().map(Course::getDescription).collect(Collectors.toList());
                        Collections.reverse(availableCourses);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, availableCourses);
                        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) teacherFragmentMainBinding.courseNameInputLayout.getEditText();
                        assert autoCompleteTextView != null;
                        getActivity().runOnUiThread(() -> {
                            autoCompleteTextView.setAdapter(adapter);
                        });

                    } else {
                        Snackbar.make(teacherFragmentMainBinding.getRoot(), data.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onError(Exception e) {
                if (e.getCause().toString().contains("java.net.ConnectException")) {
                    Snackbar.make(teacherFragmentMainBinding.getRoot(), "Unable to fetch courses (Connection error)", Snackbar.LENGTH_SHORT).show();
                } else if (e.getCause().toString().contains("java.net.SocketTimeoutException")) {
                    Snackbar.make(teacherFragmentMainBinding.getRoot(), "Unable to connect to the server", Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(teacherFragmentMainBinding.getRoot(), "Unknown error", Snackbar.LENGTH_SHORT).show();
                }
            }
        };
        return courseCallback;
    }

    private void saveCourseResponse(CourseResponse data) {
        SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences(COURSES_PREF_FILE, MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();
        spEditor.putString(COURSES_KEY, new Gson().toJson(data));
        spEditor.apply();
    }

    private String getCourseResponseString() {
        SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences(COURSES_PREF_FILE, MODE_PRIVATE);
        return sp.getString(COURSES_KEY, "empty");

    }
}