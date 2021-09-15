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

public class TrustedContactsActivity extends AppCompatActivity implements LocationListener {



    private Button btn_insert, btn_view, btn_sosMessage;
    private LocationManager locationManager;
    private static RecyclerView recyclerView;

    private ConstraintLayout constraintLayout_header;

    private Animation topAnim;

    private List <String> trustedContactsNumberArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trusted_contacts);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        constraintLayout_header = (ConstraintLayout) findViewById(R.id.cl_header_trustedContacts);
        topAnim = AnimationUtils.loadAnimation(this, R.anim.header_animation);
        constraintLayout_header.setAnimation(topAnim);
        btn_insert = (Button)findViewById(R.id.btn_insert);
        btn_view = (Button)findViewById(R.id.btn_view);
        btn_sosMessage = (Button) findViewById(R.id.btn_sosMessage);



        //=====================checks GPS permission===================================
        if (ContextCompat.checkSelfPermission(
                TrustedContactsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(TrustedContactsActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }
        //=====================checks GPS permission ends===================================



        //=====================checks SMS permission ends===================================
        if(ContextCompat.checkSelfPermission(TrustedContactsActivity.this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(TrustedContactsActivity.this, new String[]
                    {Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);
        }

        //=====================checks SMS permission ends===================================

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


        //============ SOS MESSAGE Trusted Button Function Starts================
        btn_sosMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    getLocation();

            }
        });

        //============ SOS MESSAGE Trusted Button Function Ends================
    }

//====================METHODS=============================================//
    @SuppressLint("MissingPermission")
    public void getLocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,TrustedContactsActivity.this);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //===============GETTING LOCATION AS SMS============================//
    @Override
    public void onLocationChanged(Location location) {

        //Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(TrustedContactsActivity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

            String message =( "HELP!!!! I'M AT "+addresses.get(0).getAddressLine(0));
            String message_sent = "Message Sent";
            Toast.makeText(TrustedContactsActivity.this, message_sent,Toast.LENGTH_LONG).show();
            Log.d("TrustedContactMessage: ", message);
            sendSMS(message);

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    //================ Constructors Starts ===============================
//    public TrustedContactsActivity(List<String> contactNumbersList) {
//        listContactNumber = new ArrayList<>(contactNumbersList);
//
//    }


//    public void getContacts(List<String> contactNumbersList) {
//        listContactNumber = contactNumbersList;
//    }
    //=============== Constructors Ends ==================================


    //===============Sending SMS METHOD======================//
    public void sendSMS(String message){


        new TrustedContactsActivity.LoadDataTask(message).execute();

//        for(int i = 0; i<listContactNumber.size(); i++)
//        {
//            String number = listContactNumber.get(i);
//            SmsManager smsmanager = SmsManager.getDefault();
//            Log.d("Number", ""+number);
//            smsmanager.sendTextMessage(number,null,message,null,null);
//            Toast.makeText(TrustedContactsActivity.this, "Sent to "+number,Toast.LENGTH_LONG).show();
//        }
        
    }





    //===============SENDING SMS METHOD ENDS============================//


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    class LoadDataTask extends AsyncTask<Void, Void, Void> {

        TrustedContactsRepository trustedContactsRepository;
        List<String> trustedContactNumberList;
        String message;

        public LoadDataTask(String message) {
            this.message = message;
        }

        public LoadDataTask() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            trustedContactsRepository = new TrustedContactsRepository(getApplicationContext());
        }

        @Override
        protected Void doInBackground(Void... voids) {

            trustedContactNumberList = trustedContactsRepository.getTrustedContactNumbers();
            trustedContactsNumberArrayList = new ArrayList<>();


            for(int i = 0; i<trustedContactNumberList.size(); i++){


                String contactNumber = trustedContactNumberList.get(i);

                SmsManager smsmanager = SmsManager.getDefault();
                smsmanager.sendTextMessage(contactNumber,null,message,null,null);

                Log.d("Trusted Contacts", contactNumber+" received "+message);

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


        }
    }


    //==============Load Data Task=======================================

    protected void onRestart(){
        super.onRestart();
        new TrustedContactsActivity.LoadDataTask().execute();
    }
}
