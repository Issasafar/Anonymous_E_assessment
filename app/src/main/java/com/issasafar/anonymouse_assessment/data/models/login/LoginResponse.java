package com.issasafar.anonymouse_assessment.data.models.login;

import com.google.gson.annotations.SerializedName;

public  class LoginResponse {
    //private String authToken;

    @SerializedName("userId")
    private String userId;
    @SerializedName("userName")
    private String userName;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("sign")
    private String sign;
    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;

    public LoginResponse(String message, boolean success, String userId, String userName, String email, String password, String sign) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.sign = sign;
        this.success = success;
        this.message = message;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }


    @Override
    public String toString() {
        return "LoginResponse{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", sign='" + sign + '\'' +
                ", success=" + success +
                ", message='" + message + '\'' +
                '}';
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    // Getter methods for retrieving the values...

//    public String getAuthToken() {
//        return authToken;
//    }


}
