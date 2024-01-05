package com.issasafar.anonymouse_assessment.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.issasafar.anonymouse_assessment.R;
import com.issasafar.anonymouse_assessment.data.models.UserAccount;
import com.issasafar.anonymouse_assessment.databinding.ActivityLoginBinding;
import com.issasafar.anonymouse_assessment.views.login.LoggedInUserView;
import com.issasafar.anonymouse_assessment.views.login.LoginViewModel;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel mLoginViewModel;
    private ProgressBar loadingProgressBar;
    private Button loginButton;
    private UserAccount userAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        setContentView(activityLoginBinding.getRoot());
        mLoginViewModel = new LoginViewModel();
        activityLoginBinding.setLoginViewModel(mLoginViewModel);
        activityLoginBinding.executePendingBindings();
        loadingProgressBar = activityLoginBinding.loading;
        loginButton = activityLoginBinding.loginButton;
        loginButton.setOnClickListener(view -> {
            mLoginViewModel.onLoginClicked();
            if (mLoginViewModel.isInputValid()) {
                userAccount = mLoginViewModel.getUserAccount();
                loadingProgressBar.setVisibility(View.VISIBLE);

                Toast.makeText(getApplicationContext(), userAccount.getEmail() + ":" + userAccount.getPassword(), Toast.LENGTH_SHORT).show();
            activityLoginBinding.getLoginViewModel().login(userAccount.getEmail(), userAccount.getPassword());
//            LoggedInUserView loggedInUserView = activityLoginBinding.getLoginViewModel().getLoginResult().getValue().getSuccess();
//            Toast.makeText(getApplicationContext(), loggedInUserView.getDisplayName(), Toast.LENGTH_LONG);
            String message = activityLoginBinding.getLoginViewModel().getLoginResult().toString();
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });

    }

    @BindingAdapter({"inputError"})
    public static void setInputError(TextInputLayout textInputLayout, String error) {
        textInputLayout.getEditText().setError(error);
    }



}