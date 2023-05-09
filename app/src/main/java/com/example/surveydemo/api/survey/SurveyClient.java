package com.example.surveydemo.api.survey;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SurveyClient {
    private static final String BASE_URL = "https://dev.seamlesscommerce.com/services/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        // create interceptor to log request body
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
//                .addInterceptor(new Interceptor() {
//                    @NonNull
//                    @Override
//                    public Response intercept(@NonNull Chain chain) throws IOException {
//                        Log.i("EMC INTERCEPT: ", chain.request().body().toString());
//                        return chain.proceed(chain.request());
//                    }
//                })
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
