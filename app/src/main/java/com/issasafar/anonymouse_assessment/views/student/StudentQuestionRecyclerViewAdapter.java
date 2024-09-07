package com.issasafar.anonymouse_assessment.views.student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.issasafar.anonymouse_assessment.data.models.common.Answer;
import com.issasafar.anonymouse_assessment.data.models.common.Question;
import com.issasafar.anonymouse_assessment.data.models.common.TestResult;
import com.issasafar.anonymouse_assessment.databinding.StudentQuestionItemBinding;
import com.issasafar.anonymouse_assessment.viewmodels.student.StudentQuestionViewModel;
import com.issasafar.anonymouse_assessment.views.login.LoginViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentQuestionRecyclerViewAdapter extends RecyclerView.Adapter<StudentQuestionRecyclerViewAdapter.ViewHolder> {
    private List<Question> questions;
    private List<StudentQuestionViewModel> viewModels = new ArrayList<>();

    public MutableLiveData<List<StudentQuestionViewModel>> getViewModelsLive() {
        return viewModelsLive;
    }

    private MutableLiveData<List<StudentQuestionViewModel>> viewModelsLive = new MutableLiveData<>();
    private Context context = null;

    public StudentQuestionRecyclerViewAdapter(List<Question> questions) {
        this.questions = questions;
    }

    public void showAnswers(ArrayList<Answer> answers) {
        int size = viewModels.size();
        if (viewModels.size() > answers.size()) {
           size = answers.size();
        }
        for (int i = 0; i < size; i++) {
           viewModels.get(i).showStudentTestAnswers(questions.get(i),answers.get(i));
        }
    }

    public List<Answer> getAnswers() {
        return viewModels.stream().map(StudentQuestionViewModel::getAnswer).collect(Collectors.toList());
    }

    public TestResult getResult() {
        long correctAnswersCount = viewModels.stream().filter(StudentQuestionViewModel::isCorrect).count();
        double marks = (correctAnswersCount / (double) questions.size()) * 100;
        return new TestResult(Integer.parseInt(LoginViewModel.getUserId(context)), LoginViewModel.getStudentSign(context), questions.get(0).getT_id(), (int) marks);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        StudentQuestionItemBinding studentQuestionItemBinding = StudentQuestionItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        StudentQuestionViewModel studentQuestionViewModel = new StudentQuestionViewModel(parent.getContext(), studentQuestionItemBinding);
        studentQuestionItemBinding.setStudentQuestionViewModel(studentQuestionViewModel);
        viewModels.add(studentQuestionViewModel);
        viewModelsLive.postValue(viewModels);
        View rootHolderView = studentQuestionItemBinding.getRoot();
        rootHolderView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(studentQuestionItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.studentQuestionItemBinding.getStudentQuestionViewModel().setQuestion(questions.get(position));
    }

    @Override
    public int getItemCount() {
        if (questions != null) {
            return questions.size();
        } else {
            return 0;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private StudentQuestionItemBinding studentQuestionItemBinding;

        public ViewHolder(StudentQuestionItemBinding studentQuestionItemBinding) {
            super(studentQuestionItemBinding.getRoot());
            this.studentQuestionItemBinding = studentQuestionItemBinding;
        }
    }
}
