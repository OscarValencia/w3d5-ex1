package com.valencia.oscar.w3d5_ex1;

import android.util.Log;

import com.valencia.oscar.w3d5_ex1.helper.NetworkHelper;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.valencia.oscar.w3d5_ex1.util.Injector;
import com.valencia.oscar.w3d5_ex1.entities.UserResponse;

import java.io.IOException;

public class RandomUserManager {
    private static final String BASE_URL = "https://randomuser.me/api";
    private static final String TAG = RandomUserManager.class.getSimpleName() + "_TAG";

    private final OkHttpClient client;
    private final NetworkHelper networkHelper;

    public RandomUserManager() {
        client = Injector.provideOkHttpClient();
        networkHelper = Injector.provideNetworkHelper();
    }

    public void getRandomUser(final RandomUserCallback callback) {
        Request randomRequest = networkHelper
                .requestGenerator(BASE_URL);
        client.newCall(randomRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onRandomUserError();
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                    UserResponse userResponse = networkHelper
                            .fromResponse(response.body().string(),
                                    UserResponse.class);
                    callback.onRandomUserReady(userResponse);
                    Log.d(TAG, "onResponse: " + userResponse.getInfo().getSeed());
                } else {
                    callback.onRandomUserError();
                    Log.e(TAG, "onResponse: Unexpected code " + response);
                }
            }
        });
    }

    public interface RandomUserCallback {
        void onRandomUserReady(UserResponse userResponse);
        void onRandomUserError();
    }
}
