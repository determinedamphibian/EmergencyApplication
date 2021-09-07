package com.example.emergencyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EmergencyHotlineButtonActivity extends AppCompatActivity {
Button btn_medical, btn_fire, btn_police, btn_helpline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_hotline_button);

        btn_medical = findViewById(R.id.btn_medical);
        btn_fire = findViewById(R.id.btn_fire);
        btn_police = findViewById(R.id.btn_police);
        btn_helpline = findViewById(R.id.btn_helpline);

        btn_medical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmergencyHotlineButtonActivity.this, MedicalActivity.class);
                startActivity(intent);
            }
        });

        btn_fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmergencyHotlineButtonActivity.this, FireActivity.class);
                startActivity(intent);
            }
        });

        btn_police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmergencyHotlineButtonActivity.this, CrimeActivity.class);
                startActivity(intent);
            }
        });
    }
}