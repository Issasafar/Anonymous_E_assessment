package com.issasafar.anonymouse_assessment.data.models.common;

public class LongAnswerQuestion  extends Question{
    private String answer;

    public LongAnswerQuestion(int q_id, int t_id, String description, int q_order, String answer) {
        super(q_id, t_id, description, q_order);
        this.answer = answer;
    }
}
