package com.example.emergencyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class FirstAidActivity extends AppCompatActivity {
    Button btn_cpr, btn_wounds, btn_fractures, btn_sprains, btn_burns, btn_choking, btn_dogbites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_aid);



    }
}