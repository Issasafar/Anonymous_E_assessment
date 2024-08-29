package com.issasafar.anonymouse_assessment.data.models.common;

public class LongAnswerQuestion  extends Question{
    private String solution;

    public LongAnswerQuestion(int q_id, int t_id, String description, int q_order, String solution) {
        super(q_id, t_id, description, q_order);
        this.solution = solution;
    }
    public LongAnswerQuestion(String description, int q_order, String solution){
        super(description,q_order);
        this.solution = solution;
    }
}
