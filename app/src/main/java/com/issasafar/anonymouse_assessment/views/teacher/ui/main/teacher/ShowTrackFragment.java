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
import com.issasafar.anonymouse_assessment.viewmodels.teacher.ShowTrackViewModel;

public class ShowTrackFragment extends Fragment {

    private ShowTrackViewModel mViewModel;

    public static ShowTrackFragment newInstance() {
        return new ShowTrackFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_track, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ShowTrackViewModel.class);
        // TODO: Use the ViewModel
    }

}