package com.issasafar.anonymouse_assessment.data;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Myapplication extends Application {
    ExecutorService mExecutorService = Executors.newFixedThreadPool(4);
}
