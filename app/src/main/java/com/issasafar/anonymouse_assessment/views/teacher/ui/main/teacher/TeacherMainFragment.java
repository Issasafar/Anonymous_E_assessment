package com.issasafar.anonymouse_assessment.views.teacher.ui.main.teacher;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.issasafar.anonymouse_assessment.R;
import com.issasafar.anonymouse_assessment.databinding.TeacherFragmentMainBinding;
import com.issasafar.anonymouse_assessment.viewmodels.teacher.TeacherMainViewModel;


public class TeacherMainFragment extends Fragment {

    private TeacherMainViewModel mViewModel;

    private TeacherFragmentMainBinding teacherFragmentMainBinding;

    public static TeacherMainFragment newInstance() {
        return new TeacherMainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        teacherFragmentMainBinding = TeacherFragmentMainBinding.inflate(getLayoutInflater());
        teacherFragmentMainBinding.executePendingBindings();
        mViewModel = new TeacherMainViewModel(teacherFragmentMainBinding);
        teacherFragmentMainBinding.setTeacherMainFragment(mViewModel);
        // TODO: Use the ViewModel
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return this.teacherFragmentMainBinding.getRoot();
    }
}