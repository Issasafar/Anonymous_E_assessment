package com.issasafar.anonymouse_assessment.viewmodels;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.issasafar.anonymouse_assessment.data.models.UserAccount;

public class LoginViewModel extends BaseObservable {
    private UserAccount mUserAccount;
    private String passwordError = null;
    private String emailError = null;


    public LoginViewModel() {
        this.mUserAccount = new UserAccount("","");
    }
    @Bindable
    public UserAccount getUserAccount() {
        return this.mUserAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.mUserAccount = userAccount;
        notifyPropertyChanged(BR.userAccount);
    }
    @Bindable
    public String getEmail() {
        return this.mUserAccount.getEmail();
    }

    public void setEmail(String email) {
        this.mUserAccount.setEmail(email);

            setEmailError(InputValidator.validateEmail(mUserAccount.getEmail()));

        notifyPropertyChanged(BR.email);

    }

    public void setPassword(String password) {
        this.mUserAccount.setPassword(password);

        setPasswordError(InputValidator.validatePassword(mUserAccount.getPassword()));

        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getPassword() {
        return this.mUserAccount.getPassword();
    }

    @Bindable
    public String getPasswordError() {
        return this.passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
        notifyPropertyChanged(BR.passwordError);
    }

    @Bindable
    public String getEmailError() {
        return this.emailError;
    }

    public void setEmailError(String emailError) {
        this.emailError = emailError;
        notifyPropertyChanged(BR.emailError);
    }

    public void onLoginClicked() {
        if (isInputValid()) {
            setUserAccount(new UserAccount(mUserAccount.getEmail(), mUserAccount.getPassword()));
        }else{
            setEmailError(InputValidator.validateEmail(mUserAccount.getEmail()));
            setPasswordError(InputValidator.validatePassword(mUserAccount.getPassword()));
        }
    }

    private boolean isInputValid() {
        return !getEmail().trim().isEmpty()
                && !getPassword().trim().isEmpty()
                && InputValidator.validateEmail(mUserAccount.getEmail()) == null
                && InputValidator.validatePassword(mUserAccount.getPassword()) == null;
    }
}
