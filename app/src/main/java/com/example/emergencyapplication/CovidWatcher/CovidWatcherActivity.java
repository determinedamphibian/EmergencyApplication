package com.example.emergencyapplication.CovidWatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.emergencyapplication.MainDashboardActivity;
import com.example.emergencyapplication.R;

public class CovidWatcherActivity extends AppCompatActivity {

    Button btn_login;
    ImageView btn_back, btn_phone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_watcher);

        btn_back = findViewById(R.id.bt_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CovidWatcherActivity.this, MainDashboardActivity.class);
                startActivity(intent);
            }
        });

        btn_phone = findViewById(R.id.image_phone);
        btn_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CovidWatcherActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CovidWatcherActivity.this, CovidDashboard.class);
                startActivity(intent);
            }
        });
    }
}