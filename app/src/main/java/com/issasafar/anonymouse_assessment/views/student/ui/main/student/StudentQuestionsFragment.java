package com.issasafar.anonymouse_assessment.views.student.ui.main.student;

import static com.issasafar.anonymouse_assessment.viewmodels.teacher.AddQuestionsViewModel.QUESTIONS_KEY;
import static com.issasafar.anonymouse_assessment.viewmodels.teacher.TeacherMainViewModel.TEACHER_LAUNCH_KEY;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.issasafar.anonymouse_assessment.data.models.common.Question;
import com.issasafar.anonymouse_assessment.data.models.common.QuestionDeserializer;
import com.issasafar.anonymouse_assessment.databinding.FragmentStudentQuestionsBinding;
import com.issasafar.anonymouse_assessment.viewmodels.student.StudentQuestionsViewModel;
import com.issasafar.anonymouse_assessment.views.common.CoursesRepository;
import com.issasafar.anonymouse_assessment.views.student.StudentQuestionRecyclerViewAdapter;

import java.util.ArrayList;

public class StudentQuestionsFragment extends Fragment {

    private FragmentStudentQuestionsBinding fragmentStudentQuestionsBinding;

    public static StudentQuestionsFragment newInstance() {
        return new StudentQuestionsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentStudentQuestionsBinding = FragmentStudentQuestionsBinding.inflate(getLayoutInflater());
        fragmentStudentQuestionsBinding.resultCard.setVisibility(View.GONE);
        CoursesRepository coursesRepository = new CoursesRepository();
        if (getArguments() != null) {
            ArrayList<Question> questions = QuestionDeserializer.getQuestionsFromJson(getArguments().getString(QUESTIONS_KEY));
            StudentQuestionRecyclerViewAdapter studentQuestionRecyclerViewAdapter = new StudentQuestionRecyclerViewAdapter(questions);
            fragmentStudentQuestionsBinding.questionsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            fragmentStudentQuestionsBinding.questionsRecycler.setAdapter(studentQuestionRecyclerViewAdapter);
            StudentQuestionsViewModel studentQuestionsViewModel = new StudentQuestionsViewModel(studentQuestionRecyclerViewAdapter,coursesRepository,fragmentStudentQuestionsBinding.getRoot());
            fragmentStudentQuestionsBinding.setStudentQuestionsViewModel(studentQuestionsViewModel);
            if (getArguments().getBoolean(TEACHER_LAUNCH_KEY)) {
               fragmentStudentQuestionsBinding.sendAnswersButton.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return fragmentStudentQuestionsBinding.getRoot();
    }
}