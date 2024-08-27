package com.issasafar.anonymouse_assessment.views.teacher.ui.main.teacher;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.issasafar.anonymouse_assessment.R;
import com.issasafar.anonymouse_assessment.databinding.FragmentAddQuestionsBinding;
import com.issasafar.anonymouse_assessment.viewmodels.teacher.AddQuestionsViewModel;

public class AddQuestionsFragment extends Fragment {

    private AddQuestionsViewModel mViewModel;
    private FragmentAddQuestionsBinding addQuestionsBinding;

    public static AddQuestionsFragment newInstance() {
        return new AddQuestionsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addQuestionsBinding = FragmentAddQuestionsBinding.inflate(getLayoutInflater());
       // add more logic
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return this.addQuestionsBinding.getRoot();
    }

}