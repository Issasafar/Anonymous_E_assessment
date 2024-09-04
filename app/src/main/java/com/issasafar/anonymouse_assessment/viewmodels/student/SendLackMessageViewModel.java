package com.issasafar.anonymouse_assessment.viewmodels.student;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.issasafar.anonymouse_assessment.BR;
import com.issasafar.anonymouse_assessment.data.models.common.Course;
import com.issasafar.anonymouse_assessment.data.models.common.CourseRequest;
import com.issasafar.anonymouse_assessment.data.models.common.CourseResponse;
import com.issasafar.anonymouse_assessment.databinding.FragmentSendLackMessageBinding;
import com.issasafar.anonymouse_assessment.views.common.CoursesRepository;
import com.issasafar.anonymouse_assessment.views.common.ResultCallback;
import com.issasafar.anonymouse_assessment.views.login.LoginViewModel;

public class SendLackMessageViewModel extends BaseObservable {
    private FragmentSendLackMessageBinding fragmentSendLackMessageBinding;
    private CoursesRepository coursesRepository;

    public void setSelected(Course selected) {
        this.selected = selected;
    }

    private Course selected;
    private Context context;
    @Bindable
    private String messageText = "";

    public SendLackMessageViewModel(FragmentSendLackMessageBinding fragmentSendLackMessageBinding, CoursesRepository coursesRepository, Context context) {
        this.context = context;
        this.fragmentSendLackMessageBinding = fragmentSendLackMessageBinding;
        this.coursesRepository = coursesRepository;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
        notifyPropertyChanged(BR.messageText);
    }

    public void sendClicked() {
        if ("".equals(getMessageText().trim())) {
            Snackbar.make(fragmentSendLackMessageBinding.getRoot(), "No message provided", Snackbar.LENGTH_SHORT).show();
        } else {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("teacher_id", selected.getOwner_id());
            jsonObject.addProperty("student_id", LoginViewModel.getUserId(context));
            jsonObject.addProperty("message", getMessageText());
            coursesRepository.postData(new CourseRequest(CourseRequest.CourseAction.POST_MESSAGE, jsonObject), new ResultCallback<CourseResponse>() {
                @Override
                public void onSuccess(CourseResponse data) {
                    if (data.getSuccess()) {
                        showDialog(data.getMessage());
                        setMessageText("");
                    } else {
                        Snackbar.make(fragmentSendLackMessageBinding.getRoot(), "Unable to post message (Server error)", Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Exception e) {
                    if (e.getCause().toString().contains("java.net.ConnectException")) {
                        Snackbar.make(fragmentSendLackMessageBinding.getRoot(), "Unable to post message (Connection error)", Snackbar.LENGTH_SHORT).show();
                    } else if (e.getCause().toString().contains("java.net.SocketTimeoutException")) {
                        Snackbar.make(fragmentSendLackMessageBinding.getRoot(), "Unable to connect to the server", Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(fragmentSendLackMessageBinding.getRoot(), "Unknown error", Snackbar.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    public void showDialog(String message) {
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(context);
        materialAlertDialogBuilder.setTitle("Message sent")
                .setMessage(message)
                .setPositiveButton("Ok", (dialogInterface, i) -> dialogInterface.cancel());
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            AlertDialog alertDialog = materialAlertDialogBuilder.create();
            alertDialog.show();
        });
    }

}