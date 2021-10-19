package com.example.emergencyapplication.TrustedContactsActivites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import com.example.emergencyapplication.Adapter.CustomAdapter;
import com.example.emergencyapplication.EntityClass.TrustedContacts;
import com.example.emergencyapplication.Database.TrustedContactsRepository;
import com.example.emergencyapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    ArrayList<TrustedContacts> trustedContactsArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        recyclerView = (RecyclerView)findViewById(R.id.rv_trustedContacts);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        new LoadDataTask().execute();

    }

    //==============Load Data Task=======================================

    class LoadDataTask extends AsyncTask<Void, Void, Void> {

        TrustedContactsRepository trustedContactsRepository;
        List<TrustedContacts> trustedContactsList;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            trustedContactsRepository = new TrustedContactsRepository(getApplicationContext());
        }

        @Override
        protected Void doInBackground(Void... voids) {

            trustedContactsList = trustedContactsRepository.getTrustedContacts();
            trustedContactsArrayList = new ArrayList<>();

            for(int i = 0; i<trustedContactsList.size(); i++){

                trustedContactsArrayList.add(trustedContactsList.get(i));

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            CustomAdapter customAdapter =  new CustomAdapter(trustedContactsArrayList, ViewActivity.this);
            recyclerView.setAdapter(customAdapter);
        }
    }


    //==============Load Data Task=======================================

    protected void onRestart(){
        super.onRestart();
        new LoadDataTask().execute();
    }

}