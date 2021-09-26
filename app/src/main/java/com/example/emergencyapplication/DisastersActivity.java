package com.example.emergencyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.emergencyapplication.Disasters.EarthquakeActivity;
import com.example.emergencyapplication.Disasters.FireDisasterActivity;
import com.example.emergencyapplication.Disasters.FloodActivity;
import com.example.emergencyapplication.Disasters.GoBagActivity;
import com.example.emergencyapplication.Disasters.StormActivity;
import com.example.emergencyapplication.Disasters.StormSurgeActivity;
import com.example.emergencyapplication.Disasters.TsunamiActivity;
import com.example.emergencyapplication.Disasters.VolcanicActivity;

public class DisastersActivity extends AppCompatActivity {

    ImageView bt_back;
    CardView cv_fire, cv_earthquake, cv_floods, cv_tsunami, cv_stormSurge, cv_goBag, cv_storm, cv_volcano;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster_button);

        bt_back = findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisastersActivity.this, DisasterAndEmergencyGuidelineButtonActivity.class);
                startActivity(intent);
            }
        });

        cv_fire = findViewById(R.id.cv_fire);
        cv_fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisastersActivity.this, FireDisasterActivity.class);
                startActivity(intent);

            }
        });

        cv_earthquake = findViewById(R.id.cv_earthQuake);
        cv_earthquake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisastersActivity.this, EarthquakeActivity.class);
                startActivity(intent);

            }
        });

        cv_storm = findViewById(R.id.cv_storm);
        cv_storm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisastersActivity.this, StormActivity.class);
                startActivity(intent);

            }
        });

        cv_stormSurge = findViewById(R.id.cv_stormSurge);
        cv_stormSurge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisastersActivity.this, StormSurgeActivity.class);
                startActivity(intent);
            }
        });

        cv_goBag = findViewById(R.id.cv_goBag);
        cv_goBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisastersActivity.this, GoBagActivity.class);
                startActivity(intent);
            }
        });

        cv_floods = findViewById(R.id.cv_flood);
        cv_floods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisastersActivity.this, FloodActivity.class);
                startActivity(intent);
            }
        });

        cv_volcano = findViewById(R.id.cv_volcanic);
        cv_volcano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisastersActivity.this, VolcanicActivity.class);
                startActivity(intent);
            }
        });

        cv_tsunami = findViewById(R.id.cv_tsunami);
        cv_tsunami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisastersActivity.this, TsunamiActivity.class);
                startActivity(intent);
            }
        });

    }
}