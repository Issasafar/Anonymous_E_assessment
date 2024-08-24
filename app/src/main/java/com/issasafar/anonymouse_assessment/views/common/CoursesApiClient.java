package com.issasafar.anonymouse_assessment.views.common;

import com.issasafar.anonymouse_assessment.views.login.LoginApiClient;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CoursesApiClient {
    private static Retrofit retrofit = null;
    public static Retrofit getClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Executor executor = Executors.newCachedThreadPool();
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://127.0.0.1")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .callbackExecutor(executor)
                    .build();
        }
        return retrofit;
    }
}
