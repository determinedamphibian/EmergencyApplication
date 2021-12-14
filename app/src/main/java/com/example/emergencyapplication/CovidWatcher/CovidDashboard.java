package com.example.emergencyapplication.CovidWatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emergencyapplication.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CovidDashboard extends AppCompatActivity {

    CovidAdapter covidAdapter;
    ImageView bt_menu;
    DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    Button btn_logout, btn_view;
    private DatabaseReference reference;
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

        btn_view = findViewById(R.id.btn_view);

        Log.d("UID", FirebaseAuth.getInstance().getUid());
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals("9UPor0R3Enfh413X7dmew0d8tFB3")){
            btn_view.setVisibility(View.VISIBLE);
            btn_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CovidDashboard.this, ViewUserDataActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
        else{
            btn_view.setVisibility(View.GONE);
        }

        //getting the value of user status from firebase
        List<String> userStatusList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference().child("UserStatus");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String userStatus = dataSnapshot.child("status").getValue().toString();

                    userStatusList.add(userStatus);
                    Log.d("USER_STATUS", userStatus);
                }

                Log.d("USER_STATUS_SIZE", String.valueOf(userStatusList.size()));

                int activeCounter = 0;
                int clearCounter = 0;
                int deathCounter = 0;
                for(int i = 0; i < userStatusList.size(); i++){

                    Log.d("TEST", String.valueOf(userStatusList.get(i).contains("active case")));
                  if(userStatusList.get(i).contains("active case")){
                      activeCounter++;
                  }
                  else if(userStatusList.get(i).contains("clear case")){
                      clearCounter++;
                  }
                  else{
                      deathCounter++;
                  }
                }

                getTotalCase(activeCounter, clearCounter, deathCounter);
                displayPie(activeCounter, clearCounter, deathCounter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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

    private void getTotalCase(int activeCounter, int clearCounter, int deathCounter) {

        String totalCase = String.valueOf(activeCounter+clearCounter+deathCounter);
        TextView tv_totalCase = findViewById(R.id.tv_totalCase);
        tv_totalCase.setText(totalCase);
    }

    private void displayPie(int activeCounter, int clearCounter, int deathCounter) {

        PieChart pieChart = findViewById(R.id.pieChart);
        ArrayList <PieEntry> cases = new ArrayList<>();

        if(activeCounter > 0){
            Log.d("ACTIVE_COUNTER", String.valueOf(activeCounter));
            cases.add(new PieEntry(activeCounter, "Active Case"));
        }
        else{

        }

        if(clearCounter > 0){
            Log.d("CLEAR_COUNTER", String.valueOf(clearCounter));
            cases.add(new PieEntry(clearCounter, "Clear Case"));
        }
        else{

        }

        if(deathCounter > 0){
            Log.d("DEATH_COUNTER", String.valueOf(deathCounter));
            cases.add(new PieEntry(deathCounter, "Death Case"));
        }
        else{

        }

        PieDataSet pieDataSet = new PieDataSet(cases, "Covid-19 Status Cases");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//        pieDataSet.setValueTextColor(Color.BLACK);
//        pieDataSet.setValueTextSize(16f);

        PieData data =  new PieData(pieDataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(16f);

        pieChart.setData(data);
        pieChart.invalidate();
        pieChart.setCenterText("Green Archers Compound");
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(14);
        pieChart.setCenterTextSize(18);
        pieChart.getDescription().setEnabled(false);

        Legend legend = pieChart.getLegend();
        legend.setTextSize(14f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setEnabled(true);
        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }

}