package com.issasafar.anonymouse_assessment.views.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.issasafar.anonymouse_assessment.views.teacher.ui.main.teacher.TeacherMainFragment;

public class TeacherMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, TeacherMainFragment.newInstance())
                    .commitNow();
        }
    }
}