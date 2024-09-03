package com.issasafar.anonymouse_assessment.views.teacher.ui.main.teacher;

import static com.issasafar.anonymouse_assessment.views.teacher.TeacherMainActivity.MESSAGES_KEY;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.issasafar.anonymouse_assessment.data.models.common.CourseRequest;
import com.issasafar.anonymouse_assessment.data.models.common.CourseResponse;
import com.issasafar.anonymouse_assessment.data.models.common.UnderstandingMessage;
import com.issasafar.anonymouse_assessment.databinding.FragmentMessagesNotificationBinding;
import com.issasafar.anonymouse_assessment.viewmodels.teacher.MessagesNotificationViewModel;
import com.issasafar.anonymouse_assessment.views.common.CoursesRepository;
import com.issasafar.anonymouse_assessment.views.common.ResultCallback;
import com.issasafar.anonymouse_assessment.views.login.LoginDataSource;
import com.issasafar.anonymouse_assessment.views.login.LoginRepository;
import com.issasafar.anonymouse_assessment.views.login.LoginViewModel;
import com.issasafar.anonymouse_assessment.views.teacher.MessageRecyclerAdapter;

import java.util.ArrayList;

public class MessagesNotificationFragment extends Fragment {

    private MessagesNotificationViewModel mViewModel;
    private FragmentMessagesNotificationBinding fragmentMessagesNotificationBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentMessagesNotificationBinding = FragmentMessagesNotificationBinding.inflate(getLayoutInflater());
        if (getArguments() != null && getArguments().getString(MESSAGES_KEY) != null) {
            ArrayList<UnderstandingMessage> messages = new Gson().fromJson(getArguments().getString(MESSAGES_KEY), new TypeToken<ArrayList<UnderstandingMessage>>() {
            }.getType());
            if (messages != null) {
                fragmentMessagesNotificationBinding.messagesRecycler.setAdapter(new MessageRecyclerAdapter(messages));
                fragmentMessagesNotificationBinding.messagesRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                CoursesRepository coursesRepository = new CoursesRepository();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("teacher_id", LoginViewModel.getUserId(getContext()));
                jsonObject.addProperty("m_id",messages.get(messages.size()-1).getM_id());
                coursesRepository.postData(new CourseRequest(CourseRequest.CourseAction.MARK_SEEN, jsonObject), new ResultCallback<CourseResponse>() {
                    @Override
                    public void onSuccess(CourseResponse data) {
                        if (data.getSuccess()) {
                            Snackbar.make(fragmentMessagesNotificationBinding.getRoot(),data.getMessage(),Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return fragmentMessagesNotificationBinding.getRoot();
    }

}