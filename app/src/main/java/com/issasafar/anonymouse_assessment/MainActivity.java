package com.issasafar.anonymouse_assessment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.issasafar.anonymouse_assessment.databinding.ActivityMainBinding;
import com.issasafar.anonymouse_assessment.views.login.LoginActivity;
import com.issasafar.anonymouse_assessment.views.login.ResetPasswordActivity;
import com.issasafar.anonymouse_assessment.views.login.ResetPasswordViewModel;
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
        Button teacherRegister = mActivityMainBinding.teacherButton;
        Button studentRegister = mActivityMainBinding.studentButton;
        teacherRegister.setOnClickListener((view)->{
            Intent intent = new Intent(this, TeacherRegisterActivity.class);
            startActivity(intent);
        });
        studentRegister.setOnClickListener((view)->{
            Intent intent = new Intent(this, StudentRegisterActivity.class);
            startActivity(intent);
        });
    }
}