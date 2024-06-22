package com.issasafar.anonymouse_assessment.data.models;

public class Teacher extends User {
    private int t_id;

    public Teacher(String name, String email, String password) {
        super(name, email, password);
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
    }


    public Teacher(int t_id, String name, String email, String password) {
        super(name, email, password);
        this.t_id = t_id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getT_id() {
        return t_id;
    }

}
