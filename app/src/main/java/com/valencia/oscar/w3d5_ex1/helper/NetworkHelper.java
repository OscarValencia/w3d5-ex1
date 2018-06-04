package com.valencia.oscar.w3d5_ex1.helper;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

public class NetworkHelper {

    private static NetworkHelper INSTANCE;
    private OkHttpClient okHttpClient;
    private Gson gson;

    public NetworkHelper() {

    }

    public static synchronized NetworkHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NetworkHelper();
        }
        return INSTANCE;
    }

    public OkHttpClient getOkHttpClient(boolean isLoggingEnabled) {
        if (okHttpClient == null) {
            if (isLoggingEnabled) {
                okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(getLoggingInterceptor())
                        .build();
            } else {
                okHttpClient = new OkHttpClient.Builder().build();

            }

        }
        return okHttpClient;
    }

    private HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    public Request requestGenerator(String baseURL) {
        return new Request.Builder()
                .url(baseURL)
                .build();
    }

    public <T> T fromResponse(String json, Class<T> classOfT) {
        return configureGson().fromJson(json, classOfT);
    }


    public Gson configureGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;

    }
}
