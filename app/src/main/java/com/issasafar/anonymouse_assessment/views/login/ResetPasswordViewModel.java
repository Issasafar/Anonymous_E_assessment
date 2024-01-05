package com.issasafar.anonymouse_assessment.views.login;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.issasafar.anonymouse_assessment.BR;
import com.issasafar.anonymouse_assessment.data.models.login.ResetPasswordCredentials;
import com.issasafar.anonymouse_assessment.viewmodels.InputValidator;

public class ResetPasswordViewModel extends BaseObservable {
    private ResetPasswordCredentials resetPasswordCredentials;
    private String email;
    private String userName;
    private String newPassword = null;

    public ResetPasswordCredentials.ResetPasswordDataHolder.AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(ResetPasswordCredentials.ResetPasswordDataHolder.AccountType accountType) {
        this.accountType = accountType;
    }

    private ResetPasswordCredentials.ResetPasswordDataHolder.AccountType accountType;
@Bindable
    public String getNewPasswordError() {
        return newPasswordError;
    }

    public void setNewPasswordError(String newPasswordError) {
        this.newPasswordError = newPasswordError;
        notifyPropertyChanged(BR.newPasswordError);
    }

    private String newPasswordError = null;
    private String userNameError = null;
    private String emailError = null;

    @Bindable
    public ResetPasswordCredentials getResetPasswordCredentials() {
        return resetPasswordCredentials;
    }

    public void setResetPasswordCredentials(ResetPasswordCredentials resetPasswordCredentials) {
        this.resetPasswordCredentials = resetPasswordCredentials;
        notifyPropertyChanged(BR.resetPasswordCredentials);
    }

    @Bindable
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        notifyPropertyChanged(BR.newPassword);
    }

    @Bindable
    public String getUserNameError() {
        return userNameError;
    }

    public void setUserNameError(String userNameError) {
        this.userNameError = userNameError;
        notifyPropertyChanged(BR.userNameError);
    }

    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.userName);
    }

    @Bindable
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getEmailError() {
        return emailError;
    }

    public void setEmailError(String emailError) {
        this.emailError = emailError;
        notifyPropertyChanged(BR.emailError);
    }

    public void onSubmitClicked() {
        if (isInputValid() && newPassword == null) {
            setResetPasswordCredentials(new ResetPasswordCredentials.EmailNamePair(getEmail(), getUserName()));

        } else if (isInputValid() && newPassword != null && InputValidator.validatePassword(getNewPassword()) == null) {
            setResetPasswordCredentials(new ResetPasswordCredentials.ResetPasswordDataHolder(getEmail(),getNewPassword(),));
        } else {
            setEmailError(InputValidator.validateEmail(getEmail()));
            if (newPassword != null) {
                setNewPasswordError(InputValidator.validatePassword(getNewPassword()));
            }
            setUserNameError(InputValidator.validateName(getUserName()));
        }
    }

    public boolean isInputValid() {
        return !getEmail().trim().isEmpty()
                && !getUserName().trim().isEmpty()
                && InputValidator.validateEmail(getEmail()) == null
                && InputValidator.validateName(getUserName()) == null;
    }
}
