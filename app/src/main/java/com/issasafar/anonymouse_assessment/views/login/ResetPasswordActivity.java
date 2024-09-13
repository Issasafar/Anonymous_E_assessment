package com.issasafar.anonymouse_assessment.views.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.issasafar.anonymouse_assessment.R;
import com.issasafar.anonymouse_assessment.data.models.login.ResetPasswordCredentials;
import com.issasafar.anonymouse_assessment.databinding.ActivityForgotPasswordBinding;

public class ResetPasswordActivity extends AppCompatActivity {
    private ResetPasswordViewModel resetPasswordViewModel;

    private Button submitButton;

    private TextInputEditText newPasswordTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityForgotPasswordBinding activityForgotPasswordBinding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);
        setContentView(activityForgotPasswordBinding.getRoot());
        resetPasswordViewModel = new ResetPasswordViewModel(getApplicationContext(),activityForgotPasswordBinding,getWindow());
        activityForgotPasswordBinding.setResetPasswordViewModel(resetPasswordViewModel);
        activityForgotPasswordBinding.executePendingBindings();
        submitButton = activityForgotPasswordBinding.submitButton;
        newPasswordTextInput = activityForgotPasswordBinding.newPasswordField;
        activityForgotPasswordBinding.getResetPasswordViewModel().getProgressVisibilityLiveData().observe(this, integer -> {
            if (integer == View.VISIBLE) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            } else {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });
        newPasswordTextInput.setOnEditorActionListener((textView, i, keyEvent) ->{
        if (i == EditorInfo.IME_ACTION_DONE) {
            submitButton.performClick();
        }
        return  false;
    });

    }
    @BindingAdapter({"resetPasswordInputError"})
    public static void setInputError(TextInputLayout textInputLayout, String error) {
        textInputLayout.getEditText().setError(error);
    }
}