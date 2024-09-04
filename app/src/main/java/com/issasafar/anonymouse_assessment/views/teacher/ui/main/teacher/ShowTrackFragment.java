package com.issasafar.anonymouse_assessment.views.teacher.ui.main.teacher;

import static com.issasafar.anonymouse_assessment.viewmodels.teacher.TeacherMainViewModel.ADAPTER_KEY;
import static com.issasafar.anonymouse_assessment.views.teacher.TeacherMainActivity.COURSES_KEY;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.issasafar.anonymouse_assessment.R;
import com.issasafar.anonymouse_assessment.data.models.common.Course;
import com.issasafar.anonymouse_assessment.data.models.common.TestResult;
import com.issasafar.anonymouse_assessment.databinding.FragmentShowTrackBinding;
import com.issasafar.anonymouse_assessment.viewmodels.teacher.ShowTrackViewModel;
import com.issasafar.anonymouse_assessment.views.teacher.ShowTrackRecyclerAdapter;

import java.util.ArrayList;

public class ShowTrackFragment extends Fragment {

    public static final String TESTS_RESULTS_KEY = "tests_result_key";
    private ShowTrackViewModel showTrackViewModel;
    private FragmentShowTrackBinding fragmentShowTrackBinding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentShowTrackBinding = FragmentShowTrackBinding.inflate(getLayoutInflater());
        showTrackViewModel = new ShowTrackViewModel(fragmentShowTrackBinding);
        fragmentShowTrackBinding.setShowTrackViewModel(showTrackViewModel);
        ShowTrackRecyclerAdapter showTrackRecyclerAdapter;
        if (getArguments() != null) {
            if (getArguments().getString(ADAPTER_KEY) != null) {
                ShowTrackRecyclerAdapter.AdapterType adapterType = new Gson().fromJson(getArguments().getString(ADAPTER_KEY), new TypeToken<ShowTrackRecyclerAdapter.AdapterType>() {
                }.getType());
                if (adapterType == ShowTrackRecyclerAdapter.AdapterType.GENERAL_TEACHER) {
                    ArrayList<Course> courses = new Gson().fromJson(getArguments().getString(COURSES_KEY), new TypeToken<ArrayList<Course>>() {
                    }.getType());
                     showTrackRecyclerAdapter = new ShowTrackRecyclerAdapter(getActivity().getSupportFragmentManager(),courses, adapterType);
                    fragmentShowTrackBinding.testResultRecycler.setAdapter(showTrackRecyclerAdapter);
                    showTrackRecyclerAdapter.setContext(getActivity());
                } else if (adapterType == ShowTrackRecyclerAdapter.AdapterType.TEACHER_VIEW) {
                    ArrayList<TestResult> testResults = new Gson().fromJson(getArguments().getString(TESTS_RESULTS_KEY), new TypeToken<ArrayList<TestResult>>() {
                    }.getType());
                    showTrackRecyclerAdapter = new ShowTrackRecyclerAdapter(getActivity().getSupportFragmentManager(), adapterType, testResults);
                    fragmentShowTrackBinding.testResultRecycler.setAdapter(showTrackRecyclerAdapter);
                    showTrackRecyclerAdapter.setContext(getActivity());
                } else if (adapterType == ShowTrackRecyclerAdapter.AdapterType.GENERAL_STUDENT) {
                    //todo() handle the student request
                    ArrayList<Course> courses = new Gson().fromJson(getArguments().getString(COURSES_KEY), new TypeToken<ArrayList<Course>>() {
                    }.getType());
                    ArrayList<TestResult> testResults = new Gson().fromJson(getArguments().getString(TESTS_RESULTS_KEY), new TypeToken<ArrayList<TestResult>>() {
                    }.getType());
                    showTrackRecyclerAdapter = new ShowTrackRecyclerAdapter(courses, testResults, adapterType, getActivity().getSupportFragmentManager());
                    fragmentShowTrackBinding.testResultRecycler.setAdapter(showTrackRecyclerAdapter);
                    showTrackRecyclerAdapter.setContext(getActivity());
                }


                fragmentShowTrackBinding.testResultRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       return this.fragmentShowTrackBinding.getRoot();
    }
}