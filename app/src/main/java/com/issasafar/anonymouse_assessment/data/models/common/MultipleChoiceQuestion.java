package com.issasafar.anonymouse_assessment.data.models.common;

public class MultipleChoiceQuestion extends Question{
    private String solution;
    private String[] choices;

    public MultipleChoiceQuestion(int q_id, int t_id, String description, int q_order, String solution, String[] choices) {
        super(q_id, t_id, description, q_order);
        this.solution = solution;
        this.choices = choices;
    }
}
