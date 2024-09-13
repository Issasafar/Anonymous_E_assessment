package com.issasafar.anonymouse_assessment.views.common;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CoursesApiClient {
    private static final String[] BASE_URLS = {
            "http://10.0.2.2",
            "http://192.168.1.37",
            "http://10.96.0.27"
    };

    private static Retrofit retrofit = null;
    private static OkHttpClient client;

    public static Retrofit getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new FailoverInterceptor())
                .build();

        Executor executor = Executors.newCachedThreadPool();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URLS[0])
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .callbackExecutor(executor)
                    .build();
        }

        return retrofit;
    }

    private static class FailoverInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = null;
            IOException exception = null;

            for (String baseUrl : BASE_URLS) {
                try {
                    HttpUrl newUrl = HttpUrl.parse(baseUrl)
                            .newBuilder(request.url().encodedPath())
                            .build();
                    Request newRequest = request.newBuilder().url(newUrl).build();
                    response = chain.proceed(newRequest);

                    if (response.isSuccessful()) {
                        return response;
                    }
                } catch (IOException e) {
                    exception = e;
                }
            }

            throw (exception != null) ? exception : new IOException("Failed to connect to any base URL");
        }
    }
}
