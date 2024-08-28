package com.issasafar.anonymouse_assessment.viewmodels.teacher;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.snackbar.Snackbar;
import com.issasafar.anonymouse_assessment.BR;
import com.issasafar.anonymouse_assessment.data.models.Result;
import com.issasafar.anonymouse_assessment.data.models.Teacher;
import com.issasafar.anonymouse_assessment.data.models.login.LoggedInUser;
import com.issasafar.anonymouse_assessment.data.models.login.LoginResponse;
import com.issasafar.anonymouse_assessment.databinding.ActivityTeacherRegisterBinding;
import com.issasafar.anonymouse_assessment.viewmodels.InputValidator;
import com.issasafar.anonymouse_assessment.views.login.LoggedInUserView;
import com.issasafar.anonymouse_assessment.views.login.LoginDataSource;
import com.issasafar.anonymouse_assessment.views.login.LoginRepository;
import com.issasafar.anonymouse_assessment.views.login.LoginResult;
import com.issasafar.anonymouse_assessment.views.login.LoginViewModel;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TeacherRegisterViewModel extends BaseObservable {

    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private final String successMessage = "Registration was successful";
    private final String errorMessage = "Registration Failed";
    private int progressVisibility = View.GONE;
    private LoginRepository loginRepository;
    @Bindable
    private String toastMessage = null;
    @Bindable
    private String nameError = null;
    @Bindable
    private String emailError = null;
    @Bindable
    private String passwordError = null;
    @Bindable
    private String repeatedPasswordError = null;
    @Bindable
    private Teacher mTeacher;
    private String confirmPassword;
    private Context appContext;
    private ActivityTeacherRegisterBinding teacherRegisterBinding;

    public TeacherRegisterViewModel(Context appContext, ActivityTeacherRegisterBinding teacherRegisterBinding) {
        this.mTeacher = new Teacher("", "", "");
        this.confirmPassword = "";
        this.appContext = appContext;
        this.teacherRegisterBinding = teacherRegisterBinding;
        Executor executor = Executors.newCachedThreadPool();
        this.loginRepository = LoginRepository.getInstance(new LoginDataSource(executor));
    }


    public MutableLiveData<LoginResult> getLoginResult() {
        return this.loginResult;
    }

    public TeacherRegisterViewModel(Teacher teacher, String confirmPassword) {
        mTeacher = teacher;
        this.confirmPassword = confirmPassword;
    }

    @Bindable
    public Teacher getTeacher() {
        return this.mTeacher;
    }

    public void setTeacher(Teacher teacher) {
        this.mTeacher = teacher;
        notifyPropertyChanged(BR.teacher);
    }

    @Bindable
    public String getToastMessage() {
        return this.toastMessage;
    }

    private void setToastMessage(String message) {
        this.toastMessage = message;
        notifyPropertyChanged(BR.toastMessage);
    }

    @Bindable
    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        notifyPropertyChanged(BR.confirmPassword);
    }

    @Bindable
    public String getTeacherName() {
        return mTeacher.getName();
    }

    public void setTeacherName(String name) {
        mTeacher.setName(name);
        notifyPropertyChanged(BR.teacherName);
    }

    @Bindable
    public String getTeacherEmail() {
        return mTeacher.getEmail();
    }

    public void setTeacherEmail(String email) {
        mTeacher.setEmail(email);
        notifyPropertyChanged(BR.teacherEmail);
    }

    @Bindable
    public String getTeacherPassword() {
        return mTeacher.getPassword();
    }

    public void setTeacherPassword(String password) {
        mTeacher.setPassword(password);
        notifyPropertyChanged(BR.teacherPassword);
    }

    public void onRegisterClicked() {
        if (isInputValid()) {
            setProgressVisibility(View.VISIBLE);
            setToastMessage(successMessage + ": " + getTeacherName());
            setTeacher(new Teacher(mTeacher.getName(), mTeacher.getEmail(), mTeacher.getPassword()));
         /*   if (!Objects.equals(mTeacher.getEmail().trim(), "")) {
                Toast.makeText(getAppContext(), "invoked register " + mTeacher.getEmail(), Toast.LENGTH_LONG).show();
            }*/
            loginRepository.register(getTeacher(), result -> {
                setProgressVisibility(View.GONE);
                if (result instanceof Result.Success) {
                    LoggedInUser loggedInUser;
                    LoginResponse response = ((Result.Success<LoginResponse>) result).getData();
                    loggedInUser = new LoggedInUser(response.getUserId(), response.getUserName(), response.getEmail(), response.getPassword(), response.getSign());
                    LoginViewModel loginViewModel = new LoginViewModel(getAppContext());
                    loginViewModel.saveUserCredentials(new LoginResult(loggedInUser));
                    loginResult.postValue(new LoginResult(loggedInUser));
                } else if (result instanceof Result.Error) {
                    // failed
                    String errorMessage = ((Result.Error<LoginResponse>) result).getException().getMessage();
                    assert errorMessage != null;
                    Snackbar snackbar = Snackbar.make(teacherRegisterBinding.getRoot(), errorMessage, Snackbar.LENGTH_LONG);
                    snackbar.show();
                    loginResult.postValue(new LoginResult(errorMessage));
                }
            });
        } else {
            setNameError(InputValidator.validateName(mTeacher.getName()));
            setPasswordError(InputValidator.validatePassword(mTeacher.getPassword()));
            setEmailError(InputValidator.validateEmail(mTeacher.getEmail()));
            setRepeatedPasswordError(InputValidator.validateRepeatedPassword(mTeacher.getPassword(), getConfirmPassword()));
            setToastMessage(errorMessage);
            //setTeacher(new Teacher(mTeacher.getName(), mTeacher.getEmail(), mTeacher.getPassword()));
        }
    }

    private boolean isInputValid() {
        return (!TextUtils.isEmpty(getTeacherName()) &&
                Patterns.EMAIL_ADDRESS.matcher(getTeacherEmail().trim()).matches() &&
                getTeacherPassword().trim().length() > 5 &&
                Objects.equals(getTeacherPassword().trim(), getConfirmPassword().trim()));
    }

    @Bindable
    public String getNameError() {
        return nameError;
    }

    public void setNameError(String nameError) {
        this.nameError = nameError;
        notifyPropertyChanged(BR.nameError);
    }

    @Bindable
    public String getEmailError() {
        return emailError;
    }

    public void setEmailError(String emailError) {
        this.emailError = emailError;
        notifyPropertyChanged(BR.emailError);
    }

    @Bindable
    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
        notifyPropertyChanged(BR.passwordError);
    }

    @Bindable
    public String getRepeatedPasswordError() {
        return repeatedPasswordError;
    }

    public void setRepeatedPasswordError(String repeatedPasswordError) {
        this.repeatedPasswordError = repeatedPasswordError;
        notifyPropertyChanged(BR.repeatedPasswordError);
    }

    public Context getAppContext() {
        return appContext;
    }

    @Bindable
    public int getProgressVisibility() {
        return progressVisibility;
    }

    public void setProgressVisibility(int progressVisibility) {
        this.progressVisibility = progressVisibility;
        notifyPropertyChanged(BR.progressVisibility);
    }
}
