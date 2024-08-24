package com.issasafar.anonymouse_assessment.data.models.common;

public class CourseRequest<T> {
    private CourseAction action;
    private T data;

    public CourseRequest(CourseAction action, T data) {
        this.action = action;
        this.data = data;
    }

    public enum CourseAction {
        POST_TEST,
        GET_COURSES,
        GET_QUESTIONS,
        GET_RESULTS,
        POST_RESULT,
        POST_ANSWERS,
        GET_ANSWERS,
        GET_MESSAGES,
        POST_MESSAGE
    }
}
