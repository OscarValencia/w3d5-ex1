package com.valencia.oscar.w3d5_ex1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.valencia.oscar.w3d5_ex1.entities.UserResponse;
import com.valencia.oscar.w3d5_ex1.util.Injector;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String BASE_URL = "https://randomuser.me/api";
    private static final String TAG = MainActivity.class.getSimpleName()+"_TAG";
    private Button callBtn;
    private TextView responseTV;
    private RandomUserManager randomUserManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        randomUserManager = Injector.provideRandomUserManager();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initViews(){
        callBtn = findViewById(R.id.doNetworkMagic);
        callBtn.setOnClickListener(this);
        responseTV = findViewById(R.id.tvResponse);
    }

    private void doNetworkCall() {
        randomUserManager.getRandomUser(new RandomUserManager.RandomUserCallback() {
            @Override
            public void onRandomUserReady(UserResponse userResponse) {
                responseTV.setText(userResponse.getResults().get(0).getName().getFirst());
            }

            @Override
            public void onRandomUserError() {
                Toast.makeText(MainActivity.this, "The world is over, run!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.doNetworkMagic:
                doNetworkCall();
                break;
        }
    }
}
