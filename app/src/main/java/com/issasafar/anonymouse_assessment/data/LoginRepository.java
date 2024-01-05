package com.issasafar.anonymouse_assessment.data;

import com.issasafar.anonymouse_assessment.data.models.Result;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class LoginRepository {
    private final String loginUrl = "http://192.168.1.70/Anonymous_e_assessment/register.php";
    private final LoginResponseParser mLoginResponseParser;

    public LoginRepository(LoginResponseParser loginResponseParser) {
        this.mLoginResponseParser = loginResponseParser;
    }

    public Result<LoginResponse> makeLoginRequest(String jsonBody) {
        try {
            URL url = new URL(loginUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.getOutputStream().write(jsonBody.getBytes(StandardCharsets.UTF_8));
            LoginResponse loginResponse = mLoginResponseParser.parse(httpURLConnection.getInputStream());
            return new Result.Success<LoginResponse>(loginResponse);
        } catch (Exception e) {
            return new Result.Error<LoginResponse>(e);
        }
    }
}
