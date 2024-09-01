package com.issasafar.anonymouse_assessment.data.models.common;

public class TestResult {
    private int r_id;
    private int owner_id;
    private String owner_name;
    private int t_id;
    private int score;

    public int getT_id() {
        return t_id;
    }

    public int getR_id() {
        return r_id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public int getScore() {
        return score;
    }

    public TestResult(int r_id, int owner_id, String owner_name, int t_id, int score) {
        this.r_id = r_id;
        this.owner_id = owner_id;
        this.t_id = t_id;
        this.score = score;
        this.owner_name = owner_name;
    }
}
