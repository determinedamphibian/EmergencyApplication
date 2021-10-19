package com.example.emergencyapplication.TrustedContactsActivites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.emergencyapplication.Database.TrustedContactsRepository;
import com.example.emergencyapplication.MainDashboardActivity;
import com.example.emergencyapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InstantSOS extends AppCompatActivity implements LocationListener {
    static String userEmergencyMessage;
    private LocationManager locationManager;
    Button btn_later, btn_ok;
    private static final int PERMISSION_REQUEST_ENABLE_GPS = 9002;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        MainDashboardActivity mainDashboardActivity = new MainDashboardActivity();
        mainDashboardActivity.getLocation();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instant_s_o_s);
        //=====================checks GPS permission===================================
        if (ContextCompat.checkSelfPermission(
                InstantSOS.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(InstantSOS.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }
        //=====================checks GPS permission ends===================================

        //=====================checks SMS permission ends===================================
        if (ContextCompat.checkSelfPermission(InstantSOS.this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(InstantSOS.this, new String[]
                    {Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);
        }
        //=====================checks SMS permission ends==================================
        enableGPS();
        getLocation();

    }
    private void enableGPS(){
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(!locationManager.isProviderEnabled(locationManager.GPS_PROVIDER)){
            userAlertGPSToEnable();
        }

    }

    private void userAlertGPSToEnable() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = InstantSOS.this.getLayoutInflater().inflate(R.layout.activity_alert_dialog_customed, null);
        builder.setView(view);
        final AlertDialog alert = builder.create();
        alert.show();

        btn_later = (Button) view.findViewById(R.id.btn_cancel_action);
        btn_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

        btn_ok = (Button) view.findViewById(R.id.btn_ok_action);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, PERMISSION_REQUEST_ENABLE_GPS);
                alert.dismiss();
            }
        });

    }

    private void getLocation(){
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, InstantSOS.this);
    }

    @Override
    public void onLocationChanged(Location location) {
        try {

            Geocoder geocoder = new Geocoder(InstantSOS.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
            userEmergencyMessage = sharedPreferences.getString("text", "");
            String message = userEmergencyMessage+" "+ "Please locate me here with this link https://www.google.com/maps/search/?api=1&query="+location.getLatitude()+","+location.getLongitude();
            Log.d("TrustedContactMessage: ", message);

            new InstantSOS.LoadDataTasks(message).execute();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    class LoadDataTasks extends AsyncTask <Void, Void, Void>{

        TrustedContactsRepository trustedContactsRepository;
        List <String> trustedContactsNumberArrayList;
        List<String> trustedContactNumberList;
        String message;

        LoadDataTasks(String message){
            this.message = message;

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            trustedContactsRepository = new TrustedContactsRepository(getApplicationContext());
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            trustedContactNumberList = trustedContactsRepository.getTrustedContactNumbers();
            trustedContactsNumberArrayList = new ArrayList<>();

            if(trustedContactNumberList.isEmpty()){

                Log.d("Trusted_Contacts", "Empty");

                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(InstantSOS.this, "Your trusted contacts is empty",Toast.LENGTH_LONG).show();
                    }
                });


            }else
                {
                for(int i = 0; i<trustedContactNumberList.size(); i++){

                    String contactNumber = trustedContactNumberList.get(i);

                    SmsManager smsmanager = SmsManager.getDefault();
                    smsmanager.sendTextMessage(contactNumber,null,message,null,null);

                    Log.d("Trusted Contacts", contactNumber+" received "+message);
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(InstantSOS.this, "Message sent",Toast.LENGTH_LONG).show();
                    }
                });

            }
            Intent intent = new Intent(InstantSOS.this, MainDashboardActivity.class);
            startActivity(intent);
            return null;
        }
    }
}