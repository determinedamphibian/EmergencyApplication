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

public class MedicalActivity extends AppCompatActivity {

    TextView tv_hosCon1, tv_hosCon2,tv_hosCon3, tv_hosCon4, tv_hosCon5, tv_hosCon6,
            tv_hosCon10, tv_hosCon12, tv_hosCon13;

    ImageButton btn_hosCon1, btn_hosCon2, btn_hosCon3, btn_hosCon4, btn_hosCon5, btn_hosCon6,
           btn_hosCon10, btn_hosCon12, btn_hosCon13;

    private static final int REQUEST_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_medical);

        tv_hosCon1 = findViewById(R.id.tv_hosCon1);
        tv_hosCon2 = findViewById(R.id.tv_hosCon2);
        tv_hosCon3 = findViewById(R.id.tv_hosCon3);
        tv_hosCon4 = findViewById(R.id.tv_hosCon4); tv_hosCon10 = findViewById(R.id.tv_hosCon10);
        tv_hosCon5 = findViewById(R.id.tv_hosCon5); tv_hosCon12 = findViewById(R.id.tv_hosCon12);
        tv_hosCon6 = findViewById(R.id.tv_hosCon6); tv_hosCon13 = findViewById(R.id.tv_hosCon13);


        btn_hosCon1 = findViewById(R.id.btn_hosCon1);
        btn_hosCon2 = findViewById(R.id.btn_hosCon2);
        btn_hosCon3 = findViewById(R.id.btn_hosCon3);
        btn_hosCon4 = findViewById(R.id.btn_hosCon4); btn_hosCon10 = findViewById(R.id.btn_hosCon10);
        btn_hosCon5 = findViewById(R.id.btn_hosCon5); btn_hosCon12 = findViewById(R.id.btn_hosCon12);
        btn_hosCon6 = findViewById(R.id.btn_hosCon6); btn_hosCon13 = findViewById(R.id.btn_hosCon13);




        btn_hosCon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makePhoneCall(tv_hosCon1);
            }
        });


        btn_hosCon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makePhoneCall(tv_hosCon2);
            }
        });


        btn_hosCon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makePhoneCall(tv_hosCon3);
            }
        });

        btn_hosCon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall(tv_hosCon4);
            }
        });

        btn_hosCon5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makePhoneCall(tv_hosCon5);
            }
        });


        btn_hosCon6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makePhoneCall(tv_hosCon6);
            }
        });

        btn_hosCon10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makePhoneCall(tv_hosCon10);
            }
        });

        btn_hosCon12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makePhoneCall(tv_hosCon12);
            }
        });


        btn_hosCon13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makePhoneCall(tv_hosCon13);
            }
        });
    }



    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if(requestCode == REQUEST_CALL){

            if(ActivityCompat.checkSelfPermission(MedicalActivity.this, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED)  {
                Intent intent = new Intent(Intent.ACTION_CALL);
                String number = tv_hosCon1.getText().toString();
                intent.setData(Uri.parse("tel:"+number));
                startActivity(intent);
                finish();
            }
        }
    }
    public void makePhoneCall(TextView tv_contact_number)
    {
        this.tv_hosCon1 = tv_contact_number;
        String number = tv_contact_number.getText().toString();

        if(ActivityCompat.checkSelfPermission(MedicalActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MedicalActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }
        else{
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+number));
            startActivity(intent);
            finish();
        }
    }
}