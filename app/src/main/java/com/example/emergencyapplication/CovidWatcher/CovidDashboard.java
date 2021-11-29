package com.example.emergencyapplication.CovidWatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.emergencyapplication.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class CovidDashboard extends AppCompatActivity {

    CovidAdapter covidAdapter;
    ImageView bt_menu;
    DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    Button btn_logout;

    public static ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_dashboard);

        drawerLayout = findViewById(R.id.drawer_covid_layout);
        recyclerView = findViewById(R.id.covid_recycler_view);
        arrayList.clear();

        //Add menu item to arrayList
        arrayList.add("Home");
        arrayList.add("Profile");
        arrayList.add("COVID-19 Guidelines");

        //setting arrayList in an adapter
        covidAdapter = new CovidAdapter(this, arrayList);

        //using the adapter to be shown in recyclerview
        recyclerView.setLayoutManager(new LinearLayoutManager(CovidDashboard.this));
        recyclerView.setAdapter(covidAdapter);
        
        bt_menu = findViewById(R.id.bt_menu);
        bt_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(CovidDashboard.this, CovidWatcherActivity.class);
                startActivity(intent);
                Toast.makeText(CovidDashboard.this, "Logout successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}