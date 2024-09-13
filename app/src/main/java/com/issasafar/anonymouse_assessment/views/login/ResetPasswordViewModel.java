package com.issasafar.anonymouse_assessment.views.login;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.issasafar.anonymouse_assessment.BR;
import com.issasafar.anonymouse_assessment.data.models.login.ResetPasswordCredentials;
import com.issasafar.anonymouse_assessment.data.models.login.SuccessMessagePair;
import com.issasafar.anonymouse_assessment.databinding.ActivityForgotPasswordBinding;
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
    private Context context;
    private ActivityForgotPasswordBinding activityForgotPasswordBinding;
    private MutableLiveData<Integer> progressVisibility = new MutableLiveData<>(View.GONE);
    private int newPasswordTextInputLayoutVisibility = View.GONE;
    private ResetPasswordCredentials.ResetPasswordDataHolder.AccountType accountType;
    private String newPasswordError = null;
    private String userNameError = null;
    private String emailError = null;
    private Window window;


    public ResetPasswordViewModel(Context context, ActivityForgotPasswordBinding activityForgotPasswordBinding, Window window) {
        this.email = "";
        this.userName = "";
        this.context = context;
        this.activityForgotPasswordBinding = activityForgotPasswordBinding;
        this.window = window;
   }

    @Bindable
    public int getProgressVisibility() {
        return progressVisibility.getValue();
    }
    public MutableLiveData<Integer> getProgressVisibilityLiveData() {
        return progressVisibility;
    }

    public void setProgressVisibility(int progressVisibility) {
        this.progressVisibility.postValue(progressVisibility);
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
                    Snackbar.make(activityForgotPasswordBinding.getRoot(),"Connection error",Snackbar.LENGTH_SHORT).show();
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
                        SuccessMessagePair successMessagePair = response.body();
                        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(context);
                        alertDialogBuilder.setTitle("Success");
                        alertDialogBuilder.setMessage(successMessagePair.getMessage());
                        AlertDialog dialog = alertDialogBuilder.create();
                        alertDialogBuilder.setCancelable(false);
                        alertDialogBuilder.setPositiveButton("Ok", (dialogInterface, i) -> {
                           dialog.cancel();
                        });

                    }
                }

                @Override
                public void onFailure(Call<SuccessMessagePair> call, Throwable t) {
                    setProgressVisibility(View.GONE);
                    Snackbar.make(activityForgotPasswordBinding.getRoot(),"Connection error",Snackbar.LENGTH_SHORT).show();
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
