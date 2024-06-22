package com.issasafar.anonymouse_assessment.data.models;

public class Student extends User {
    private int std_id;
    public String sign;

    public void setStd_id(int std_id) {
        this.std_id = std_id;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Student(int std_id, String name, String email, String password, String sign) {
        super(name,email,password);
        this.std_id = std_id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.sign = sign;
    }

    public Student( String name, String email, String password, String sign) {
        super(name,email,password);
        this.name = name;
        this.email = email;
        this.password = password;
        this.sign = sign;
    }
    public int getStd_id() {
        return std_id;
    }


    public String getSign() {
        return sign;
    }
}
