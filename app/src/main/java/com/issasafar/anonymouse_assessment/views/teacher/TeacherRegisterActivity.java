package com.issasafar.anonymouse_assessment.views.teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.issasafar.anonymouse_assessment.R;
import com.issasafar.anonymouse_assessment.databinding.ActivityTeacherRegisterBinding;
import com.issasafar.anonymouse_assessment.data.models.Teacher;
import com.issasafar.anonymouse_assessment.viewmodels.InputValidator;
import com.issasafar.anonymouse_assessment.viewmodels.teacher.TeacherRegisterViewModel;

import java.util.Objects;

public class TeacherRegisterActivity extends AppCompatActivity {
private ActivityTeacherRegisterBinding mBinding;
private TeacherRegisterViewModel mTeacherRegisterViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTeacherRegisterBinding activityTeacherRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_teacher_register);
        mTeacherRegisterViewModel = new TeacherRegisterViewModel();
        activityTeacherRegisterBinding.setTeacherRegister(mTeacherRegisterViewModel);
        activityTeacherRegisterBinding.executePendingBindings();
        activityTeacherRegisterBinding.emailInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
               mTeacherRegisterViewModel.setEmailError(InputValidator.validateEmail(mTeacherRegisterViewModel.getTeacherEmail()));

                activityTeacherRegisterBinding.emailInput.getEditText().setError(mTeacherRegisterViewModel.getEmailError());
            }
        });
        activityTeacherRegisterBinding.nameInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
             mTeacherRegisterViewModel.setNameError(InputValidator.validateName(mTeacherRegisterViewModel.getTeacherName()));
             activityTeacherRegisterBinding.nameInput.getEditText().setError(mTeacherRegisterViewModel.getNameError());
            }
        });
        activityTeacherRegisterBinding.passwordInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
            mTeacherRegisterViewModel.setPasswordError(InputValidator.validatePassword(mTeacherRegisterViewModel.getTeacherPassword()));
            activityTeacherRegisterBinding.passwordInput.getEditText().setError(mTeacherRegisterViewModel.getPasswordError());
            }
        });
        activityTeacherRegisterBinding.confirmPasswordInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
            mTeacherRegisterViewModel.setRepeatedPasswordError(InputValidator.validateRepeatedPassword(mTeacherRegisterViewModel.getTeacherPassword(),mTeacherRegisterViewModel.getConfirmPassword()));
            activityTeacherRegisterBinding.confirmPasswordInput.getEditText().setError(mTeacherRegisterViewModel.getRepeatedPasswordError());
            }
        });
    }

    @BindingAdapter({"inputError"})
    public static void setInputError(TextInputLayout textInputLayout, String error) {
       textInputLayout.getEditText().setError(error);
    }

    @BindingAdapter({"teacher"})
    public static void runMe(View view, Teacher teacher) {
        // TODO() handle registration
        if (!Objects.equals(teacher.getEmail().trim(), "")) {
            Toast.makeText(view.getContext(), "invoked register " + teacher.getEmail(), Toast.LENGTH_LONG).show();
        }
    }
    @BindingAdapter({"toastMessage"})
    public static void runMe(View view, String message) {
//       if(message != null)
//           Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG).show();

    }
}