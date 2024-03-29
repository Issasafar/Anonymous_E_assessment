package com.issasafar.anonymouse_assessment.viewmodels.student;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.issasafar.anonymouse_assessment.BR;
import com.issasafar.anonymouse_assessment.data.models.Student;
import com.issasafar.anonymouse_assessment.viewmodels.InputValidator;

import java.util.Objects;

public class StudentRegisterViewModel extends BaseObservable {
    private String successMessage = "Registration was successful";
    private String errorMessage = "Registration failed";
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
    public StudentRegisterViewModel() {
        this.mStudent = new Student("","","","");
        this.confirmPassword = "";
    }
    public StudentRegisterViewModel(Student student, String confirmPassword) {
        mStudent = student;
        this.confirmPassword = confirmPassword;
    }

    public void setStudentName(String name) {
        mStudent.setName(name);
        notifyPropertyChanged(BR.studentName);
    }

    public void setStudentEmail(String email) {
        mStudent.setEmail(email);
        notifyPropertyChanged(BR.studentEmail);
    }

    public void setStudentPassword(String password) {
        mStudent.setPassword(password);
        notifyPropertyChanged(BR.studentPassword);

    }

    public void setStudentSign(String sign) {
        mStudent.setSign(sign);
        notifyPropertyChanged(BR.studentSign);
    }

    public void setConfirmPassword(String password) {
        this.confirmPassword = password;
        notifyPropertyChanged(BR.confirmPassword);
    }

    @Bindable
    public String getToastMessage() {
        return this.toastMessage;
    }
    @Bindable
    public String getConfirmPassword() {
        return this.confirmPassword;
    }
    @Bindable
    public String getStudentName() {
        return mStudent.getName();
    }
    @Bindable
    public String getStudentEmail() {
        return mStudent.getEmail();
    }
    @Bindable
    public String getStudentPassword() {
        return mStudent.getPassword();
    }
    @Bindable
    public String getStudentSign() {
        return mStudent.getSign();
    }

    @Bindable
    public Student getStudent() {
        return this.mStudent;
    }

    public void setStudent(Student student) {
        this.mStudent = student;
        notifyPropertyChanged(BR.student);
    }
    public void onRegisterClicked() {
        if (isInputValid()) {
            setToastMessage(successMessage + ": "+getStudentName());
            setStudent(new Student(mStudent.getName(), mStudent.getEmail(), mStudent.getPassword(), mStudent.getSign()));
        }else {
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

    private void setToastMessage(String message) {
        this.toastMessage = message;
        notifyPropertyChanged(BR.toastMessage);
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
}

