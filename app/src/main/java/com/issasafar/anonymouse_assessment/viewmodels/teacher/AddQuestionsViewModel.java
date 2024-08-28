package com.issasafar.anonymouse_assessment.viewmodels.teacher;

import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.issasafar.anonymouse_assessment.data.models.common.Question;
import com.issasafar.anonymouse_assessment.databinding.FragmentAddQuestionsBinding;
import com.issasafar.anonymouse_assessment.views.teacher.QuestionRecyclerViewAdapter;

import java.util.ArrayList;

public class AddQuestionsViewModel extends ViewModel {
    private FragmentAddQuestionsBinding questionsBinding;
    private QuestionRecyclerViewAdapter questionRecyclerViewAdapter;
    public AddQuestionsViewModel(FragmentAddQuestionsBinding fragmentAddQuestionsBinding , QuestionRecyclerViewAdapter questionRecyclerViewAdapter) {
        this.questionsBinding = fragmentAddQuestionsBinding;
        this.questionRecyclerViewAdapter = questionRecyclerViewAdapter;
    }
    public void saveClicked(){

    }

    public void addQuestionClicked() {
        questionRecyclerViewAdapter.addQuestion();
        questionRecyclerViewAdapter.notifyDataSetChanged();
    }

}