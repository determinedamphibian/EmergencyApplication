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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class CrimeActivity extends AppCompatActivity {
    private TextView tv_contact_number,
            tv_contact_number2, tv_contact_number3, tv_contact_number4, tv_contact_number5;
    private ImageButton call_button, call_button2, call_button3, call_button4, call_button5;
    ImageView btn_back;
    private static final int REQUEST_CALL = 1;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_crime_contacts);

        btn_back = findViewById(R.id.bt_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CrimeActivity.this, EmergencyHotlineButtonActivity.class);
                startActivity(intent);
            }
        });

        //call contact1
        tv_contact_number = (TextView) findViewById(R.id.pcp_contact_number);
        call_button= (ImageButton) findViewById(R.id.btn_pcp_pulanglupa);
        call_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall(tv_contact_number);
            }
        });

        //call contact2
        tv_contact_number2 = (TextView) findViewById(R.id.pcp_contact_number_dos);
        call_button2= (ImageButton) findViewById(R.id.btn_pcp_manuyo_dos);
        call_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall(tv_contact_number2);
            }
        });

        //call contact3
        tv_contact_number3 = (TextView) findViewById(R.id.pcp_contact_number_bf);
        call_button3= (ImageButton) findViewById(R.id.btn_pcp_bf);
        call_button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall(tv_contact_number3);
            }
        });

        //call contact4
        tv_contact_number4 = (TextView) findViewById(R.id.pcp_contact_number_singko);
        call_button4= (ImageButton) findViewById(R.id.btn_pcp_singko);
        call_button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall(tv_contact_number4);
            }
        });

        //call contact5
        tv_contact_number5 = (TextView) findViewById(R.id.pcp_contact_pulis);
        call_button5= (ImageButton) findViewById(R.id.btn_pcp_pulis);
        call_button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall(tv_contact_number5);
            }
        });
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if(requestCode == REQUEST_CALL){

            if(ActivityCompat.checkSelfPermission(CrimeActivity.this, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED)  {
                Intent intent = new Intent(Intent.ACTION_CALL);
                String number = tv_contact_number.getText().toString();
                intent.setData(Uri.parse("tel:"+number));
                startActivity(intent);
                finish();
            }
        }
    }
    public void makePhoneCall(TextView tv_contact_number)
    {
        this.tv_contact_number = tv_contact_number;
        String number = tv_contact_number.getText().toString();

        if(ActivityCompat.checkSelfPermission(CrimeActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(CrimeActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }
        else{
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+number));
            startActivity(intent);
            finish();
        }
    }

}