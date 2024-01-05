package com.issasafar.anonymouse_assessment.data.models;

public class Teacher {
    private int t_id;
    private String name;
    private String email;
    private String password;

    public Teacher(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
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

    public Teacher(int t_id, String name, String email, String password) {
        this.t_id = t_id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getT_id() {
        return t_id;
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
}
