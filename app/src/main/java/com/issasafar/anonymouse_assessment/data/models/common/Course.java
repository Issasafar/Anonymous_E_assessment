package com.issasafar.anonymouse_assessment.data.models.common;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Course {
    @SerializedName("t_id")
    private int t_id;
    @SerializedName("owner_id")
    private int owner_id;
    @SerializedName("description")
    private String description;

    public Course(int t_id, int owner_id, String description) {
        this.t_id = t_id;
        this.owner_id = owner_id;
        this.description = description;
    }

    public Course(int owner_id, String description) {
        this.owner_id = owner_id;
        this.description = description;
    }

    public int getT_id() {
        return t_id;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    // The TestCourse subclass now contains the questions and answers
    public static class TestCourse extends Course {
        private ArrayList<Question> questions;

        public TestCourse(int t_id, int owner_id, String description, ArrayList<Question> questions) {
            super(t_id, owner_id, description);
            this.questions = questions;
        }

        public TestCourse(int owner_id, String description, ArrayList<Question> questions) {
            super(owner_id, description);
            this.questions = questions;
        }

        public ArrayList<Question> getQuestions() {
            return questions;
        }

        public void setQuestions(ArrayList<Question> questions) {
            this.questions = questions;
        }

    }
}
