package com.issasafar.anonymouse_assessment.data.models.common;

import java.util.ArrayList;

public abstract class Course {
    private int t_id;
    private int owner_id;
    private String description;
    private ArrayList<Question> questions;
    private ArrayList<Answer> answers;

    public Course(int t_id, int owner_id, String description, ArrayList<Question> questions, ArrayList<Answer> answers) {
        this.t_id = t_id;
        this.owner_id = owner_id;
        this.description = description;
        this.questions = questions;
        this.answers = answers;
    }

    public Course(int owner_id, String description, ArrayList<Question> questions, ArrayList<Answer> answers) {
        this.owner_id = owner_id;
        this.description = description;
        this.questions = questions;
        this.answers = answers;
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

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public static class TestCourse extends Course {
        public TestCourse(int t_id, int owner_id, String description, ArrayList<Question> questions, ArrayList<Answer> answers) {
            super(t_id, owner_id, description, questions, answers);
        }

        public TestCourse(int owner_id, String description, ArrayList<Question> questions, ArrayList<Answer> answers) {
            super(owner_id, description, questions, answers);
        }
    }
}
