package com.ly.example.myapplication2.api;

import com.ly.example.myapplication2.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class ServiceGenerator {

    private static final String BASE_URL = "http://news-at.zhihu.com/api/";

    private static OkHttpClient.Builder builder = new OkHttpClient.Builder();

    private static Retrofit retrofit = new Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(setLoggingClient())
            .baseUrl(BASE_URL)
            .build();

    private static OkHttpClient setLoggingClient() {
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor
                    .Logger() {
                @Override
                public void log(String message) {
                    Timber.d("Retrofit", message);
                }
            });
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }
        return builder.build();
    }

    public static <T> T createService(Class<T> clazz) {
        return retrofit.create(clazz);
    }
}