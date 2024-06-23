package com.issasafar.anonymouse_assessment.views.login;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;


import com.google.gson.Gson;
import com.issasafar.anonymouse_assessment.BR;
import com.issasafar.anonymouse_assessment.data.models.login.ResetPasswordCredentials;
import com.issasafar.anonymouse_assessment.data.models.login.SuccessMessagePair;
import com.issasafar.anonymouse_assessment.viewmodels.InputValidator;


import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordViewModel extends BaseObservable {

    private ResetPasswordCredentials resetPasswordCredentials;
    private String email;
    private String userName;
    private String newPassword = null;

    private int progressVisibility = View.GONE;
    private int newPasswordTextInputLayoutVisibility = View.GONE;
    private ResetPasswordCredentials.ResetPasswordDataHolder.AccountType accountType;
    private String newPasswordError = null;
    private String userNameError = null;
    private String emailError = null;


    public ResetPasswordViewModel() {
        this.email = "";
        this.userName = "";
    }

    @Bindable
    public int getProgressVisibility() {
        return progressVisibility;
    }

    public void setProgressVisibility(int progressVisibility) {
        this.progressVisibility = progressVisibility;
        notifyPropertyChanged(BR.progressVisibility);
    }

    @Bindable

    public int getNewPasswordTextInputLayoutVisibility() {
        return newPasswordTextInputLayoutVisibility;
    }

    public void setNewPasswordTextInputLayoutVisibility(int newPasswordTextInputLayoutVisibility) {
        this.newPasswordTextInputLayoutVisibility = newPasswordTextInputLayoutVisibility;
        notifyPropertyChanged(BR.newPasswordTextInputLayoutVisibility);
    }

    @Bindable
    public ResetPasswordCredentials.ResetPasswordDataHolder.AccountType getAccountType() {
        return this.accountType;
    }

    public void setAccountType(ResetPasswordCredentials.ResetPasswordDataHolder.AccountType accountType) {
        this.accountType = accountType;
        notifyPropertyChanged(BR.accountType);
    }

    @Bindable
    public String getNewPasswordError() {
        return this.newPasswordError;
    }

    public void setNewPasswordError(String newPasswordError) {
        this.newPasswordError = newPasswordError;
        notifyPropertyChanged(BR.newPasswordError);
    }

    @Bindable
    public ResetPasswordCredentials getResetPasswordCredentials() {
        return this.resetPasswordCredentials;
    }

    public void setResetPasswordCredentials(ResetPasswordCredentials resetPasswordCredentials) {
        this.resetPasswordCredentials = resetPasswordCredentials;
        notifyPropertyChanged(BR.resetPasswordCredentials);
    }

    @Bindable
    public String getNewPassword() {
        return this.newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        notifyPropertyChanged(BR.newPassword);
    }

    @Bindable
    public String getUserNameError() {
        return this.userNameError;
    }

    public void setUserNameError(String userNameError) {
        this.userNameError = userNameError;
        notifyPropertyChanged(BR.userNameError);
    }

    @Bindable
    public String getUserName() {
        return this.userName;
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
        return this.emailError;
    }

    public void setEmailError(String emailError) {
        this.emailError = emailError;
        notifyPropertyChanged(BR.emailError);
    }

    public void onSubmitClicked() {


        LoginApi loginApi = LoginApiClient.getClient();
        if (isInputValid() && newPassword == null) {

            setResetPasswordCredentials(new ResetPasswordCredentials.EmailNamePair(getEmail(), getUserName()));
            // Attempt to post these credentials to the api
            Gson gson = new Gson();
          String body =  gson.toJson(resetPasswordCredentials);
            Call<SuccessMessagePair> resetPasswordResponseCall = loginApi.resetPassword(body);


            setProgressVisibility(View.VISIBLE);
            resetPasswordResponseCall.enqueue(new Callback<SuccessMessagePair>() {
                @Override
                public void onResponse(Call<SuccessMessagePair> call, Response<SuccessMessagePair> response) {
                    if (response.isSuccessful()) {
                        setProgressVisibility(View.GONE);
                        SuccessMessagePair successMessagePair = response.body();
                        assert successMessagePair != null;
                        if (successMessagePair.getSuccess()) {
                            // Account approved show the new password input layout
                            setNewPasswordTextInputLayoutVisibility(View.VISIBLE);
                            if (Objects.equals(successMessagePair.getMessage(), ResetPasswordCredentials.ResetPasswordDataHolder.AccountType.STUDENT.getValue())) {
                                setAccountType(ResetPasswordCredentials.ResetPasswordDataHolder.AccountType.STUDENT);
                            } else {
                                setAccountType(ResetPasswordCredentials.ResetPasswordDataHolder.AccountType.TEACHER);
                            }
                        } else {
                            setEmailError(successMessagePair.getMessage());
                        }

                    }
                }

                @Override
                public void onFailure(Call<SuccessMessagePair> call, Throwable t) {
                    setProgressVisibility(View.GONE);
                }
            });

        } else if (isInputValid() && newPassword != null && InputValidator.validatePassword(getNewPassword()) == null) {
            setProgressVisibility(View.VISIBLE);
            setResetPasswordCredentials(new ResetPasswordCredentials.ResetPasswordDataHolder(getEmail(), getNewPassword(), getAccountType()));
            // Attempt to send these credentials to the api
            Gson gson = new Gson();
            String body = gson.toJson(resetPasswordCredentials);
            Call<SuccessMessagePair> resetPasswordResponseCall = loginApi.resetPassword(body);
            resetPasswordResponseCall.enqueue(new Callback<SuccessMessagePair>() {
                @Override
                public void onResponse(Call<SuccessMessagePair> call, Response<SuccessMessagePair> response) {
                    if (response.isSuccessful()) {
                        setProgressVisibility(View.GONE);
                        // TODO () show dialog after password reset
                        SuccessMessagePair successMessagePair = response.body();

                    }
                }

                @Override
                public void onFailure(Call<SuccessMessagePair> call, Throwable t) {
                    setProgressVisibility(View.GONE);
                }
            });
        } else {
            setEmailError(InputValidator.validateEmail(this.getEmail()));
            if (newPassword != null) {
                setNewPasswordError(InputValidator.validatePassword(this.getNewPassword()));
            }
            setUserNameError(InputValidator.validateName(this.getUserName()));
        }
    }

    public boolean isInputValid() {

        return !getEmail().trim().isEmpty()
                && !getUserName().trim().isEmpty()
                && InputValidator.validateEmail(getEmail()) == null
                && InputValidator.validateName(getUserName()) == null;
    }
}
