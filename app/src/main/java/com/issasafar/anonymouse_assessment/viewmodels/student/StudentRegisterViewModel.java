package com.issasafar.anonymouse_assessment.viewmodels.student;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.snackbar.Snackbar;
import com.issasafar.anonymouse_assessment.BR;
import com.issasafar.anonymouse_assessment.data.models.Result;
import com.issasafar.anonymouse_assessment.data.models.Student;
import com.issasafar.anonymouse_assessment.data.models.login.LoggedInUser;
import com.issasafar.anonymouse_assessment.data.models.login.LoginResponse;
import com.issasafar.anonymouse_assessment.databinding.ActivityStudentRegisterBinding;
import com.issasafar.anonymouse_assessment.viewmodels.InputValidator;
import com.issasafar.anonymouse_assessment.views.login.LoginDataSource;
import com.issasafar.anonymouse_assessment.views.login.LoginRepository;
import com.issasafar.anonymouse_assessment.views.login.LoginResult;
import com.issasafar.anonymouse_assessment.views.login.LoginViewModel;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class StudentRegisterViewModel extends BaseObservable {
    private String successMessage = "Registration was successful";
    private String errorMessage = "Registration failed";
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private MutableLiveData<Integer> progressVisibility = new MutableLiveData<>(View.GONE);
    private LoginRepository loginRepository;
    @Bindable
    private String toastMessage = null;
    @Bindable
    private Student mStudent;
    private String confirmPassword;
    @Bindable
    private String nameError = null;

    @Bindable
    private String emailError = null;
    @Bindable
    private String passwordError = null;
    @Bindable
    private String repeatedPasswordError = null;
    @Bindable
    private String signError = null;
    private Window window;
    private Context appContext;
    private ActivityStudentRegisterBinding studentRegisterBinding;

    public StudentRegisterViewModel(Context appContext, ActivityStudentRegisterBinding studentRegisterBinding, Window window) {
        this.mStudent = new Student("", "", "", "");
        this.confirmPassword = "";
        this.appContext = appContext;
        this.studentRegisterBinding = studentRegisterBinding;
        Executor executor = Executors.newCachedThreadPool();
        this.loginRepository = LoginRepository.getInstance(new LoginDataSource(executor));
        this.window = window;
    }

    public StudentRegisterViewModel(Student student, String confirmPassword) {
        mStudent = student;
        this.confirmPassword = confirmPassword;
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

    public void setConfirmPassword(String password) {
        this.confirmPassword = password;
        notifyPropertyChanged(BR.confirmPassword);
    }

    @Bindable
    public String getStudentName() {
        return mStudent.getName();
    }

    public void setStudentName(String name) {
        mStudent.setName(name);
        notifyPropertyChanged(BR.studentName);
    }

    @Bindable
    public String getStudentEmail() {
        return mStudent.getEmail();
    }

    public void setStudentEmail(String email) {
        mStudent.setEmail(email);
        notifyPropertyChanged(BR.studentEmail);
    }

    @Bindable
    public String getStudentPassword() {
        return mStudent.getPassword();
    }

    public void setStudentPassword(String password) {
        mStudent.setPassword(password);
        notifyPropertyChanged(BR.studentPassword);

    }

    @Bindable
    public String getStudentSign() {
        return mStudent.getSign();
    }

    public void setStudentSign(String sign) {
        mStudent.setSign(sign);
        notifyPropertyChanged(BR.studentSign);
    }

    @Bindable
    public Student getStudent() {
        return this.mStudent;
    }

    public void setStudent(Student student) {
        this.mStudent = student;
        notifyPropertyChanged(BR.student);
    }

    public Student getStudentAndEnableWindow() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        return getStudent();
    }

    public MutableLiveData<LoginResult> getLoginResult() {
        return this.loginResult;
    }

    public void onRegisterClicked() {
        if (isInputValid()) {
            setProgressVisibility(View.VISIBLE);
            setStudent(new Student(mStudent.getName(), mStudent.getEmail(), mStudent.getPassword(), mStudent.getSign()));
            loginRepository.register(getStudentAndEnableWindow(), result -> {
                setProgressVisibility(View.GONE);
                if (result instanceof Result.Success) {
                    LoggedInUser loggedInUser;
                    LoginResponse response = ((Result.Success<LoginResponse>) result).getData();
                    loggedInUser = new LoggedInUser(response.getUserId(), response.getUserName(), response.getEmail(), response.getPassword(), response.getSign());
                    LoginViewModel loginViewModel = new LoginViewModel(getAppContext());
                    loginViewModel.saveUserCredentials(new LoginResult(loggedInUser));
                    setToastMessage(successMessage + ": " + getStudentName());
                    loginResult.postValue(new LoginResult(loggedInUser));
                } else if (result instanceof Result.Error) {
                    // failed
                    String errorMessage = ((Result.Error<LoginResponse>) result).getException().getMessage();
                    assert errorMessage != null;
                    Snackbar snackbar = Snackbar.make(studentRegisterBinding.getRoot(), errorMessage, Snackbar.LENGTH_LONG);
                    snackbar.show();
                    loginResult.postValue(new LoginResult(errorMessage));
                }
            });
        } else {
            setNameError(InputValidator.validateName(mStudent.getName()));
            setEmailError(InputValidator.validateEmail(mStudent.getEmail()));
            setPasswordError(InputValidator.validatePassword(mStudent.getPassword()));
            setRepeatedPasswordError(InputValidator.validateRepeatedPassword(mStudent.getPassword(), getConfirmPassword()));
            setSignError(InputValidator.validateSign(mStudent.getSign()));
            setToastMessage(errorMessage);
        }
    }

    private boolean isInputValid() {
        return (!TextUtils.isEmpty(getStudentName().trim()) &&
                !TextUtils.isEmpty(getStudentSign().trim()) &&
                Patterns.EMAIL_ADDRESS.matcher(getStudentEmail().trim()).matches() &&
                getStudentPassword().trim().length() > 5 &&
                Objects.equals(getStudentPassword().trim(), getConfirmPassword().trim()));
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

    @Bindable
    public String getSignError() {
        return signError;
    }

    public void setSignError(String signError) {
        this.signError = signError;
        notifyPropertyChanged(BR.signError);
    }

    @Bindable
    public int getProgressVisibility() {
        return progressVisibility.getValue();
    }

    public MutableLiveData<Integer> getLiveProgressVisibility() {
        return progressVisibility;
    }

    public void setProgressVisibility(int progressVisibility) {
        this.progressVisibility.postValue(progressVisibility);;
        notifyPropertyChanged(BR.progressVisibility);
    }

    public Context getAppContext() {
        return appContext;
    }
}

