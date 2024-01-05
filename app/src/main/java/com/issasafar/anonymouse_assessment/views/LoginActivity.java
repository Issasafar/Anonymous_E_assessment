package com.issasafar.anonymouse_assessment.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.issasafar.anonymouse_assessment.R;
import com.issasafar.anonymouse_assessment.databinding.ActivityLoginBinding;
import com.issasafar.anonymouse_assessment.data.models.UserAccount;
import com.issasafar.anonymouse_assessment.viewmodels.LoginViewModel;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel mLoginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        setContentView(activityLoginBinding.getRoot());
        mLoginViewModel = new LoginViewModel();
        activityLoginBinding.setLoginViewModel(mLoginViewModel);
        activityLoginBinding.executePendingBindings();
    }

    @BindingAdapter({"inputError"})
    public static void setInputError(TextInputLayout textInputLayout, String error) {
        textInputLayout.getEditText().setError(error);
    }

    @BindingAdapter({"userAccount"})
    public static void handleUserLogin(View button, UserAccount userAccount) {
        if (!Objects.equals(userAccount.getEmail(), "")) {

            Toast.makeText(button.getContext(),userAccount.getEmail()+":"+userAccount.getPassword(),Toast.LENGTH_SHORT).show();
        }


    }
}