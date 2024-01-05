package com.issasafar.anonymouse_assessment.data.models;

public class Question {
    public int q_id;
    public String description;

    public Question(int q_id, String description) {
        this.q_id = q_id;
        this.description = description;
    }

    public int getQ_id() {
        return q_id;
    }

    public String getDescription() {
        return description;
    }
}
