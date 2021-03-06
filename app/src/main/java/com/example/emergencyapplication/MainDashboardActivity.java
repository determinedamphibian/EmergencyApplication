package com.example.emergencyapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
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
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.emergencyapplication.CovidWatcher.CovidWatcherActivity;
import com.example.emergencyapplication.Database.TrustedContactsRepository;
import com.example.emergencyapplication.TrustedContactsActivites.InstantSOS;
import com.example.emergencyapplication.TrustedContactsActivites.TrustedContactsMessageActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainDashboardActivity extends AppCompatActivity implements LocationListener {

    private ImageView imageButton_emergencyHotlines, imageButton_watcher, imageButton_sos, imageButton_guidelines;
    private LocationManager locationManager;
    DrawerLayout drawerLayout;
    ImageView btMenu;
    RecyclerView recyclerView;
    Button btn_later, btn_ok;
    public static ArrayList<String> arrayList = new ArrayList<>();
    MainAdapter adapter;
    static String userEmergencyMessage;
    TrustedContactsRepository trustedContactsRepository;
    List<String> trustedContactsNumberArrayList;
    List<String> trustedContactNumberList;

    private static final int PERMISSION_REQUEST_ENABLE_GPS = 9002;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);

        recyclerView = findViewById(R.id.recycler_view);

        //Clear arrayList
        arrayList.clear();

        //Add menu item to arrayList
        arrayList.add("Home");
        arrayList.add("Trusted Contacts");
        arrayList.add("Government Agencies");
        arrayList.add("Siren & Flicker Light");
        arrayList.add("About Us");

        //setting arrayList in an adapter
        adapter = new MainAdapter(this, arrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainDashboardActivity.this));

        recyclerView.setAdapter(adapter);

        //menu
        btMenu = findViewById(R.id.bt_back);
        btMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        //sos button when clicked!
        imageButton_sos = findViewById(R.id.imgbtn_SOS);
        imageButton_sos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //checks GPS permission
                if (ContextCompat.checkSelfPermission(
                        MainDashboardActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(MainDashboardActivity.this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, 100);
                }


                //checks SMS permission
                if (ContextCompat.checkSelfPermission(MainDashboardActivity.this, Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(MainDashboardActivity.this, new String[]
                            {Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);
                }

                enableGPSForSOS();
//                getLocation();


            }
        });

        //guideline button
       imageButton_guidelines = findViewById(R.id.bt_guidelines);
        imageButton_guidelines.setOnClickListener((View v) -> {
            openGuidelineForm();

        });

        imageButton_emergencyHotlines = findViewById(R.id.bt_emergencyHotline);
        imageButton_emergencyHotlines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEmergencyHotlineForms();

            }
        });

        imageButton_watcher = findViewById(R.id.watcher_imageButton);
        imageButton_watcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCovidWatcher();
            }
        });

        //checks the phone version to know if it is with the required android versions
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Green Archer Notification",
                    "Green Archer Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        else{
            Toast.makeText(MainDashboardActivity.this, "Your phone is not compatible with this feature", Toast.LENGTH_SHORT);
        }

        //calls notification bar for SOS message every startup
        startNotification();

    }

    private void enableGPSForSOS() {
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(!locationManager.isProviderEnabled(locationManager.GPS_PROVIDER)){
            userAlertGPSToEnableSOS();
        }else{
            Intent intent = new Intent(MainDashboardActivity.this, TrustedContactsMessageActivity.class);
            startActivity(intent);
        }
        
    }

    private void userAlertGPSToEnableSOS() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = MainDashboardActivity.this.getLayoutInflater().inflate(R.layout.activity_alert_dialog_customed, null);
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



    private void openCovidWatcher() {

        Intent intent = new Intent(MainDashboardActivity.this, CovidWatcherActivity.class);
        startActivity(intent);
    }


    //asks the user to turn on their gps
    private void enableGPS() {
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(!locationManager.isProviderEnabled(locationManager.GPS_PROVIDER)){
            userAlertGPSToEnable();
        }
    }

    //prompt alertDialog that let the user turn on their gps
    private void userAlertGPSToEnable() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = MainDashboardActivity.this.getLayoutInflater().inflate(R.layout.activity_alert_dialog_customed, null);
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
    //notification bar method
    private void startNotification() {

        //Instantiation of NotificationCompat.Builder that will help to create live notification for SOS Message when the app is clicked
        NotificationCompat.Builder notification = new NotificationCompat.Builder(MainDashboardActivity.this, "Green Archer Notification");

        //setting notification attributes
        notification.setContentTitle("Green Archers Emergency Application").setOngoing(true);
        notification.setContentText("Send S.O.S message");
        notification.setSmallIcon(R.drawable.ic_launcher_background);
        notification.setAutoCancel(true);

        //whenever the starts it asks the user to open their gps
        enableGPS();

        //calling the InstantSOS activity that will flash for a second whenever the notification was pressed
        Intent intent = new Intent(MainDashboardActivity.this, InstantSOS.class);

        //this will perform the previous activity will be called when notification was pressed
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(MainDashboardActivity.this);
        taskStackBuilder.addParentStack(InstantSOS.class).addNextIntent(intent);
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainDashboardActivity.this);
        managerCompat.notify(1, notification.build());

    }

    private void openGuidelineForm() {
        Intent intent = new Intent(this, DisasterAndEmergencyGuidelineButtonActivity.class);
        startActivity(intent);
    }


    public void openEmergencyHotlineForms() {
        Intent intent = new Intent(this, EmergencyHotlineButtonActivity.class);
        startActivity(intent);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        closeDrawer(drawerLayout);
    }

    public void getLocation() {

        //it checks the availability of using google location
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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, MainDashboardActivity.this);

    }


    @Override
    public void onLocationChanged(Location location) {
        try {
//            Geocoder geocoder = new Geocoder(MainDashboardActivity.this, Locale.getDefault());
//            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

            //Getting the string value (message) from the editText of S.O.S message
            SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
            userEmergencyMessage = sharedPreferences.getString("text", "");

            //Getting the latitude and longitude and mapping it with Google Map's link
            String message = userEmergencyMessage+" "+ "Please locate me here with this link https://www.google.com/maps/search/?api=1&query="+location.getLatitude()+","+location.getLongitude();
            Log.d("TrustedContactMessage: ", message);

            new MainDashboardActivity.LoadDataTasks(message).execute();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //predefined methods of LocationListener
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    @SuppressLint("StaticFieldLeak")
    private class LoadDataTasks extends AsyncTask<Void, Void, Void> {


        String message;

        public LoadDataTasks(String message) {
            this.message = message;
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

            if(trustedContactNumberList.isEmpty()){

                Log.d("Trusted_Contacts", "Empty");

                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(MainDashboardActivity.this, "Your trusted contacts is empty",Toast.LENGTH_LONG).show();
                    }
                });


            }else{
                for(int i = 0; i<trustedContactNumberList.size(); i++){

                    String contactNumber = trustedContactNumberList.get(i);

                    SmsManager smsmanager = SmsManager.getDefault();
                    smsmanager.sendTextMessage(contactNumber,null,message,null,null);

                    Log.d("Trusted Contacts", contactNumber+" received "+message);
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(MainDashboardActivity.this, "Message sent",Toast.LENGTH_LONG).show();
                    }
                });

            }



            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }


}