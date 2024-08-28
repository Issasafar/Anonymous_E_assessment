package com.issasafar.anonymouse_assessment.views.teacher.ui.main.teacher;

import static com.issasafar.anonymouse_assessment.views.teacher.ui.main.teacher.TeacherMainFragment.TARGET_COURSE_KEY;

import androidx.fragment.app.FragmentResultListener;
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
import com.issasafar.anonymouse_assessment.R;
import com.issasafar.anonymouse_assessment.data.models.common.Course;
import com.issasafar.anonymouse_assessment.data.models.common.LongAnswerQuestion;
import com.issasafar.anonymouse_assessment.data.models.common.Question;
import com.issasafar.anonymouse_assessment.databinding.FragmentAddQuestionsBinding;
import com.issasafar.anonymouse_assessment.viewmodels.teacher.AddQuestionsViewModel;
import com.issasafar.anonymouse_assessment.views.teacher.QuestionRecyclerViewAdapter;

import java.util.ArrayList;

public class AddQuestionsFragment extends Fragment {

    private AddQuestionsViewModel mViewModel;
    private FragmentAddQuestionsBinding addQuestionsBinding;

    public static AddQuestionsFragment newInstance() {
        return new AddQuestionsFragment();
    }
    private Course currentCourse;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addQuestionsBinding = FragmentAddQuestionsBinding.inflate(getLayoutInflater());
        addQuestionsBinding.executePendingBindings();
        // todo() data binding for addQues...
        getParentFragmentManager().setFragmentResultListener(TARGET_COURSE_KEY, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String courseJson = result.getString(TARGET_COURSE_KEY);
                currentCourse = new Gson().fromJson(courseJson, Course.class);
            }
        });
        QuestionRecyclerViewAdapter adapter = new QuestionRecyclerViewAdapter();
        addQuestionsBinding.questionsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        addQuestionsBinding.questionsRecycler.setAdapter(adapter);
        adapter.addQuestion();
        AddQuestionsViewModel addQuestionsViewModel = new AddQuestionsViewModel(addQuestionsBinding,adapter);
        addQuestionsBinding.setAddQuestionViewModel(addQuestionsViewModel);

       // add more logic
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return this.addQuestionsBinding.getRoot();
    }

}