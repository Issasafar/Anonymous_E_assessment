package com.issasafar.anonymouse_assessment.views.teacher.ui.main.teacher;

import static com.issasafar.anonymouse_assessment.viewmodels.teacher.AddQuestionsViewModel.QUESTIONS_KEY;
import static com.issasafar.anonymouse_assessment.viewmodels.teacher.TeacherMainViewModel.ADAPTER_KEY;
import static com.issasafar.anonymouse_assessment.views.teacher.ShowTrackRecyclerAdapter.ANSWERS_KEY;
import static com.issasafar.anonymouse_assessment.views.teacher.TeacherMainActivity.COURSES_KEY;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.issasafar.anonymouse_assessment.data.models.common.Answer;
import com.issasafar.anonymouse_assessment.data.models.common.Course;
import com.issasafar.anonymouse_assessment.data.models.common.Question;
import com.issasafar.anonymouse_assessment.data.models.common.QuestionDeserializer;
import com.issasafar.anonymouse_assessment.data.models.common.TestResult;
import com.issasafar.anonymouse_assessment.databinding.FragmentShowTrackBinding;
import com.issasafar.anonymouse_assessment.viewmodels.student.StudentQuestionViewModel;
import com.issasafar.anonymouse_assessment.viewmodels.teacher.ShowTrackViewModel;
import com.issasafar.anonymouse_assessment.views.student.StudentQuestionRecyclerViewAdapter;
import com.issasafar.anonymouse_assessment.views.teacher.ShowTrackRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShowTrackFragment extends Fragment {

    public static final String TESTS_RESULTS_KEY = "tests_result_key";
    private ShowTrackViewModel showTrackViewModel;
    private FragmentShowTrackBinding fragmentShowTrackBinding;
    private StudentQuestionRecyclerViewAdapter studentQuestionRecyclerViewAdapter;
    private ArrayList<Answer> answers;

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
                    showTrackRecyclerAdapter = new ShowTrackRecyclerAdapter(getActivity().getSupportFragmentManager(), courses, adapterType);
                    fragmentShowTrackBinding.testResultRecycler.setAdapter(showTrackRecyclerAdapter);
                    showTrackRecyclerAdapter.setContext(getActivity());
                } else if (adapterType == ShowTrackRecyclerAdapter.AdapterType.TEACHER_VIEW) {
                    ArrayList<TestResult> testResults = new Gson().fromJson(getArguments().getString(TESTS_RESULTS_KEY), new TypeToken<ArrayList<TestResult>>() {
                    }.getType());
                    ArrayList<Course> courses = new Gson().fromJson(getArguments().getString(COURSES_KEY), new TypeToken<ArrayList<Course>>() {
                    }.getType());
                    showTrackRecyclerAdapter = new ShowTrackRecyclerAdapter(getActivity().getSupportFragmentManager(), adapterType, testResults);
                    showTrackRecyclerAdapter.setCourses(courses);
                    fragmentShowTrackBinding.testResultRecycler.setAdapter(showTrackRecyclerAdapter);
                    showTrackRecyclerAdapter.setContext(getActivity());
                } else if (adapterType == ShowTrackRecyclerAdapter.AdapterType.GENERAL_STUDENT) {
                    ArrayList<Course> courses = new Gson().fromJson(getArguments().getString(COURSES_KEY), new TypeToken<ArrayList<Course>>() {
                    }.getType());
                    ArrayList<TestResult> testResults = new Gson().fromJson(getArguments().getString(TESTS_RESULTS_KEY), new TypeToken<ArrayList<TestResult>>() {
                    }.getType());
                    showTrackRecyclerAdapter = new ShowTrackRecyclerAdapter(courses, testResults, adapterType, getActivity().getSupportFragmentManager());
                    fragmentShowTrackBinding.testResultRecycler.setAdapter(showTrackRecyclerAdapter);
                    showTrackRecyclerAdapter.setContext(getActivity());
                } else if (adapterType == ShowTrackRecyclerAdapter.AdapterType.STUDENT_VIEW) {
                    ArrayList<Question> questions = QuestionDeserializer.getQuestionsFromJson(getArguments().getString(QUESTIONS_KEY));
                    answers = new Gson().fromJson(getArguments().getString(ANSWERS_KEY),new TypeToken<ArrayList<Answer>>(){}.getType());
                     studentQuestionRecyclerViewAdapter = new StudentQuestionRecyclerViewAdapter(questions);
                    fragmentShowTrackBinding.testResultRecycler.setAdapter(studentQuestionRecyclerViewAdapter);
                    studentQuestionRecyclerViewAdapter.getViewModelsLive().observe(this, new Observer<List<StudentQuestionViewModel>>() {
                        @Override
                        public void onChanged(List<StudentQuestionViewModel> studentQuestionViewModels) {
                            if (studentQuestionViewModels.size() == questions.size()) {
                                studentQuestionRecyclerViewAdapter.showAnswers(answers);
                            }
                        }
                    });

                }


                fragmentShowTrackBinding.testResultRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        }

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return this.fragmentShowTrackBinding.getRoot();
    }
}