package com.example.emergencyapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class FireActivity extends AppCompatActivity {

    TextView tv_fireCon1, tv_fireCon2, tv_fireCon3, tv_fireCon4, tv_fireCon5, tv_fireCon7;
    ImageButton btn_fireCon1, btn_fireCon2,btn_fireCon3, btn_fireCon4, btn_fireCon5, btn_fireCon7;
    private static final int REQUEST_CALL = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fire);

        tv_fireCon1 = findViewById(R.id.tv_fireCon1); tv_fireCon5 = findViewById(R.id.tv_fireCon5);
        tv_fireCon2 = findViewById(R.id.tv_fireCon2);
        tv_fireCon3 = findViewById(R.id.tv_fireCon3); tv_fireCon7 = findViewById(R.id.tv_fireCon7);
        tv_fireCon4 = findViewById(R.id.tv_fireCon4);

        btn_fireCon1 = findViewById(R.id.btn_fireCon1); btn_fireCon5 = findViewById(R.id.btn_fireCon5);
        btn_fireCon2 = findViewById(R.id.btn_fireCon2);
        btn_fireCon3 = findViewById(R.id.btn_fireCon3); btn_fireCon7 = findViewById(R.id.btn_fireCon7);
        btn_fireCon4 = findViewById(R.id.btn_fireCon4);


        btn_fireCon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makePhoneCall(tv_fireCon1);
            }
        });

        btn_fireCon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makePhoneCall(tv_fireCon2);
            }
        });

        btn_fireCon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makePhoneCall(tv_fireCon3);
            }
        });

        btn_fireCon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makePhoneCall(tv_fireCon4);
            }
        });

        btn_fireCon5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makePhoneCall(tv_fireCon5);
            }
        });



        btn_fireCon7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makePhoneCall(tv_fireCon7);
            }
        });

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if(requestCode == REQUEST_CALL){

            if(ActivityCompat.checkSelfPermission(FireActivity.this, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED)  {
                Intent intent = new Intent(Intent.ACTION_CALL);
                String number = tv_fireCon1.getText().toString();
                intent.setData(Uri.parse("tel:"+number));
                startActivity(intent);
                finish();
            }
        }
    }
    public void makePhoneCall(TextView tv_contact_number)
    {
        this.tv_fireCon1 = tv_contact_number;
        String number = tv_contact_number.getText().toString();

        if(ActivityCompat.checkSelfPermission(FireActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(FireActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }
        else{
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+number));
            startActivity(intent);
            finish();
        }
    }
}