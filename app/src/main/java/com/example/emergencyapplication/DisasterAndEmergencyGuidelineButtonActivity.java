package com.example.emergencyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class DisasterAndEmergencyGuidelineButtonActivity extends AppCompatActivity {


    ImageView btn_back, btn_disaster, btn_firstAid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster_guideline_button);

        btn_back = findViewById(R.id.bt_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisasterAndEmergencyGuidelineButtonActivity.this, MainDashboardActivity.class);
                startActivity(intent);
            }
        });

        btn_disaster = findViewById(R.id.bt_disaster);
        btn_disaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisasterAndEmergencyGuidelineButtonActivity.this, DisastersActivity.class);
                startActivity(intent);

            }
        });

        btn_firstAid = findViewById(R.id.bt_first_aid_kit);
        btn_firstAid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (DisasterAndEmergencyGuidelineButtonActivity.this, FirstAidActivity.class);
                startActivity(intent);
            }
        });

    }
}