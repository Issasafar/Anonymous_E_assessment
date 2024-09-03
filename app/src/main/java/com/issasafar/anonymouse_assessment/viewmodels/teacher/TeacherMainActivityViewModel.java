package com.issasafar.anonymouse_assessment.viewmodels.teacher;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.issasafar.anonymouse_assessment.data.models.common.UnderstandingMessage;
import com.issasafar.anonymouse_assessment.databinding.ActivityTeacherMainBinding;

import java.util.ArrayList;

public class TeacherMainActivityViewModel extends BaseObservable {
    private ActivityTeacherMainBinding activityTeacherMainBinding;

    public ArrayList<UnderstandingMessage> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<UnderstandingMessage> messages) {
        if (messages.isEmpty()) {
            setNotificationCount(0);
            return;
        }
        this.messages = messages;
        Handler handler = new Handler(Looper.getMainLooper());
        int messageCount = messages.size();
        final int delay = 1000;
        setNotificationCount(messageCount);
        for (int i = 0; i < messageCount; i++) {
            if (messages.get(i).isFetched()) {
                continue;
            }
            String str = messages.get(i).getStudent_name() + ": " + messages.get(i).getMessage();
            Runnable messageDisplayRunnable = () -> {setMessage(str);};
            handler.postDelayed(messageDisplayRunnable, delay * (i + 1));
        }
        handler.postDelayed(() -> {
            setMessage("");
        }, delay * messageCount + delay+delay/3);
    }

    private ArrayList<UnderstandingMessage> messages = new ArrayList<>();
    public TeacherMainActivityViewModel(ActivityTeacherMainBinding activityTeacherMainBinding) {
        this.activityTeacherMainBinding = activityTeacherMainBinding;
    }

    @Bindable
    public int getNotificationVisibility() {
        return notificationVisibility;
    }

    public void setNotificationVisibility(int notificationVisibility) {
        this.notificationVisibility = notificationVisibility;
        notifyPropertyChanged(BR.notificationCount);
    }
    @Bindable
    public String getNotificationCount() {
        return notificationCount;
    }

    public void setNotificationCount(int notificationCount) {
        this.notificationCount = String.valueOf(notificationCount);
        if (notificationCount > 0) {
            setNotificationVisibility(View.VISIBLE);
            notifyPropertyChanged(BR.notificationVisibility);
        } else {
            setNotificationVisibility(View.GONE);
            notifyPropertyChanged(BR.notificationVisibility);
        }
    }

    @Bindable
    private int notificationVisibility = View.GONE;
    @Bindable
    private String notificationCount = "";
    @Bindable
    private String message = "";
    @Bindable
    int messageTextViewVisibility = View.GONE;
    @Bindable
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        if ("".equals(message)) {
            setMessageTextViewVisibility(View.GONE);
            notifyPropertyChanged(BR.messageTextViewVisibility);
        } else {
            setMessageTextViewVisibility(View.VISIBLE);
            notifyPropertyChanged(BR.messageTextViewVisibility);
        }
        this.message = message;
        notifyPropertyChanged(BR.message);
    }
@Bindable
    public int getMessageTextViewVisibility() {
        return messageTextViewVisibility;
    }

    public void setMessageTextViewVisibility(int messageTextViewVisibility) {
        this.messageTextViewVisibility = messageTextViewVisibility;
        notifyPropertyChanged(BR.notificationVisibility);
    }
}
