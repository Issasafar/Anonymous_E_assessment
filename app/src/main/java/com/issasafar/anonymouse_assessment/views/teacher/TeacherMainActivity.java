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
import com.issasafar.anonymouse_assessment.R;
import com.issasafar.anonymouse_assessment.data.models.common.CourseRequest;
import com.issasafar.anonymouse_assessment.data.models.common.CourseResponse;
import com.issasafar.anonymouse_assessment.databinding.ActivityTeacherMainBinding;
import com.issasafar.anonymouse_assessment.viewmodels.teacher.TeacherMainActivityViewModel;
import com.issasafar.anonymouse_assessment.views.common.CoursesRepository;
import com.issasafar.anonymouse_assessment.views.common.ResultCallback;
import com.issasafar.anonymouse_assessment.views.login.LoginViewModel;
import com.issasafar.anonymouse_assessment.views.teacher.ui.main.teacher.TeacherMainFragment;


public class TeacherMainActivity extends AppCompatActivity {
    public static final String COURSES_KEY = "courses";
    private static final String COURSES_PREF_FILE = "courses_shared_pref_file";
    private String userId;
    private Handler fetchMessageHandler = new Handler();
    private Runnable runnable;
    private final int delay = 60000;
    private CoursesRepository coursesRepository;


    ActivityTeacherMainBinding activityTeacherMainBinding;

    //todo(optional) remove course from shared preference when the app stops
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTeacherMainBinding = ActivityTeacherMainBinding.inflate(getLayoutInflater());
        setContentView(activityTeacherMainBinding.getRoot());
        TeacherMainActivityViewModel teacherMainActivityViewModel = new TeacherMainActivityViewModel(activityTeacherMainBinding);
        ResultCallback<CourseResponse> courseCallback = getCourseResponseResultCallback();
        userId = LoginViewModel.getUserId(getApplicationContext());
        if (savedInstanceState == null) {
             coursesRepository = new CoursesRepository();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("owner_id",userId);
            coursesRepository.getData(new CourseRequest(CourseRequest.CourseAction.GET_COURSES, jsonObject), courseCallback);
        }
        Toolbar toolbar = (Toolbar) activityTeacherMainBinding.toolbar.getRoot();
        setSupportActionBar(toolbar);
        ImageButton menuButton = activityTeacherMainBinding.toolbar.menuIcon;
        menuButton.setOnClickListener((this::showPopupMenu));
        activityTeacherMainBinding.toolbar.setActivityTeacherMainViewModel(teacherMainActivityViewModel);

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
        //todo() replace with actual user id
        jsonObject.addProperty("teacher_id",1);
        coursesRepository.postData(new CourseRequest(CourseRequest.CourseAction.GET_MESSAGES, jsonObject), new ResultCallback<CourseResponse>() {
            @Override
            public void onSuccess(CourseResponse data) {
                if (data.getSuccess()) {
                    activityTeacherMainBinding.toolbar.getActivityTeacherMainViewModel().setNotificationCount(2);
                    //todo() start the notification fragment
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
            fetchMessageHandler.postDelayed(runnable,delay);
        };
        fetchMessageHandler.postDelayed(runnable, delay);
    }
    private @NonNull ResultCallback<CourseResponse> getCourseResponseResultCallback() {
        ResultCallback<CourseResponse> courseCallback = new ResultCallback<CourseResponse>() {
            @Override
            public void onSuccess(CourseResponse data) {
                if (data.getSuccess()) {
                    saveCourseResponse(data);
                    Bundle bundle = new Bundle();
                    bundle.putString(COURSES_KEY, new Gson().toJson(data));
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .add(R.id.teacher_fragment_container, TeacherMainFragment.class, bundle)
                            .commit();
                }
            }

            @Override
            public void onError(Exception e) {
                if (e.getCause().toString().contains("java.net.ConnectException")) {
                    Snackbar.make(activityTeacherMainBinding.getRoot(), "Unable to fetch courses (Connection error)", Snackbar.LENGTH_SHORT).show();
                } else if (e.getCause().toString().contains("java.net.SocketTimeoutException")) {
                    Snackbar.make(activityTeacherMainBinding.getRoot(), "Unable to connect to the server", Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(activityTeacherMainBinding.getRoot(), "Unknown error", Snackbar.LENGTH_SHORT).show();
                }

                Bundle bundle = new Bundle();
                bundle.putString(COURSES_KEY, getCourseResponseString());
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.teacher_fragment_container, TeacherMainFragment.class, bundle)
                        .commit();
            }
        };
        return courseCallback;
    }

    private void saveCourseResponse(CourseResponse data) {
        SharedPreferences sp = getApplicationContext().getSharedPreferences(COURSES_PREF_FILE, MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();
        spEditor.putString(COURSES_KEY, new Gson().toJson(data));
        spEditor.apply();
    }

    private String getCourseResponseString() {
        SharedPreferences sp = getApplicationContext().getSharedPreferences(COURSES_PREF_FILE, MODE_PRIVATE);
        return sp.getString(COURSES_KEY, "empty");

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