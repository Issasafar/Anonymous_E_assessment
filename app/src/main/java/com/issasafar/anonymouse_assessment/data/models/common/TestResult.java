package com.issasafar.anonymouse_assessment.data.models.common;

public class TestResult {
    private int r_id;
    private int owner_id;
    private int t_id;
    private int score;

    public TestResult(int r_id, int owner_id, int t_id, int score) {
        this.r_id = r_id;
        this.owner_id = owner_id;
        this.t_id = t_id;
        this.score = score;
    }
}
