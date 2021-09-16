package com.example.emergencyapplication.TrustedContactsActivites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.example.emergencyapplication.Database.TrustedContactsRepository;
import com.example.emergencyapplication.MainDashboardActivity;
import com.example.emergencyapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InstantSOS extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager;

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
//
        getLocation();

    }

    private void getLocation(){
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

            String message =( "HELP!!!! I'M AT "+addresses.get(0).getAddressLine(0));
            String message_sent = "Message Sent";
            Toast.makeText(InstantSOS.this, message_sent,Toast.LENGTH_LONG).show();
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


            for(int i = 0; i<trustedContactNumberList.size(); i++){

                String contactNumber = trustedContactNumberList.get(i);

                SmsManager smsmanager = SmsManager.getDefault();
                smsmanager.sendTextMessage(contactNumber,null,message,null,null);

                Log.d("Trusted Contacts", contactNumber+" received "+message);

            }
            Intent intent = new Intent(InstantSOS.this, MainDashboardActivity.class);
            startActivity(intent);
            return null;
        }
    }
}