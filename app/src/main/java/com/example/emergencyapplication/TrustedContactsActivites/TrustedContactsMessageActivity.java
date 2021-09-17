package com.example.emergencyapplication.TrustedContactsActivites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.emergencyapplication.MainDashboardActivity;
import com.example.emergencyapplication.R;

import java.util.ArrayList;
import java.util.List;

public class TrustedContactsMessageActivity extends AppCompatActivity {

    EditText et_message;
    Button btn_saveMessage, btn_sendMessage;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trusted_contacts_message);


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

}