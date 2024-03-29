package com.example.bettersurveylib.api.register;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TerminalRegisterClient {
    private static final String BASE_URL= "http://ec2-54-242-58-225.compute-1.amazonaws.com:5000/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        // create interceptor to log request body
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        // create retrofit client
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


        return retrofit;
    }


}
