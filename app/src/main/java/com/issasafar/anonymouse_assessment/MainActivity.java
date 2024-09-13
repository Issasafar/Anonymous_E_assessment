package com.issasafar.anonymouse_assessment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.issasafar.anonymouse_assessment.databinding.ActivityMainBinding;
import com.issasafar.anonymouse_assessment.views.login.LoginActivity;
import com.issasafar.anonymouse_assessment.views.login.LoginViewModel;
import com.issasafar.anonymouse_assessment.views.student.StudentMainActivity;
import com.issasafar.anonymouse_assessment.views.student.StudentRegisterActivity;
import com.issasafar.anonymouse_assessment.views.teacher.TeacherMainActivity;
import com.issasafar.anonymouse_assessment.views.teacher.TeacherRegisterActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mActivityMainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mActivityMainBinding.getRoot());

        // More descriptive variable names
        Button registerTeacherButton = mActivityMainBinding.teacherButton;
        Button registerStudentButton = mActivityMainBinding.studentButton;
        Button signInButton = mActivityMainBinding.signInButton;
        if (Integer.parseInt(LoginViewModel.getUserId(getApplicationContext())) != -1) {
            Intent i;
            if (!"".equals(LoginViewModel.getStudentSign(getApplicationContext()))) {
                i = new Intent(this, StudentMainActivity.class);
            } else {
               i = new Intent(this, TeacherMainActivity.class);
            }
            startActivity(i);
            finish();
        }
        // Using a lambda for the ActivityResultLauncher callback
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        startActivity(result.getData());
                        finish(); // Close the current activity after starting the new one
                    } else {
                        // Handle different result codes with specific error messages
                        int resultCode = result.getResultCode();
                        String errorMessage = getErrorMessage(resultCode); // Helper method to get error messageSnackbar.make(mActivityMainBinding.getRoot(), errorMessage, Snackbar.LENGTH_LONG).show();
                        Snackbar snackbar = Snackbar.make(mActivityMainBinding.getRoot(), errorMessage, Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                });

        signInButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            activityResultLauncher.launch(intent);
        });

        registerTeacherButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, TeacherRegisterActivity.class);
            activityResultLauncher.launch(intent);
        });

        registerStudentButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, StudentRegisterActivity.class);
            activityResultLauncher.launch(intent);
        });
    }

    // Helper method to provide specific error messages
    private String getErrorMessage(int resultCode) {
        switch (resultCode) {
            case RESULT_CANCELED:
//                return "Action canceled";
            case RESULT_FIRST_USER: // Example: Custom result code
                return "Registration failed. Please try again.";
            default:
                return "Something went wrong";
        }
    }
}