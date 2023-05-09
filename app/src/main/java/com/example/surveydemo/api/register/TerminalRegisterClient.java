package com.example.surveydemo.api.register;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TerminalRegisterClient {
    private static final String BASE_URL= "http://ec2-52-22-60-22.compute-1.amazonaws.com:5000/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        // create interceptor to log request body
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        Interceptor headerInterceptor = new Interceptor() {
//            @NonNull
//            @Override
//            public Response intercept(@NonNull Chain chain) throws IOException {
//                Request request = chain.request().newBuilder()
//                        .addHeader("TimeStamp", "value")
//                        .addHeader("SignatureData", "value")
//                        .build();
//                return chain.proceed(request);
//            }
//        };
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
//                .addInterceptor(headerInterceptor)
                .build();

        // create retrofit client
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


        return retrofit;
    }


}
