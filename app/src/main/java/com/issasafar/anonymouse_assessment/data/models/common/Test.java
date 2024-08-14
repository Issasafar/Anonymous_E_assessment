package com.issasafar.anonymouse_assessment.data.models.common;

import java.util.ArrayList;

public abstract class Test {
    public int t_id;
    public String description;
    public ArrayList<Question> questions;
    public ArrayList<Answer> answers;

    public Test(int t_id, String description, ArrayList<Question> questions, ArrayList<Answer> answers) {
        this.t_id = t_id;
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
}
