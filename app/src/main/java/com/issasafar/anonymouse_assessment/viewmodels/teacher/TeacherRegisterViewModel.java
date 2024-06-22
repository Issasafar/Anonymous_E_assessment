package com.issasafar.anonymouse_assessment.viewmodels.teacher;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.issasafar.anonymouse_assessment.BR;
import com.issasafar.anonymouse_assessment.data.models.Teacher;
import com.issasafar.anonymouse_assessment.viewmodels.InputValidator;

import java.util.Objects;

public class TeacherRegisterViewModel extends BaseObservable {

    private final String successMessage = "Registration was successful";
    private final String errorMessage = "Registration Failed";
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

    public TeacherRegisterViewModel(Context appContext) {
       this.mTeacher = new Teacher("", "", "");
this.appContext = appContext;
        this.confirmPassword = "";
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
            setToastMessage(successMessage + ": " + getTeacherName());
            setTeacher(new Teacher(mTeacher.getName(), mTeacher.getEmail(), mTeacher.getPassword()));
            if (!Objects.equals(mTeacher.getEmail().trim(), "")) {
                Toast.makeText(getAppContext(), "invoked register " + mTeacher.getEmail(), Toast.LENGTH_LONG).show();
            }
        } else {
            setNameError(InputValidator.validateName(mTeacher.getName()));
            setPasswordError(InputValidator.validatePassword(mTeacher.getPassword()));
            setEmailError(InputValidator.validateEmail(mTeacher.getEmail()));
            setRepeatedPasswordError(InputValidator.validateRepeatedPassword(mTeacher.getPassword(),getConfirmPassword()));
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
}
