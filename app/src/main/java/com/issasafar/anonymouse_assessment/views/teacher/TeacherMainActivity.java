package com.issasafar.anonymouse_assessment.views.teacher;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.issasafar.anonymouse_assessment.R;
import com.issasafar.anonymouse_assessment.data.models.common.CourseRequest;
import com.issasafar.anonymouse_assessment.data.models.common.CourseResponse;
import com.issasafar.anonymouse_assessment.data.models.common.UnderstandingMessage;
import com.issasafar.anonymouse_assessment.databinding.ActivityTeacherMainBinding;
import com.issasafar.anonymouse_assessment.viewmodels.teacher.TeacherMainActivityViewModel;
import com.issasafar.anonymouse_assessment.views.common.CoursesRepository;
import com.issasafar.anonymouse_assessment.views.common.ResultCallback;
import com.issasafar.anonymouse_assessment.views.login.LoginViewModel;
import com.issasafar.anonymouse_assessment.views.teacher.ui.main.teacher.MessagesNotificationFragment;
import com.issasafar.anonymouse_assessment.views.teacher.ui.main.teacher.TeacherMainFragment;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class TeacherMainActivity extends AppCompatActivity {
    public static final String COURSES_KEY = "courses";
    public static final String COURSES_PREF_FILE = "courses_shared_pref_file";
    public static final String MESSAGES_KEY = "messages";
    private final int delay = 60000;
    ActivityTeacherMainBinding activityTeacherMainBinding;
    private Handler fetchMessageHandler = new Handler();
    private Runnable runnable;
    private CoursesRepository coursesRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTeacherMainBinding = ActivityTeacherMainBinding.inflate(getLayoutInflater());
        setContentView(activityTeacherMainBinding.getRoot());
        TeacherMainActivityViewModel teacherMainActivityViewModel = new TeacherMainActivityViewModel(activityTeacherMainBinding);

        Toolbar toolbar = (Toolbar) activityTeacherMainBinding.toolbar.getRoot();
        setSupportActionBar(toolbar);
        ImageButton menuButton = activityTeacherMainBinding.toolbar.menuIcon;
        menuButton.setOnClickListener((this::showPopupMenu));
        activityTeacherMainBinding.toolbar.setActivityTeacherMainViewModel(teacherMainActivityViewModel);
        activityTeacherMainBinding.executePendingBindings();
        activityTeacherMainBinding.setActivityTeacherMainViewModel(teacherMainActivityViewModel);
        coursesRepository = new CoursesRepository();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.teacher_fragment_container,TeacherMainFragment.newInstance())
                .setReorderingAllowed(true)
                .commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        startFetchingMessages();
    }

    @Override
    protected void onPause() {
        super.onPause();
        fetchMessageHandler.removeCallbacks(runnable);
    }

    private void fetchMessagesFromDataBase() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("teacher_id", LoginViewModel.getUserId(getApplicationContext()));
        coursesRepository.postData(new CourseRequest(CourseRequest.CourseAction.GET_MESSAGES, jsonObject), new ResultCallback<CourseResponse>() {
            @Override
            public void onSuccess(CourseResponse data) {
                if (data.getSuccess()) {
                    Type courseMessagesResponseType = new TypeToken<CourseResponse<ArrayList<UnderstandingMessage>>>() {
                    }.getType();
                    CourseResponse<ArrayList<UnderstandingMessage>> response = new Gson().fromJson(new Gson().toJson(data), courseMessagesResponseType);
                    ArrayList<UnderstandingMessage> messages = response.getData();
                    activityTeacherMainBinding.getActivityTeacherMainViewModel().setMessages(messages);
                    activityTeacherMainBinding.toolbar.navIcon.setOnClickListener((view) -> {
                        activityTeacherMainBinding.getActivityTeacherMainViewModel().setMessages(new ArrayList<>());
                        Bundle bundle = new Bundle();
                        bundle.putString(MESSAGES_KEY,new Gson().toJson(messages));
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.teacher_fragment_container, MessagesNotificationFragment.class,bundle)
                                .addToBackStack("messages_frag")
                                .commit();
                    });
                } else {
                    activityTeacherMainBinding.toolbar.navIcon.setOnClickListener((view) -> {
                        Snackbar.make(activityTeacherMainBinding.getRoot(), data.getMessage(), Snackbar.LENGTH_SHORT).show();
                    });
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void startFetchingMessages() {
        runnable = () -> {
            fetchMessagesFromDataBase();
            fetchMessageHandler.postDelayed(runnable, delay);
        };
        fetchMessageHandler.postDelayed(runnable, delay);
    }



    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_main_teacher, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }

}