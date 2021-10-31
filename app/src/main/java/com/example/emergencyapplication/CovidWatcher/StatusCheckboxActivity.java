package com.example.emergencyapplication.CovidWatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.emergencyapplication.R;

public class StatusCheckboxActivity extends AppCompatActivity {

    private CheckBox  cb_fever, cb_dryCough, cb_fatigue, cb_aches, cb_runnyNose, cb_soreThroat, cb_shortnessBreath, cb_diarrhea, cb_headache, cb_smellAndTaste;
    private Button btn_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_checkbox);

        cb_fever = findViewById(R.id.checkbox_fever);
        cb_fever.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    Toast.makeText(StatusCheckboxActivity.this, "Hello World!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cb_dryCough = findViewById(R.id.checkbox_dry_cough);
        cb_dryCough.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        cb_fatigue = findViewById(R.id.checkbox_fatigue);
        cb_fatigue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        cb_aches = findViewById(R.id.checkbox_aches);
        cb_aches.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        cb_runnyNose = findViewById(R.id.checkbox_runny_nose);
        cb_runnyNose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        cb_soreThroat = findViewById(R.id.checkbox_sore_throat);
        cb_soreThroat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        cb_shortnessBreath = findViewById(R.id.checkbox_shortness);
        cb_shortnessBreath.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        cb_diarrhea = findViewById(R.id.checkbox_diarrhea);
        cb_diarrhea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        cb_headache = findViewById(R.id.checkbox_headache);
        cb_headache.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        cb_smellAndTaste = findViewById(R.id.checkbox_smellAndTaste);
        cb_smellAndTaste.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        btn_confirm = findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatusCheckboxActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

    }
}