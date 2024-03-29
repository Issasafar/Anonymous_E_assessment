package com.issasafar.anonymouse_assessment.views.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
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
        resetPasswordViewModel = new ResetPasswordViewModel();
        activityForgotPasswordBinding.setResetPasswordViewModel(resetPasswordViewModel);
        activityForgotPasswordBinding.executePendingBindings();
        newPasswordTextInput = activityForgotPasswordBinding.newPasswordField;
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