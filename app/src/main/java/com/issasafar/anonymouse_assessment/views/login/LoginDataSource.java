package com.issasafar.anonymouse_assessment.data;

import android.util.Log;

import com.issasafar.anonymouse_assessment.data.models.LoggedUser;
import com.issasafar.anonymouse_assessment.data.models.Result;
import com.issasafar.anonymouse_assessment.views.login.LoginResponse;
import com.issasafar.anonymouse_assessment.views.login.LoginResponseParser;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.Executor;

/**
        * Class that handles authentication w/ login credentials and retrieves user information.
        */
public class LoginDataSource {
    private final Executor executor;
    private final String loginUrl = "http://192.168.43.151/Anonymous_e_assessment/register.php/";
    private final LoginResponseParser loginResponseParser;

    public LoginDataSource(LoginResponseParser responseParser, Executor executor) {
        this.executor = executor;
        this.loginResponseParser = responseParser;
    }
    public void login(String email, String password, RepositoryCallBack<LoginResponse> repositoryCallback) {
        Log.d("logindatasourse", "i am called with " + email);
        try {

            // TODO: handle loggedInUser authentication
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", "");
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("sign", "");

            Log.d("logindatasourse", "nothing was thrown");
            makeLoginRequest(loginUrl, "POST", jsonObject.toString(),repositoryCallback);

        } catch (Exception e) {
            Log.d("logindatasourse", "Exception was thrown");
            return;
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }


    public void makeLoginRequest(
            final String loginUrl,
            String method,
             final String jsonBody,
            final RepositoryCallBack<LoginResponse> callback
    ) {
        Log.d("makeLoginRequest", "called " + jsonBody);
        executor.execute(() -> {
            try {
                Log.d("makeRequset", "making request with " +jsonBody);
                Result<LoginResponse> result = makeSynchronousLoginRequest(loginUrl, method, jsonBody);
                callback.onComplete(result);
            } catch (Exception e) {
                Log.d("RequsetError", e.getMessage()+" : "+e.getCause());
                Result<LoginResponse> errorResult = new Result.Error<>(e);
                callback.onComplete(errorResult);
            }
        });

    }


    public Result<LoginResponse> makeSynchronousLoginRequest(String loginUrl, String method, String jsonBody) throws Exception {
        Log.d("POSTTING", jsonBody);
        // HttpURLConnection logic
        if (method.equals("POST")) {
            Log.d("POSTTING", "inside Post");
            URL url = new URL(loginUrl);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod(method);
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpConnection.setRequestProperty("Accept", "application/json");
            httpConnection.setDoOutput(true);
            httpConnection.getOutputStream().write(jsonBody.getBytes(StandardCharsets.UTF_8));
            httpConnection.getOutputStream().flush();

//            int responseCode = httpConnection.getResponseCode();
//            Log.d("LoginDataSource", "response code: " + responseCode);
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                BufferedReader in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
//                String inputLine;
//                StringBuffer reponseStringBuffer = new StringBuffer();
//                while ((inputLine = in.readLine()) != null) {
//                    reponseStringBuffer.append(inputLine);
//                }
//                in.close();
//                Log.d("LoginDataSource", "response was: " + reponseStringBuffer.toString());
//            }
          //  Log.d("InputStream", "the inputStream is: "+httpConnection.getInputStream().read());

            LoginResponse loginResponse = loginResponseParser.parse(httpConnection.getInputStream());

            return new Result.Success<LoginResponse>(loginResponse);
        } else {
            //TODO manage other cases later
            return null;
        }
    }

}
