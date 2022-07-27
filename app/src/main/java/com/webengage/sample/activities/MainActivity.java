package com.webengage.sample.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import com.webengage.sample.Constants;
import com.webengage.sample.databinding.ActivityMainBinding;
import com.webengage.sample.manager.SharedPrefsManager;
import com.webengage.sdk.android.WebEngage;

public class MainActivity extends AppCompatActivity {

    private String USER_LOGIN_CHECK_KEY = "user_logged_in";
    private String LOGGED_USER = "logged_in_user";
    private ActivityMainBinding binding;
    private String cuid = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initActivity();
        initListeners();
    }


    @Override
    protected void onResume() {
        super.onResume();
        WebEngage.get().analytics().screenNavigated("Home");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void initListeners() {
        binding.mainActivityButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cuid = binding.mainActivityEditTextCuid.getText().toString();
                if(!(cuid.trim().equalsIgnoreCase(""))){
                    loginUser(cuid);
                }
            }
        });
    }

    private void loginUser(String cuid) {
        Log.d(Constants.DEBUG_TAG, "Logging in as "+cuid);

        SharedPrefsManager.get().put(USER_LOGIN_CHECK_KEY,true);
       SharedPrefsManager.get().put(LOGGED_USER,cuid);
        WebEngage.get().user().login(cuid);
        navigateToEventsActivity(cuid);
    }

    private void initActivity() {
        boolean userLoggedIn = SharedPrefsManager.get().getBoolean(USER_LOGIN_CHECK_KEY, false);
        if (userLoggedIn) {
            String loggedInUser = SharedPrefsManager.get().getString(LOGGED_USER, "");
            if (!loggedInUser.trim().equals("")) {
                Log.d(Constants.DEBUG_TAG, "User Already Logged In" + loggedInUser);
                navigateToEventsActivity(loggedInUser);
            }
        }
    }

    private void navigateToEventsActivity(String cuid) {
        Log.d(Constants.DEBUG_TAG,"navigating to Events Activity");
        Intent mIntent = new Intent(this, EventActivity.class);
        mIntent.putExtra("user",cuid);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_SINGLE_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mIntent);
    }
}