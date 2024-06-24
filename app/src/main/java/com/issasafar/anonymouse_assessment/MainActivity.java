package com.issasafar.anonymouse_assessment;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
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
        Button signInButton = mActivityMainBinding.signInButton;
        //TODO() start activity for result
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
          if(result.getResultCode() == RESULT_OK) {
             // start the corresponding activity
              startActivity(result.getData());
              finish();
          }else{
              //TODO() test for result code and show error message
              Snackbar snackbar = Snackbar.make(mActivityMainBinding.getRoot(), "something went wrong", Snackbar.LENGTH_LONG);
              snackbar.show();
          }
        });
        signInButton.setOnClickListener((view)->{
           Intent intent = new Intent(this, LoginActivity.class);
           activityResultLauncher.launch(intent);
        });
        teacherRegister.setOnClickListener((view)->{
            Intent intent = new Intent(this, TeacherRegisterActivity.class);
            activityResultLauncher.launch(intent);
        });
        studentRegister.setOnClickListener((view)->{
            Intent intent = new Intent(this, StudentRegisterActivity.class);
            activityResultLauncher.launch(intent);
        });
    }
}