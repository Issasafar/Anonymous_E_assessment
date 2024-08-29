package com.issasafar.anonymouse_assessment.views.teacher;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.issasafar.anonymouse_assessment.R;
import com.issasafar.anonymouse_assessment.data.models.common.Question;
import com.issasafar.anonymouse_assessment.databinding.QuestionRecyclerItemBinding;
import com.issasafar.anonymouse_assessment.viewmodels.teacher.QuestionViewModel;

import java.util.ArrayList;

public class QuestionRecyclerViewAdapter extends RecyclerView.Adapter<QuestionRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Question> questions = new ArrayList<>();
    private Context context;
    private String[] questionTypes = new String[2];
    private static final ArrayList<ViewHolder> viewHolders = new ArrayList<>();
    private int questionsCount = 0;
    public boolean isValidQuestions() {
        return validQuestions;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    private boolean validQuestions = false;

    public QuestionRecyclerViewAdapter(ArrayList<Question> questions) {
        this.questions = questions;
    }
    public void checkForInputErrors(){
        for (ViewHolder holder : viewHolders) {
            holder.questionRecyclerItemBinding.getQuestionViewModel().notifyAllTexts();
            if (!holder.questionRecyclerItemBinding.getQuestionViewModel().inputIsValid()) {
                validQuestions = false;
            }
        }
        validQuestions = viewHolders.stream().noneMatch(item->item.getQuestionRecyclerItemBinding().getQuestionViewModel().inputIsValid() == false);
    }
    public void createQuestionsFromHolders(){
        if (isValidQuestions()) {
            for (ViewHolder holder : viewHolders) {
                Question question  = holder.questionRecyclerItemBinding.getQuestionViewModel().getQuestion();
                questions.add(question);
            }
        }
    }

    public QuestionRecyclerViewAdapter() {
    }

    public void setContext(Context context) {
        this.context = context;
        questionTypes[0] = context.getResources().getString(R.string.long_answer);
        questionTypes[1] = context.getResources().getString(R.string.multiple_choice);
    }

    public void addQuestion() {
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
            questionRecyclerItemBinding.executePendingBindings();
            QuestionViewModel questionViewModel = new QuestionViewModel(questionRecyclerItemBinding);
            questionRecyclerItemBinding.setQuestionViewModel(questionViewModel);
            viewHolders.add(this);
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
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, questionTypes);
        questionRecyclerItemBinding.courseTypeSpinner.setAdapter(spinnerAdapter);
        questionRecyclerItemBinding.courseTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    questionRecyclerItemBinding.choicesLayout.setVisibility(View.GONE);
                } else {
                    questionRecyclerItemBinding.choicesLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return new ViewHolder(questionView, questionRecyclerItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionRecyclerViewAdapter.ViewHolder holder, int position) {
        TextView questionNumberEditText = holder.getQuestionRecyclerItemBinding().questionNumberText;
        holder.getQuestionRecyclerItemBinding().getQuestionViewModel().setQuestionNumber(position+1);
        questionNumberEditText.setText("Question " + (position + 1) + ":");
    }


    @Override
    public int getItemCount() {
        return questionsCount;
    }
}
