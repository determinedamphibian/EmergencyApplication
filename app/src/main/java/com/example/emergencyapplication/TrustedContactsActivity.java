package com.example.emergencyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;


import com.example.emergencyapplication.Database.TrustedContactsRepository;
import com.example.emergencyapplication.TrustedContactsActivites.InsertActivity;
import com.example.emergencyapplication.TrustedContactsActivites.ViewActivity;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TrustedContactsActivity extends AppCompatActivity {



    private Button btn_insert, btn_view, btn_sosMessage;
    private ConstraintLayout constraintLayout_header;
    private Animation topAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trusted_contacts);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        constraintLayout_header = findViewById(R.id.cl_header_trustedContacts);
        topAnim = AnimationUtils.loadAnimation(this, R.anim.header_animation);
        constraintLayout_header.setAnimation(topAnim);
        btn_insert = findViewById(R.id.btn_insert);
        btn_view = findViewById(R.id.btn_view);
        btn_sosMessage = findViewById(R.id.btn_sosMessage);



        //============ Insert Trusted Button Function Starts================
        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrustedContactsActivity.this, InsertActivity.class);
                startActivity(intent);
            }
        });
        //============ Insert Trusted Button Function Ends================



        //============ View Trusted Button Function Starts================
        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrustedContactsActivity.this, ViewActivity.class);
                startActivity(intent);
            }
        });
        //============ View Trusted Button Function Ends================



    }

}
