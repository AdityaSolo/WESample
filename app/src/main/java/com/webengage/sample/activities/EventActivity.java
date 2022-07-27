package com.webengage.sample.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.webengage.sample.R;
import com.webengage.sample.databinding.ActivityEventBinding;
import com.webengage.sdk.android.WebEngage;

public class EventActivity extends AppCompatActivity {
    private ActivityEventBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEventBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        sendNavigationEvent();

    }

    @Override
    protected void onResume() {
        super.onResume();
        WebEngage.get().analytics().screenNavigated("Events");
    }

    private void initView() {
        binding.EventActivityButtonNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebEngage.get().analytics().track("Triggered");
                Toast.makeText(getApplicationContext(),"Triggered New Event",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendNavigationEvent() {
        //WebEngage.get().analytics().track("Navigated");
    }
}