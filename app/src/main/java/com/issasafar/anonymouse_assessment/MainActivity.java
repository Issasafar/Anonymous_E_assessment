package com.issasafar.anonymouse_assessment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.issasafar.anonymouse_assessment.databinding.ActivityMainBinding;
import com.issasafar.anonymouse_assessment.views.LoginActivity;
import com.issasafar.anonymouse_assessment.views.student.StudentRegisterActivity;
import com.issasafar.anonymouse_assessment.views.teacher.TeacherRegisterActivity;

public class MainActivity extends AppCompatActivity {
private ActivityMainBinding mActivityMainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View uiRoot = mActivityMainBinding.getRoot();
        setContentView(uiRoot);
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);

    }
}