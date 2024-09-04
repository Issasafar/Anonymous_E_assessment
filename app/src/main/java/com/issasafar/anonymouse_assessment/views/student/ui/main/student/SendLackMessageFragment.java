package com.issasafar.anonymouse_assessment.views.student.ui.main.student;

import static com.issasafar.anonymouse_assessment.viewmodels.student.StudentMainFragViewModel.SELECTED_COURSE_KEY;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.issasafar.anonymouse_assessment.R;
import com.issasafar.anonymouse_assessment.data.models.common.Course;
import com.issasafar.anonymouse_assessment.databinding.FragmentSendLackMessageBinding;
import com.issasafar.anonymouse_assessment.viewmodels.student.SendLackMessageViewModel;
import com.issasafar.anonymouse_assessment.views.common.CoursesRepository;

public class SendLackMessageFragment extends Fragment {

    private SendLackMessageViewModel mViewModel;

    public static SendLackMessageFragment newInstance() {
        return new SendLackMessageFragment();
    }

    private FragmentSendLackMessageBinding fragmentSendLackMessageBinding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CoursesRepository coursesRepository = new CoursesRepository();
        fragmentSendLackMessageBinding = FragmentSendLackMessageBinding.inflate(getLayoutInflater());
        SendLackMessageViewModel sendLackMessageViewModel = new SendLackMessageViewModel(fragmentSendLackMessageBinding,coursesRepository,getActivity());
        fragmentSendLackMessageBinding.setSendLackMessageViewModel(sendLackMessageViewModel);
        if (getArguments() != null && getArguments().getString(SELECTED_COURSE_KEY) != null) {
            Course course = new Gson().fromJson(getArguments().getString(SELECTED_COURSE_KEY),Course.class);
            sendLackMessageViewModel.setSelected(course);
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return fragmentSendLackMessageBinding.getRoot();
    }


}