package com.example.emergencyapplication.SideDockContents;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.emergencyapplication.MainAdapter;
import com.example.emergencyapplication.MainDashboardActivity;
import com.example.emergencyapplication.R;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SirenAndFlickerLightSideDock extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView btMenu;
    Button btn_siren, btn_flickerLight;
    RecyclerView recyclerView;
    private static final int CAMERA_REQUEST = 123;
    boolean hasCamerFlash = false;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siren_and_flicker_light_side_dock);

        //Assign values to initialized variables

        drawerLayout = findViewById(R.id.drawer_layout);
        btMenu = findViewById(R.id.bt_back);
        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new MainAdapter( this, MainDashboardActivity.arrayList));

        ActivityCompat.requestPermissions(SirenAndFlickerLightSideDock.this,
                new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
        hasCamerFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        final AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        final int originalVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        final MediaPlayer mediaPlayer = MediaPlayer.create(SirenAndFlickerLightSideDock.this, R.raw.emergency_alarm);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0);

            }
        });

        btn_siren = findViewById(R.id.btn_siren);
        btn_siren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
                else{
                    mediaPlayer.start();
                }
            }
        });

        btn_flickerLight = findViewById(R.id.btn_flickerLight);
        btn_flickerLight.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                    try {
                            String cameraString = "10101010101010";
                            long blinkDelay = 150;

                            for (int i = 0; i<cameraString.length(); i++){
                                if(cameraString.charAt(i)== '0'){
                                    String cameraId = cameraManager.getCameraIdList()[0];
                                    cameraManager.setTorchMode(cameraId, true);
                                }
                                else{
                                    try{
                                        String cameraId = cameraManager.getCameraIdList()[0];
                                        cameraManager.setTorchMode(cameraId, false);
                                    }
                                    catch (CameraAccessException e){
                                        e.printStackTrace();
                                    }
                                    try {
                                        Thread.sleep(blinkDelay);
                                    }
                                    catch (InterruptedException e){
                                        e.printStackTrace();
                                    }
                                }
                            }
                    }
                    catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(SirenAndFlickerLightSideDock.this, "Android version not supported", Toast.LENGTH_SHORT);
                }


            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainDashboardActivity.closeDrawer(drawerLayout);
    }
}
