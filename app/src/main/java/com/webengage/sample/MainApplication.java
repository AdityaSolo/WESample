package com.webengage.sample;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.webengage.sdk.android.WebEngage;
import com.webengage.sdk.android.WebEngageActivityLifeCycleCallbacks;
import com.webengage.sdk.android.WebEngageConfig;

public class MainApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Constants.DEBUG_TAG, "onCreate : ");

        initContext();

        //initialize the WEBENGAGE SDK here
        initWebEngage();
    }

    private void initContext() {
        mContext = this.getApplicationContext();
    }

    private void initWebEngage() {
        Log.d(Constants.DEBUG_TAG, "Initializing WebEngage SDK");
        WebEngageConfig webEngageConfig = new WebEngageConfig.Builder()
                .setWebEngageKey(Constants.LICENSE_CODE)
                .setDebugMode(true)
                .build();
        registerActivityLifecycleCallbacks(new WebEngageActivityLifeCycleCallbacks(this, webEngageConfig));

        registerFCM();

        /**
         * Register Custom Push Renderer if necessary
         * Refer https://docs.webengage.com/docs/android-customizing-push-notifications
         **/

    }

    private void registerFCM() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                try {
                    String token = task.getResult();
                    WebEngage.get().setRegistrationID(token);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static Context getContext() {
        return mContext;
    }



}
