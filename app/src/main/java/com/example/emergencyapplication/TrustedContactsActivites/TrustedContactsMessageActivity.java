package com.example.emergencyapplication.TrustedContactsActivites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.emergencyapplication.MainDashboardActivity;
import com.example.emergencyapplication.R;
import com.example.emergencyapplication.SideDockContents.TrustedContactsSideDock;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TrustedContactsMessageActivity extends AppCompatActivity implements LocationListener {

    EditText et_message;
    Button btn_saveMessage, btn_sendMessage, btn_sendMessageTrustedContacts;
    ImageView btn_back;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    String text;
    private static final int PERMISSION_REQUEST_ENABLE_GPS = 9002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trusted_contacts_message);

        btn_back =findViewById(R.id.bt_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        et_message = findViewById(R.id.et_message);
        if( updateData() == false){
            et_message.setText("HELP!!! I'M AT");

        }

        btn_saveMessage = findViewById(R.id.btn_saveMessage);
        btn_saveMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        loadData();
        updateData();

        btn_sendMessage= findViewById(R.id.btn_sendMesage);
        btn_sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enableGPS();
                getLocation();

            }
        });

        btn_sendMessageTrustedContacts = findViewById(R.id.btn_sendMessageTrustedContacts);
        btn_sendMessageTrustedContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enableGPS();
                Intent intent = new Intent(TrustedContactsMessageActivity.this, InstantSOS.class);
                startActivity(intent);
            }
        });
    }



    private void enableGPS() {

        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(!locationManager.isProviderEnabled(locationManager.GPS_PROVIDER)){
            userAlerGPSToEnable();
        }
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT, et_message.getText().toString());
        editor.apply();
        Toast.makeText(TrustedContactsMessageActivity.this, " Message Saved", Toast.LENGTH_SHORT).show();
    }
    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT, "");
    }

    public boolean updateData(){
        et_message.setText(text);
        return true;
    }
    public void getLocation(){

        LocationManager locationManager;
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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, TrustedContactsMessageActivity.this);
    }
    //LocationListener predefined methods
    @Override
    public void onLocationChanged(Location location) {
        try {

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            String userMessage = et_message.getText().toString();

            Geocoder geocoder = new Geocoder(TrustedContactsMessageActivity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

            String messageEmergency = userMessage+" "+ "Please locate me here with this link https://www.google.com/maps/search/?api=1&query="+location.getLatitude()+","+location.getLongitude();

            //SOS message can be send to any share intent
            shareIntent.putExtra(Intent.EXTRA_TEXT, messageEmergency);
            shareIntent.setType("text/plain");
            startActivity(shareIntent);

            Log.d("TrustedContactMessage: ", messageEmergency);


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void userAlerGPSToEnable() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = TrustedContactsMessageActivity.this.getLayoutInflater().inflate(R.layout.activity_alert_dialog_customed, null);
        builder.setView(view);
        final AlertDialog alert = builder.create();

        Button btn_later = (Button) view.findViewById(R.id.btn_cancel_action);
        btn_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

        Button btn_ok = (Button) view.findViewById(R.id.btn_ok_action);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, PERMISSION_REQUEST_ENABLE_GPS);

            }
        });
        alert.show();
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
}