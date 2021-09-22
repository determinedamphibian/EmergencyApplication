package com.example.emergencyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class EmergencyHotlineButtonActivity extends AppCompatActivity {

    ImageButton imgBtn_policeStations, imgBtn_hospitals, imgBtn_fireStations, imgBtn_emotionSupp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_hotline_button);

        imgBtn_hospitals = findViewById(R.id.imgBtn_hospitals);
        imgBtn_hospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmergencyHotlineButtonActivity.this, MedicalActivity.class);
                startActivity(intent);
            }
        });
        imgBtn_fireStations = findViewById(R.id.imgBtn_fireStations);
        imgBtn_fireStations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmergencyHotlineButtonActivity.this, FireActivity.class);
                startActivity(intent);
            }
        });

        imgBtn_policeStations = findViewById(R.id.imgBtn_policeStations);
        imgBtn_policeStations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmergencyHotlineButtonActivity.this, CrimeActivity.class);
                startActivity(intent);
            }

        });

        imgBtn_emotionSupp = findViewById(R.id.imgBtn_emotionalSup);
        imgBtn_emotionSupp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmergencyHotlineButtonActivity.this, HelplineActivity.class);
                startActivity(intent);
            }
        });
    }
}