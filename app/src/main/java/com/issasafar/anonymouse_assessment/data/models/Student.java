package com.issasafar.anonymouse_assessment.data.models;

public class Student {
    private int std_id;
    private String  name;
    private String email;
    private String password;
    public String sign;

    public void setStd_id(int std_id) {
        this.std_id = std_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Student(int std_id, String name, String email, String password, String sign) {
        this.std_id = std_id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.sign = sign;
    }

    public Student( String name, String email, String password, String sign) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.sign = sign;
    }
    public int getStd_id() {
        return std_id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSign() {
        return sign;
    }
}
