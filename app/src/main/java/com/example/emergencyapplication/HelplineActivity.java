package com.example.emergencyapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.pm.PackageInfoCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class HelplineActivity extends AppCompatActivity {
    ImageButton btn_hopeline, btn_intouch, btn_mentalhealth;
    TextView tv_num_hopeline, tv_num_intouch, tv_num_mentalhealth;
    ImageView btn_back;

    private static final int REQUEST_CALL = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_helpline);

        btn_back = findViewById(R.id.bt_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_num_hopeline = findViewById(R.id.tv_num_hopeline);
        btn_hopeline = findViewById(R.id.btn_hopeline);

        btn_hopeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall(tv_num_hopeline);
            }
        });

        tv_num_intouch = findViewById(R.id.tv_num_intouch);
        btn_intouch = findViewById(R.id.btn_intouch);

        btn_intouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall(tv_num_intouch);
            }
        });

        tv_num_mentalhealth = findViewById(R.id.tv_num_mentalhealth);
        btn_mentalhealth = findViewById(R.id.btn_mentalhealth);

        btn_mentalhealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall(tv_num_mentalhealth);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CALL) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED)
            {
                Intent intent = new Intent(Intent.ACTION_CALL);
                String number = tv_num_hopeline.getText().toString();
                intent.setData(Uri.parse("tel:"+number));
                startActivity(intent);
                finish();
            }
        }

    }

    private void makePhoneCall(TextView tv_contact_num) {
        this.tv_num_hopeline = tv_contact_num;
        String number = tv_contact_num.getText().toString();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }

        else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+ number));
            startActivity(intent);
            finish();
        }



    }
}