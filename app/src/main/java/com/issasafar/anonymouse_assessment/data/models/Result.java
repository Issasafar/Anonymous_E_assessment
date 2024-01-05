package com.issasafar.anonymouse_assessment.data.models;

public abstract class Result<T> {
    private Result() {

    }

    public static final class Success<T> extends Result<T> {
        public T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return this.data;
        }

    }

    public static final class Error<T> extends Result<T> {
        public Exception mException;

        public Error(Exception exception) {
            this.mException = exception;
        }

        public Exception getException() {
            return this.mException;
        }
    }
}
