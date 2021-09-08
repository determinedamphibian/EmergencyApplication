package com.example.emergencyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GuidelineButtonActivity extends AppCompatActivity {
Button btn_disaster, btn_firstaid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guideline_button);

        btn_disaster = findViewById(R.id.btn_disater);
        btn_disaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuidelineButtonActivity.this, DisasterGuidelineButtonActivity.class);
                startActivity(intent);

            }
        });

        btn_firstaid = findViewById(R.id.btn_firstaid);
        btn_firstaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (GuidelineButtonActivity.this, FirstAidActivity.class);
                startActivity(intent);
            }
        });

    }
}