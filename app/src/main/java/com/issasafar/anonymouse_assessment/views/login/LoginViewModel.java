package com.issasafar.anonymouse_assessment.views.login;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ProgressBar;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.MutableLiveData;

import com.issasafar.anonymouse_assessment.data.models.login.LoggedInUser;
import com.issasafar.anonymouse_assessment.data.models.login.LoginResponse;
import com.issasafar.anonymouse_assessment.data.models.login.UserAccount;
import com.issasafar.anonymouse_assessment.data.models.Result;
import com.issasafar.anonymouse_assessment.viewmodels.InputValidator;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class LoginViewModel extends BaseObservable {
    private UserAccount mUserAccount;
    private String passwordError = null;
    private String emailError = null;
    private ProgressBar loadingProgressBar;
    private LoginRepository loginRepository;
    private Context appContext;
    public static final String SHARED_PREF_CREDENTIAL_FILE = "user_credentials";

    public MutableLiveData<LoginResult> getLoginResult() {
        return this.loginResult;
    }

    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

    @Bindable
    public ProgressBar getLoadingProgressBar() {
        return this.loadingProgressBar;
    }

    public void setLoadingProgressBar(ProgressBar progressBar) {
        this.loadingProgressBar = progressBar;
    }


    public LoginViewModel(Context appContext) {
        this.appContext = appContext;
        this.mUserAccount = new UserAccount("", "");
        Executor executor = Executors.newCachedThreadPool();
        this.loginRepository = LoginRepository.getInstance(new LoginDataSource(executor));
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
        notifyPropertyChanged(BR.email);

    }

    public void setPassword(String password) {
        this.mUserAccount.setPassword(password);
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
        } else {
            setEmailError(InputValidator.validateEmail(mUserAccount.getEmail()));
            setPasswordError(InputValidator.validatePassword(mUserAccount.getPassword()));
        }
    }


    public boolean isInputValid() {
        return !getEmail().trim().isEmpty()
                && !getPassword().trim().isEmpty()
                && InputValidator.validateEmail(mUserAccount.getEmail()) == null
                && InputValidator.validatePassword(mUserAccount.getPassword()) == null;
    }

    public void saveUserCredentials(LoginResult loginResult) {
        SharedPreferences sharedPreferences;
        sharedPreferences = appContext.getSharedPreferences(SHARED_PREF_CREDENTIAL_FILE, MODE_PRIVATE);
        LoggedInUser loggedInUser = loginResult.getSuccess();
        SharedPreferences.Editor spEditor = sharedPreferences.edit();
        spEditor.putString("userId", loggedInUser.getUserId());
        spEditor.putString("userName", loggedInUser.getUserName());
        spEditor.putString("email", loggedInUser.getEmail());
        spEditor.putString("password", loggedInUser.getPassword());
        if (loggedInUser.getSign() != null) {
            spEditor.putString("sign", loggedInUser.getSign());
        } else {
            spEditor.putString("sign", "");
        }
        spEditor.apply();
    }

    public static String getUserId(Context appContext) {
        SharedPreferences sharedPreferences = appContext.getSharedPreferences(SHARED_PREF_CREDENTIAL_FILE, MODE_PRIVATE);
        return sharedPreferences.getString("userId", "-1");
    }
    public static String getStudentSign(Context appContext) {
        SharedPreferences sharedPreferences = appContext.getSharedPreferences(SHARED_PREF_CREDENTIAL_FILE, MODE_PRIVATE);
        return sharedPreferences.getString("sign", null);
    }

    public static void logout(Context appContext) {
        SharedPreferences sharedPreferences = appContext.getSharedPreferences(SHARED_PREF_CREDENTIAL_FILE, MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

    public void login(String email, String password) {
        loginRepository.login(email, password, result -> {
            if (result instanceof Result.Success && ((Result.Success<LoginResponse>) result).getData().isSuccess()) {
                LoggedInUser loggedInUser;
                LoginResponse response = ((Result.Success<LoginResponse>) result).getData();

                loggedInUser = new LoggedInUser(response.getUserId(), response.getUserName(), response.getEmail(), response.getPassword(), response.getSign());
                loginResult.postValue(new LoginResult(loggedInUser));
            }else if(result instanceof Result.Success && !((Result.Success<LoginResponse>) result).getData().isSuccess()){
                String errorMessage = ((Result.Success<LoginResponse>)result).getData().getMessage();
                loginResult.postValue(new LoginResult(errorMessage));
            } else {
                // show an error
                loginResult.postValue(new LoginResult("Login failed (connection error)"));
            }
        });


    }
}
