package com.issasafar.anonymouse_assessment.viewmodels.teacher;

import android.widget.TextView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.ViewModel;

import com.issasafar.anonymouse_assessment.BR;
import com.issasafar.anonymouse_assessment.databinding.FragmentShowTrackBinding;

public class ShowTrackViewModel extends BaseObservable {
    private FragmentShowTrackBinding fragmentShowTrackBinding;
    @Bindable
    public String getTopText() {
        return topText;
    }

    public void setTopText(String topText) {
        this.topText = topText;
        notifyPropertyChanged(BR.topText);
    }

    @Bindable
    private String topText;

    public ShowTrackViewModel(FragmentShowTrackBinding fragmentShowTrackBinding) {
        this.fragmentShowTrackBinding = fragmentShowTrackBinding;
        setTopText("Tests' Results");
    }
}