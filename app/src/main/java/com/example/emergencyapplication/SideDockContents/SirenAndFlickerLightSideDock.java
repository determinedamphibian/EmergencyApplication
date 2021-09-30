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
    ImageView btn_siren, btMenu, btn_flickerLight;
    RecyclerView recyclerView;

    //will be used for on and off condition on torchlight
    Boolean state = false;
    private static final int CAMERA_REQUEST = 123;
    boolean hasCameraFlash = false;



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
        hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //creating an object of AudioManager that gets the System Service which is audio
        final AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        // creating variable that holds the volume of the streamed audio
        final int originalVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        //object audioManager calls the setStreamVolume method to force the output of audio to be at maximum volume
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);

        //creating an object for MediaPlayer which needs the current class context and the audio located at resources -> raw folder
        final MediaPlayer mediaPlayer = MediaPlayer.create(SirenAndFlickerLightSideDock.this, R.raw.emergency_alarm);

        //mediaPlayer calling the method of setAudioStreamType to get the value from STREAM_MUSIC
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        //method for successful creation of mediaPlayer
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                //audioManager object calls setStreamVolume to stream the audio and get the value of originalVolume which was transformed into max volume
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0);

            }
        });

        //condition to turn on and off siren sound using one button
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

                // android version is checked if it the following operation will be compatible on the device
                //lowest android can use this is Android L or Lollipop
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                    //creating an object of CameraManager and getting the camera service
                    CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                    //for selector xml that animates the flashlight display when pressed
                    v.setActivated(!v.isActivated());

                    if(state == false){

                            try {
                                //number of flickers
                                String cameraString = "1010101010101010";
                                //number of delay (ms)
                                long blinkDelay = 150;

                                for (int i = 0; i<cameraString.length(); i++){
                                    if(cameraString.charAt(i)== '0'){
                                        //turn on the torchlight
                                        String cameraId = cameraManager.getCameraIdList()[0];
                                        cameraManager.setTorchMode(cameraId, true);
                                        state = true;
                                    }
                                    else{
                                        try{
                                            //turn off the torchlight
                                            String cameraId = cameraManager.getCameraIdList()[0];
                                            cameraManager.setTorchMode(cameraId, false);
                                        }
                                        catch (CameraAccessException e){
                                                e.printStackTrace();
                                        }
                                        try {
                                                // duration between flickering
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
                    //turn-off torch light when pressed again
                         else if(state == true){

                             try{
                                 String cameraId = cameraManager.getCameraIdList()[0];
                                 cameraManager.setTorchMode(cameraId, false);
                                 state = false;
                             }
                             catch (CameraAccessException e) {
                                 e.printStackTrace();
                             }
                         }
                         else{
                            Toast.makeText(SirenAndFlickerLightSideDock.this, "Android version not supported", Toast.LENGTH_SHORT);
                         }
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
