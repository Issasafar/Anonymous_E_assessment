package com.issasafar.anonymouse_assessment.data.models.common;

public  class Question {
    protected int q_id;
    protected int t_id;
    protected String description;
    protected int q_order;

    public Question(int q_id, int t_id, String description, int q_order) {
        this.q_id = q_id;
        this.t_id = t_id;
        this.description = description;
        this.q_order = q_order;
    }
    public Question(String description, int q_order){
        this.description = description;
        this.q_order = q_order;
    }


    public int getQ_id() {
        return q_id;
    }

    public void setQ_id(int q_id) {
        this.q_id = q_id;
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

    public int getQ_order() {
        return q_order;
    }

    public void setQ_order(int q_order) {
        this.q_order = q_order;
    }
}
