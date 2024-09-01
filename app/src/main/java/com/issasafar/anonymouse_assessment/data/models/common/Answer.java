package com.issasafar.anonymouse_assessment.data.models.common;

public  class Answer {
    protected int a_id;
    protected String description;
    protected int owner_id;
    protected int a_order;

    public Answer(int a_id, String description, int owner_id, int a_order) {
        this.a_id = a_id;
        this.description = description;
        this.owner_id = owner_id;
        this.a_order = a_order;
    }


    public int getA_id() {
        return a_id;
    }

    public void setA_id(int a_id) {
        this.a_id = a_id;
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

    public int getA_order() {
        return a_order;
    }

    public void setA_order(int a_order) {
        this.a_order = a_order;
    }
    public static class LongAnswerAnswer extends Answer{

        public LongAnswerAnswer(int a_id, String description, int owner_id, int a_order) {
            super(a_id, description, owner_id, a_order);
        }
    }
    public static class MultipleChoiceAnswer extends Answer{
        public MultipleChoiceAnswer(int a_id, String description, int owner_id, int a_order) {
            super(a_id, description, owner_id, a_order);
        }
    }
}

