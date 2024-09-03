package com.issasafar.anonymouse_assessment.data.models.common;

import com.google.gson.annotations.SerializedName;

public class UnderstandingMessage {
    private int m_id;
    private int student_id;
    private int teacher_id;
    private String student_name;
    private int seen;
    private int fetched;
    private String message;

    public UnderstandingMessage(int m_id, int student_id, int teacher_id, String student_name, int seen, int fetched, String message) {
        this.m_id = m_id;
        this.student_id = student_id;
        this.teacher_id = teacher_id;
        this.student_name = student_name;
        this.seen = seen;
        this.fetched = fetched;
        this.message = message;
    }

    public boolean isSeen() {
        return seen == 1;
    }

    public void setSeen(int seen) {
        this.seen = seen;
    }

    public int getM_id() {
        return m_id;
    }

    public void setM_id(int m_id) {
        this.m_id = m_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public boolean isFetched() {
        return fetched == 1;
    }

    public void setFetched(int fetched) {
        this.fetched = fetched;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
