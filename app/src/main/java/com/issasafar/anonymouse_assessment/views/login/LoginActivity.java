package com.issasafar.anonymouse_assessment.views.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.issasafar.anonymouse_assessment.R;
import com.issasafar.anonymouse_assessment.data.models.login.UserAccount;
import com.issasafar.anonymouse_assessment.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel mLoginViewModel;
    private ProgressBar loadingProgressBar;
    private Button loginButton;
    private UserAccount userAccount;
    private Button forgotPasswordButton;
    private TextInputEditText passwordTextInput;
    private SharedPreferences sharedPreferences;

    @BindingAdapter({"inputError"})
    public static void setInputError(TextInputLayout textInputLayout, String error) {
        textInputLayout.getEditText().setError(error);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        setContentView(activityLoginBinding.getRoot());
        mLoginViewModel = new LoginViewModel(getApplicationContext());
        activityLoginBinding.setLoginViewModel(mLoginViewModel);
        activityLoginBinding.executePendingBindings();
        loadingProgressBar = activityLoginBinding.loading;
        loginButton = activityLoginBinding.loginButton;
        passwordTextInput = activityLoginBinding.passwordField;
        passwordTextInput.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginButton.performClick();
            }
            return false;
        });

        forgotPasswordButton = activityLoginBinding.forgotPasswordButton;
        forgotPasswordButton.setOnClickListener(view -> {
            Intent forgotPasswordIntent = new Intent(this, ResetPasswordActivity.class);
            startActivity(forgotPasswordIntent);
            finish();
        });
        loginButton.setOnClickListener(view -> {
            mLoginViewModel.onLoginClicked();
            if (mLoginViewModel.isInputValid()) {
                userAccount = mLoginViewModel.getUserAccount();
                loadingProgressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), userAccount.getEmail() + ":" + userAccount.getPassword(), Toast.LENGTH_SHORT).show();
                LoginViewModel loginViewModel = activityLoginBinding.getLoginViewModel();
                loginViewModel.login(userAccount.getEmail(), userAccount.getPassword());
                loginViewModel.getLoginResult().observe(this, loginResult -> {
                    if (loginResult.getSuccess() != null) {
                        loadingProgressBar.setVisibility(View.GONE);
                        loginViewModel.saveUserCredentials(loginResult);
                        //TODO() something to do with after saving the credentials :)
                    } else {
                        Toast.makeText(getApplicationContext(), loginResult.getError(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }


}