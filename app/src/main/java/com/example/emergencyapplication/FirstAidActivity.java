package com.example.emergencyapplication;

import androidx.appcompat.app.AppCompatActivity;

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

    ImageView btn_back, btn_animal_bites, btn_burns, btn_fractures, btn_sprains, btn_choking, btn_cpr, btn_bleeding;

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

        btn_animal_bites = findViewById(R.id.bt_animal_bites);
        btn_animal_bites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstAidActivity.this, AnimalBitesActivity.class);
                startActivity(intent);
            }
        });

        btn_burns = findViewById(R.id.bt_burns);
        btn_burns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(FirstAidActivity.this, BurnsActivity.class);
                startActivity(intent);
            }
        });

        btn_fractures = findViewById(R.id.bt_fractures);
        btn_fractures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(FirstAidActivity.this, FractureActivity.class);
                startActivity(intent);
            }
        });

        btn_cpr = findViewById(R.id.bt_cpr);
        btn_cpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(FirstAidActivity.this, CPRActivity.class);
                startActivity(intent);
            }
        });

        btn_sprains = findViewById(R.id.bt_sprains);
        btn_sprains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(FirstAidActivity.this, SprainsActivity.class);
                startActivity(intent);
            }
        });

        btn_choking = findViewById(R.id.bt_choking);
        btn_choking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(FirstAidActivity.this, ChokingActivity.class);
                startActivity(intent);
            }
        });

        btn_bleeding = findViewById(R.id.bt_bleeding);
        btn_bleeding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(FirstAidActivity.this, BleedingActivity.class);
                startActivity(intent);
            }
        });

    }
}