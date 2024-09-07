package com.issasafar.anonymouse_assessment.data.models.common;

public class MultipleChoiceQuestion extends Question{
    private String solution;

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public String[] getChoices() {
        return choices;
    }

    public String getSolution() {
        return solution;
    }

    private String[] choices;

    public MultipleChoiceQuestion(int q_id, int t_id, String description, int q_order, String solution, String[] choices) {
        super(q_id, t_id, description, q_order);
        this.solution = solution;
        this.choices = choices;
        this.type = QuestionType.MULTI;
    }

    public MultipleChoiceQuestion(String description, int q_order, String solution, String[] choices) {
        super(description,q_order);
        this.choices = choices;
        this.solution = solution;
        this.type = QuestionType.MULTI;
    }
}
