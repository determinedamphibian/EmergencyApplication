package com.example.emergencyapplication.SideDockContents;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.emergencyapplication.Adapter.CustomAdapter;
import com.example.emergencyapplication.Database.TrustedContactsRepository;
import com.example.emergencyapplication.EntityClass.TrustedContacts;
import com.example.emergencyapplication.MainAdapter;
import com.example.emergencyapplication.MainDashboardActivity;
import com.example.emergencyapplication.R;
import com.example.emergencyapplication.TrustedContactsActivites.InsertActivity;
import com.example.emergencyapplication.TrustedContactsActivites.TrustedContactsMessageActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TrustedContactsSideDock extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView btMenu;
    ImageButton imgBtn_message;
    RecyclerView recyclerView, rv_trustedContacts;
    RecyclerView.LayoutManager layoutManager;
    FloatingActionButton fbtn_addTrustedContacts;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trusted_contacts_side_dock);

        //Assign values to initialized variables
        drawerLayout = findViewById(R.id.drawer_layout);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MainAdapter( this, MainDashboardActivity.arrayList));

        rv_trustedContacts = findViewById(R.id.rv_trustedContacts);
        rv_trustedContacts.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager( this);
        rv_trustedContacts.setLayoutManager(layoutManager);
        rv_trustedContacts.setItemAnimator(new DefaultItemAnimator());

        new LoadData().execute();

        //Menu button
        btMenu = findViewById(R.id.bt_back);
        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //Floating Button to Insert contacts
        fbtn_addTrustedContacts = findViewById(R.id.fbtn_addTrustedContacts);
        fbtn_addTrustedContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrustedContactsSideDock.this, InsertActivity.class);
                startActivity(intent);
            }
        });

        //Image button for message composition
        imgBtn_message = findViewById(R.id.imgBtn_message);
        imgBtn_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrustedContactsSideDock.this, TrustedContactsMessageActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        MainDashboardActivity.closeDrawer(drawerLayout);
    }

    class LoadData extends AsyncTask <Void, Void, Void> {

        TrustedContactsRepository trustedContactsRepository;
        List<TrustedContacts> trustedContactsList;
        ArrayList<TrustedContacts> trustedContactsArrayList;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            trustedContactsRepository = new TrustedContactsRepository(getApplicationContext());
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            CustomAdapter customAdapter =  new CustomAdapter(trustedContactsArrayList, TrustedContactsSideDock.this);
            rv_trustedContacts.setAdapter(customAdapter);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            {

                trustedContactsList = trustedContactsRepository.getTrustedContacts();
                trustedContactsArrayList = new ArrayList<>();

                for(int i = 0; i<trustedContactsList.size(); i++){

                    trustedContactsArrayList.add(trustedContactsList.get(i));

                }
            return null;
            }
        }
    }
}
