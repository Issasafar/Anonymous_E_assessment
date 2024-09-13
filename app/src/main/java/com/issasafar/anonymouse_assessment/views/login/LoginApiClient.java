package com.issasafar.anonymouse_assessment.views.login;

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

public class LoginApiClient {
    // List of potential base URLs
    private static final String[] BASE_URLS = {
            "http://10.0.2.2",    // First option (emulator)
            "http://192.168.1.37", // Second option (replace with your computer IP)
    };

    private static OkHttpClient client;
    private static Retrofit retrofit;

    public static LoginApi getClient() {
        Executor executor = Executors.newCachedThreadPool();

        // Create logging interceptor
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Create OkHttpClient with a custom interceptor for handling retries
        client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new FailoverInterceptor())
                .build();

        // Create the first Retrofit instance (with the first base URL)
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URLS[0])
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .callbackExecutor(executor)
                .build();

        return retrofit.create(LoginApi.class);
    }

    // Custom interceptor to handle failover logic
    private static class FailoverInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = null;
            IOException exception = null;

            // Try to connect using each URL in the list
            for (String baseUrl : BASE_URLS) {
                try {
                    // Modify the request's base URL dynamically
                    HttpUrl newUrl = HttpUrl.parse(baseUrl)
                            .newBuilder(request.url().encodedPath())
                            .build();
                    Request newRequest = request.newBuilder().url(newUrl).build();

                    // Proceed with the request
                    response = chain.proceed(newRequest);

                    // If successful, return the response
                    if (response.isSuccessful()) {
                        return response;
                    }
                } catch (IOException e) {
                    exception = e; // Store exception for later reporting if all URLs fail
                }
            }

            // If none of the URLs worked, throw the last caught exception
            throw (exception != null) ? exception : new IOException("Failed to connect to any base URL");
        }
    }
}
