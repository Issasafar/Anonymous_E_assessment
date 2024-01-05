package com.issasafar.anonymouse_assessment.views.login;

public  class LoginResponse {
    //private String authToken;
    private String userId;
    private String userName;
    private String email;
    private String password;
    private String sign;

    public LoginResponse(String userId, String userName, String email, String password, String sign) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.sign = sign;
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


    // Getter methods for retrieving the values...

//    public String getAuthToken() {
//        return authToken;
//    }


}
