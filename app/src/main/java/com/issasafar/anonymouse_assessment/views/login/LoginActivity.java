package com.issasafar.anonymouse_assessment.views.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.issasafar.anonymouse_assessment.R;
import com.issasafar.anonymouse_assessment.data.models.login.UserAccount;
import com.issasafar.anonymouse_assessment.databinding.ActivityLoginBinding;
import com.issasafar.anonymouse_assessment.views.teacher.TeacherMainActivity;

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
        if (error != null && !"".equals(error)) {
            textInputLayout.setEndIconVisible(false);
        } else {
            textInputLayout.setEndIconVisible(true);
        }
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
            //todo() finish this activity if the action is not success
        });
        loginButton.setOnClickListener(view -> {
            mLoginViewModel.onLoginClicked();
            if (mLoginViewModel.isInputValid()) {
                userAccount = mLoginViewModel.getUserAccount();
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loadingProgressBar.setVisibility(View.VISIBLE);

                LoginViewModel loginViewModel = activityLoginBinding.getLoginViewModel();
                loginViewModel.login(userAccount.getEmail(), userAccount.getPassword());
                loginViewModel.getLoginResult().observe(this, loginResult -> {
                    loadingProgressBar.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    if (loginResult.getSuccess() != null) {
                        loginViewModel.saveUserCredentials(loginResult);
                        //TODO() do something after saving the credentials :)
                        if(loginResult.getSuccess().getSign() == null || "".equals(loginResult.getSuccess().getSign())){
                            // no sign so it's a teacher
                            setResult(RESULT_OK, new Intent(this, TeacherMainActivity.class));
                            finish();
                        }
                    } else {
                        assert loginResult.getError() != null;
                        Snackbar snackbar = Snackbar.make(activityLoginBinding.getRoot(), loginResult.getError(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                });
            }
        });

    }


}