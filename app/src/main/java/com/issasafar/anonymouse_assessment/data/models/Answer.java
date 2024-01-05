package com.issasafar.anonymouse_assessment.data.models;

public class Answer {
    public int a_id;
    public String description;

    public Answer(int a_id, String description) {
        this.a_id = a_id;
        this.description = description;
    }

    public int getA_id() {
        return a_id;
    }

    public String getDescription() {
        return description;
    }
}
