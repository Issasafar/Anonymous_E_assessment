package com.issasafar.anonymouse_assessment.views.teacher.ui.main.teacher;

import static com.issasafar.anonymouse_assessment.views.teacher.ui.main.teacher.TeacherMainFragment.TARGET_COURSE_KEY;

import androidx.fragment.app.FragmentResultListener;

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
import com.issasafar.anonymouse_assessment.data.models.common.Course;
import com.issasafar.anonymouse_assessment.databinding.FragmentAddQuestionsBinding;
import com.issasafar.anonymouse_assessment.viewmodels.teacher.AddQuestionsViewModel;
import com.issasafar.anonymouse_assessment.views.teacher.QuestionRecyclerViewAdapter;

import java.lang.reflect.Type;

public class AddQuestionsFragment extends Fragment {

    private AddQuestionsViewModel addQuestionsViewModel;
    private FragmentAddQuestionsBinding addQuestionsBinding;


    private Course currentCourse;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            addQuestionsBinding = FragmentAddQuestionsBinding.inflate(getLayoutInflater());
            QuestionRecyclerViewAdapter questionsAdapter = new QuestionRecyclerViewAdapter();
            questionsAdapter.setContext(getActivity().getApplicationContext());
            addQuestionsBinding.questionsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            addQuestionsBinding.questionsRecycler.setAdapter(questionsAdapter);
            addQuestionsViewModel = new AddQuestionsViewModel(addQuestionsBinding, questionsAdapter, getParentFragmentManager());
            addQuestionsBinding.setAddQuestionViewModel(addQuestionsViewModel);
        addQuestionsBinding.executePendingBindings();
        getParentFragmentManager().setFragmentResultListener(TARGET_COURSE_KEY, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String courseJson = result.getString(TARGET_COURSE_KEY);
                currentCourse = new Gson().fromJson(courseJson, Course.class);
            }
        });


        // add more logic
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return this.addQuestionsBinding.getRoot();
    }

}