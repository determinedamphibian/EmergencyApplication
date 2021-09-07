package com.example.emergencyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainDashboardActivity extends AppCompatActivity {

    private ImageButton imageButton_trustedContacts, imageButton_crime, imageButton_medical, imageButton_fire;
    DrawerLayout drawerLayout;
    ImageView btMenu;
    RecyclerView recyclerView;
    public static ArrayList<String> arrayList = new ArrayList<>();
    MainAdapter adapter;
    //private Animation topAnim;
    //private ConstraintLayout constraintLayout_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);
        btMenu = findViewById(R.id.bt_menu);
        recyclerView = findViewById(R.id.recycler_view);

        //Clear arrayList
        arrayList.clear();

        //Add menu item to arrayList
        arrayList.add("Home");
        arrayList.add("Trusted Contacts");
        arrayList.add("Government Agencies");
        arrayList.add("Siren & Flicker Light");
        arrayList.add("About Us");

        adapter = new MainAdapter( this, arrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

        btMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //constraintLayout_header = (ConstraintLayout) findViewById(R.id.constraintLayout_header);

        //topAnim = AnimationUtils.loadAnimation(this, R.anim.header_animation);

        //constraintLayout_header.setAnimation(topAnim);


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
        Intent intent = new Intent(this, EmergencyHotlineButtonActivity.class);
        startActivity(intent);
    }
    public void openFireForms(){
        Intent intent = new Intent(this, FireActivity.class);
        startActivity(intent);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){

            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        closeDrawer(drawerLayout);
    }
}