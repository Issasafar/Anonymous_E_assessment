package com.issasafar.anonymouse_assessment.viewmodels.teacher;

import android.os.Bundle;

import androidx.databinding.Bindable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.issasafar.anonymouse_assessment.data.models.common.Question;
import com.issasafar.anonymouse_assessment.databinding.FragmentAddQuestionsBinding;
import com.issasafar.anonymouse_assessment.views.teacher.QuestionRecyclerViewAdapter;

import java.util.ArrayList;

public class AddQuestionsViewModel extends ViewModel {
    public static final String QUESTIONS_KEY = "questions";
    private FragmentAddQuestionsBinding questionsBinding;
    private QuestionRecyclerViewAdapter questionRecyclerViewAdapter;
    private FragmentManager fragmentManager;
    private ArrayList<Question> questions;
    public AddQuestionsViewModel(FragmentAddQuestionsBinding fragmentAddQuestionsBinding , QuestionRecyclerViewAdapter questionRecyclerViewAdapter,FragmentManager fragmentManager) {
        this.questionsBinding = fragmentAddQuestionsBinding;
        this.fragmentManager = fragmentManager;
        this.questionRecyclerViewAdapter = questionRecyclerViewAdapter;
    }
    public void saveClicked(){
        questionRecyclerViewAdapter.checkForInputErrors();
        if(questionRecyclerViewAdapter.isValidQuestions()){
            questionRecyclerViewAdapter.createQuestionsFromHolders();
            Bundle bundle = new Bundle();
            bundle.putString(QUESTIONS_KEY,new Gson().toJson(questionRecyclerViewAdapter.getQuestions()));
            fragmentManager.setFragmentResult(QUESTIONS_KEY, bundle);
            fragmentManager.popBackStack();
        }
    }

    public void addQuestionClicked() {
        questionRecyclerViewAdapter.addQuestion();
    }

}