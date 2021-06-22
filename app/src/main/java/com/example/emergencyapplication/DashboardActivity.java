package com.example.emergencyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

public class DashboardActivity extends AppCompatActivity {

    private ImageButton imageButton_trustedContacts, imageButton_crime, imageButton_medical, imageButton_fire;
    private Animation topAnim;
    private ConstraintLayout constraintLayout_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        constraintLayout_header = (ConstraintLayout) findViewById(R.id.constraintLayout_header);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.header_animation);

        constraintLayout_header.setAnimation(topAnim);


        //trusted contact button
        imageButton_trustedContacts = (ImageButton) findViewById(R.id.trustedContacts_imageButton);
        imageButton_trustedContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTrustedContactsForms();
            }
        });

//crime button
        imageButton_crime = (ImageButton) findViewById(R.id.crime_imageButton);
        imageButton_crime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCrimeForms();
            }
        });

        imageButton_medical = (ImageButton) findViewById(R.id.medical_imageButton);
        imageButton_medical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMedicalForms();
            }
        });

        imageButton_fire = (ImageButton) findViewById(R.id.fire_imageButton);
        imageButton_fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFireForms();
            }
        });

    }

    //trusted contacts button method
    public void openTrustedContactsForms() {
        Intent intent = new Intent(this, TrustedContactsActivity.class);
        startActivity(intent);
    }

    //crime button method
    public void openCrimeForms() {
        Intent intent = new Intent(this, CrimeActivity.class);
        startActivity(intent);
    }

    public void openMedicalForms(){
        Intent intent = new Intent(this, MedicalActivity.class);
        startActivity(intent);
    }
    public void openFireForms(){
        Intent intent = new Intent(this, FireActivity.class);
        startActivity(intent);
    }
}