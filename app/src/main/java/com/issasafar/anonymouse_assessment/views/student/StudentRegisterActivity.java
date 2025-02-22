package com.issasafar.anonymouse_assessment.views.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.issasafar.anonymouse_assessment.R;
import com.issasafar.anonymouse_assessment.databinding.ActivityStudentRegisterBinding;
import com.issasafar.anonymouse_assessment.data.models.Student;
import com.issasafar.anonymouse_assessment.viewmodels.InputValidator;
import com.issasafar.anonymouse_assessment.viewmodels.student.StudentRegisterViewModel;

import java.util.Objects;

public class StudentRegisterActivity extends AppCompatActivity {
    private StudentRegisterViewModel mStudentRegisterViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityStudentRegisterBinding activityStudentRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_student_register);
        mStudentRegisterViewModel = new StudentRegisterViewModel(getApplicationContext(), activityStudentRegisterBinding,getWindow());
        activityStudentRegisterBinding.setStudentRegister(mStudentRegisterViewModel);
        activityStudentRegisterBinding.executePendingBindings();
        activityStudentRegisterBinding.nameInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mStudentRegisterViewModel.setNameError(InputValidator.validateName(mStudentRegisterViewModel.getStudentName()));
                activityStudentRegisterBinding.nameInput.getEditText().setError(mStudentRegisterViewModel.getNameError());

            }
        });
        activityStudentRegisterBinding.emailInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mStudentRegisterViewModel.setEmailError(InputValidator.validateEmail(mStudentRegisterViewModel.getStudentEmail()));
                activityStudentRegisterBinding.emailInput.getEditText().setError(mStudentRegisterViewModel.getEmailError());
            }
        });
        activityStudentRegisterBinding.passwordInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
             mStudentRegisterViewModel.setPasswordError(InputValidator.validatePassword(mStudentRegisterViewModel.getStudentPassword()));
             activityStudentRegisterBinding.passwordInput.getEditText().setError(mStudentRegisterViewModel.getPasswordError());
             activityStudentRegisterBinding.passwordInput.setEndIconVisible(activityStudentRegisterBinding.passwordInput.getError() == null);
            }
        });
        activityStudentRegisterBinding.confirmPasswordInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mStudentRegisterViewModel.setRepeatedPasswordError(InputValidator.validateRepeatedPassword(mStudentRegisterViewModel.getStudentPassword(), mStudentRegisterViewModel.getConfirmPassword()));
                activityStudentRegisterBinding.confirmPasswordInput.getEditText().setError(mStudentRegisterViewModel.getRepeatedPasswordError());
                activityStudentRegisterBinding.confirmPasswordInput.setEndIconVisible(activityStudentRegisterBinding.confirmPasswordInput.getError() == null);
            }
        });
        activityStudentRegisterBinding.YourSignInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mStudentRegisterViewModel.setSignError(InputValidator.validateSign(mStudentRegisterViewModel.getStudentSign()));
                activityStudentRegisterBinding.YourSignInput.getEditText().setError(mStudentRegisterViewModel.getSignError());
            }
        });
        mStudentRegisterViewModel.getLoginResult().observe(this,loginResult -> {
            if (loginResult.getSuccess() != null) {
                setResult(RESULT_OK,new Intent(this, StudentMainActivity.class));
                finish();
            }
        });
        activityStudentRegisterBinding.getStudentRegister().getLiveProgressVisibility().observe(this,(integer -> {
            if (integer == View.VISIBLE) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            } else {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        }));
    }


    @BindingAdapter({"toastMessage"})
    public static void runMe(View view, String message) {
//        if (message != null)
//            Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG).show();
    }
}
