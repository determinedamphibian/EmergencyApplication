package com.example.emergencyapplication.SideDockContents;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.emergencyapplication.FireActivity;
import com.example.emergencyapplication.MainAdapter;
import com.example.emergencyapplication.MainDashboardActivity;
import com.example.emergencyapplication.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GovernmentAgenciesSideDock extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView btMenu, bt_deped, bt_pagasa, bt_doh, bt_dfa, bt_doj, bt_dswd, bt_ched, bt_nbi, bt_bfp;
    TextView tv_link_deped, tv_link_pagasa, tv_link_doh, tv_link_dfa, tv_link_doj, tv_link_dswd, tv_link_ched, tv_link_nbi, tv_link_bfp,
            tv_deped, tv_pagasa, tv_doh, tv_dfa, tv_doj, tv_dswd, tv_ched, tv_nbi, tv_bfp;
    private static final int REQUEST_CALL = 1;
    RecyclerView recyclerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_government_agencies_side_dock);

        //Assign values to initialized variables

        drawerLayout = findViewById(R.id.drawer_layout);
        btMenu = findViewById(R.id.bt_back);
        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new MainAdapter( this, MainDashboardActivity.arrayList));
        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        tv_link_deped = findViewById(R.id.tv_link_deped);
        tv_link_deped.setMovementMethod(LinkMovementMethod.getInstance());
        bt_deped = findViewById(R.id.btn_call_deped);
        bt_deped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_deped = findViewById(R.id.tv_number_deped);
                String number = tv_deped.getText().toString();
                makePhoneCall(number);
            }
        });

        bt_pagasa = findViewById(R.id.btn_call_pagasa);
        bt_pagasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_pagasa = findViewById(R.id.tv_number_pagasa);
                String number = tv_pagasa.getText().toString();
                makePhoneCall(number);
            }
        });

        bt_doh = findViewById(R.id.btn_call_doh);
        bt_doh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_doh = findViewById(R.id.tv_number_doh);
                String number = tv_doh.getText().toString();
                makePhoneCall(number);
            }
        });

        bt_dfa = findViewById(R.id.btn_call_dfa);
        bt_dfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_dfa = findViewById(R.id.tv_number_dfa);
                String number = tv_dfa.getText().toString();
                makePhoneCall(number);
            }
        });

        bt_doj = findViewById(R.id.btn_call_doj);
        bt_doj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_doj = findViewById(R.id.tv_number_doj);
                String number = tv_doj.getText().toString();
                makePhoneCall(number);
            }
        });

        bt_dswd = findViewById(R.id.btn_call_dswd);
        bt_dswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_dswd = findViewById(R.id.tv_number_dswd);
                String number = tv_dswd.getText().toString();
                makePhoneCall(number);
            }
        });

        bt_ched = findViewById(R.id.btn_call_ched);
        bt_ched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_ched = findViewById(R.id.tv_number_ched);
                String number = tv_ched.getText().toString();
                makePhoneCall(number);
            }
        });

        bt_nbi = findViewById(R.id.btn_call_nbi);
        bt_nbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_nbi = findViewById(R.id.tv_number_nbi);
                String number = tv_nbi.getText().toString();
                makePhoneCall(number);
            }
        });

        bt_bfp = findViewById(R.id.btn_call_bfp);
        bt_bfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_bfp = findViewById(R.id.tv_number_bfp);
                String number = tv_bfp.getText().toString();
                makePhoneCall(number);
            }
        });



    }

    private void makePhoneCall(String number) {
        if(ActivityCompat.checkSelfPermission(GovernmentAgenciesSideDock.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(GovernmentAgenciesSideDock.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }
        else{
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+number));
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainDashboardActivity.closeDrawer(drawerLayout);
    }
}
