package com.valencia.oscar.w3d5_ex1.util;

import com.valencia.oscar.w3d5_ex1.BuildConfig;
import com.valencia.oscar.w3d5_ex1.RandomUserManager;
import com.valencia.oscar.w3d5_ex1.helper.NetworkHelper;

import okhttp3.OkHttpClient;

public class Injector {
    public Injector() {
    }
    private static NetworkHelper networkHelper;

    public static NetworkHelper provideNetworkHelper() {
        return NetworkHelper.getInstance();
    }

    public static OkHttpClient provideOkHttpClient() {
        if(BuildConfig.DEBUG) {
            return provideNetworkHelper()
                    .getOkHttpClient(true);
        } else {
            return provideNetworkHelper()
                    .getOkHttpClient(false);
        }
    }

    public static RandomUserManager provideRandomUserManager() {
        return new RandomUserManager();
    }
}
