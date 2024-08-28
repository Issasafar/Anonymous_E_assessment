package com.issasafar.anonymouse_assessment.views.teacher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.issasafar.anonymouse_assessment.data.models.common.Question;
import com.issasafar.anonymouse_assessment.databinding.QuestionRecyclerItemBinding;

import java.util.ArrayList;

public class QuestionRecyclerViewAdapter extends RecyclerView.Adapter<QuestionRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Question> questions;
private int questionsCount = 0;
    public QuestionRecyclerViewAdapter(ArrayList<Question> questions) {
        this.questions = questions;
    }
    public QuestionRecyclerViewAdapter() {
    }
    public void addQuestion(){
        this.questionsCount++;
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private QuestionRecyclerItemBinding questionRecyclerItemBinding;

        public ViewHolder(@NonNull View itemView, QuestionRecyclerItemBinding questionRecyclerItemBinding) {
            super(itemView);
            this.questionRecyclerItemBinding = questionRecyclerItemBinding;
        }

        public QuestionRecyclerItemBinding getQuestionRecyclerItemBinding() {
            return questionRecyclerItemBinding;
        }
    }

    @NonNull
    @Override
    public QuestionRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QuestionRecyclerItemBinding questionRecyclerItemBinding = QuestionRecyclerItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        View questionView = questionRecyclerItemBinding.getRoot();
        questionView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        return new ViewHolder(questionView, questionRecyclerItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionRecyclerViewAdapter.ViewHolder holder, int position) {
        TextView questionNumberEditText = holder.getQuestionRecyclerItemBinding().questionNumberText;
        questionNumberEditText.setText("Question "+(position + 1)+":");
    }


    @Override
    public int getItemCount() {
        return questionsCount;
    }
}
