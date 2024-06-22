package com.issasafar.anonymouse_assessment.views.login;

import android.util.Log;

import com.google.gson.Gson;
import com.issasafar.anonymouse_assessment.data.models.Result;
import com.issasafar.anonymouse_assessment.data.models.User;
import com.issasafar.anonymouse_assessment.data.models.login.LoginResponse;
import com.issasafar.anonymouse_assessment.data.models.login.SuccessMessagePair;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

/**
        * Class that handles authentication w/ login credentials and retrieves user information.
        */
public class LoginDataSource {
    private final Executor executor;


    public LoginDataSource( Executor executor) {
        this.executor = executor;

    }
    public void login(String email, String password, RepositoryCallBack<LoginResponse> repositoryCallback) {
       String jsonBody;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", "");
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("sign", "");
            jsonBody = jsonObject.toString();
    } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        executor.execute(() -> {
            try {
                Log.d("makeRequset", "making request with " + jsonBody);
                Result<LoginResponse> result = makeSynchronousLoginRequest(jsonBody);
                repositoryCallback.onComplete(result);
            } catch (Exception e) {
                Log.d("LoginDatasource::", e.getMessage() + " : " + e.getCause());
                Result<LoginResponse> errorResult = new Result.Error<>(e);
                repositoryCallback.onComplete(errorResult);

            }
        });
    }
    public void register(User user, RepositoryCallBack<LoginResponse> repositoryCallBack) {
        Gson gson = new Gson();
        String body = gson.toJson(user);
        LoginApi loginApiClient = LoginApiClient.getClient();
        Call<SuccessMessagePair> registerationCall = loginApiClient.registerNewUser(body);
        executor.execute(()->{
            registerationCall.enqueue(new Callback<SuccessMessagePair>() {
                @Override
                public void onResponse(Call<SuccessMessagePair> call, Response<SuccessMessagePair> response) {
                    if(response.isSuccessful()){
                        SuccessMessagePair successMessagePair = response.body();
                        assert successMessagePair != null;
                        if(successMessagePair.getSuccess()){
                           login(user.getEmail(), user.getPassword(), repositoryCallBack);
                        }
                    }
                }

                @Override
                public void onFailure(Call<SuccessMessagePair> call, Throwable t) {

                }
            });
        });
    }

        public void logout() {
        // TODO: revoke authentication

    }







    public Result<LoginResponse> makeSynchronousLoginRequest(String jsonBody) {
        LoginApi loginApi = LoginApiClient.getClient();
        Call<LoginResponse> call = loginApi.postData(jsonBody);

        try {
            Response<LoginResponse> response = call.execute();
            if (response.isSuccessful()) {
                LoginResponse responseBody = response.body();
                if (responseBody != null ) {
                    // Parse JSON response using Gson or JSONObject
                    Gson gson = new Gson();
//                    LoginResponse loginResponse = gson.fromJson(responseBody, LoginResponse.class);
                    // Log response for debugging
                    Log.d("Retrofit", "Login response: " + responseBody.toString());
                    // Return success result with login response
                    return new Result.Success<>(responseBody);
                } else {
                    Log.d("Retrofit", "Response body is null or empty");
                }
            } else {
                Log.d("Retrofit", "Unsuccessful response: " + response.code());
            }
        } catch (IOException e) {
            Log.d("loginDataSource", "IOException: " + e.getMessage());
            // Return error result with exception
            return new Result.Error<>(e);
        }
        // Return generic error result if response handling fails
        return new Result.Error<>(new Exception("Failed to handle response"));
    }

}
