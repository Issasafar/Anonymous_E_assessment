package com.issasafar.anonymouse_assessment.views.login;


import com.issasafar.anonymouse_assessment.data.models.login.LoginResponse;
import com.issasafar.anonymouse_assessment.data.models.login.SuccessMessagePair;

import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.POST;

public interface LoginApi {
    @POST("/Anonymous_e_assessment/register.php?XDEBUG_SESSION_START=PHPSTORM")
    Call<LoginResponse> postData(@Body String data);

    @POST("/Anonymous_e_assessment/resetPassword.php?XDEBUG_SESSION_START=PHPSTORM")
    Call<SuccessMessagePair> resetPassword(@Body String resetPasswordCredentials);

    @POST("Anonymous_e_assessment/register.php?XDEBUG_SESSION_START=PHPSTORM")
    Call<SuccessMessagePair> registerNewUser(@Body String userAccountDetails);
}
