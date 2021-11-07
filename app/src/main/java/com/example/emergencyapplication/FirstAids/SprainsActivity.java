package com.example.emergencyapplication.FirstAids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import com.example.emergencyapplication.MedicalActivity;
import com.example.emergencyapplication.R;

public class SprainsActivity extends AppCompatActivity {

    private ImageView bt_back;
    private WebView webView;
    private Button btn_hospital;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprains);

        bt_back = findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

      String intro = getResources().getString(R.string.Intro_sprains);
        String webText = String.valueOf(Html.fromHtml(
                "<![CDATA]<body style=\"text-align:justify\">"
                        +intro + "</body>"
        ));
        webView = findViewById(R.id.wv_intro_sprains);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.loadData(webText,"text/html;charset=utf-8","UTF-8");

        btn_hospital = findViewById(R.id.btn_hospital);
        btn_hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SprainsActivity.this, MedicalActivity.class);
                startActivity(intent);
            }
        });
    }
}