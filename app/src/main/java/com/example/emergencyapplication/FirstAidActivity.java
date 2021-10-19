package com.example.emergencyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.emergencyapplication.FirstAids.AnimalBitesActivity;
import com.example.emergencyapplication.FirstAids.BleedingActivity;
import com.example.emergencyapplication.FirstAids.BurnsActivity;
import com.example.emergencyapplication.FirstAids.CPRActivity;
import com.example.emergencyapplication.FirstAids.ChokingActivity;
import com.example.emergencyapplication.FirstAids.FractureActivity;
import com.example.emergencyapplication.FirstAids.SprainsActivity;

public class FirstAidActivity extends AppCompatActivity {

    ImageView btn_back;
    CardView  cv_animal_bites, cv_burns, cv_fractures, cv_sprains, cv_choking, cv_cpr, cv_bleeding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_aid);

        btn_back = findViewById(R.id.bt_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstAidActivity.this, DisasterAndEmergencyGuidelineButtonActivity.class);
                startActivity(intent);
            }
        });

        cv_animal_bites = findViewById(R.id.cv_animalBites);
        cv_animal_bites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstAidActivity.this, AnimalBitesActivity.class);
                startActivity(intent);
            }
        });

        cv_burns = findViewById(R.id.cv_burns);
        cv_burns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(FirstAidActivity.this, BurnsActivity.class);
                startActivity(intent);
            }
        });

        cv_fractures = findViewById(R.id.cv_fractures);
        cv_fractures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(FirstAidActivity.this, FractureActivity.class);
                startActivity(intent);
            }
        });

        cv_cpr = findViewById(R.id.cv_cpr);
        cv_cpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(FirstAidActivity.this, CPRActivity.class);
                startActivity(intent);
            }
        });

        cv_sprains = findViewById(R.id.cv_sprains);
        cv_sprains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(FirstAidActivity.this, SprainsActivity.class);
                startActivity(intent);
            }
        });

        cv_choking = findViewById(R.id.cv_choking);
        cv_choking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(FirstAidActivity.this, ChokingActivity.class);
                startActivity(intent);
            }
        });

        cv_bleeding = findViewById(R.id.cv_bleeding);
        cv_bleeding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(FirstAidActivity.this, BleedingActivity.class);
                startActivity(intent);
            }
        });

    }
}