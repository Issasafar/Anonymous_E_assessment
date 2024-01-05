package com.issasafar.anonymouse_assessment.data.models.login;

public  class LoggedUser {
    String userId;
    String userName;
    String email;
    String password;
    String sign;

    public LoggedUser(String userId, String userName, String email,String password, String sign) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.sign = sign;
        this.password = password;
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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
