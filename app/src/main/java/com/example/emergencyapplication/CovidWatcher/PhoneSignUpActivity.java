package com.example.emergencyapplication.CovidWatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.emergencyapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class PhoneSignUpActivity extends AppCompatActivity {

    TextInputEditText txt_first_name, txt_last_name, txt_username, txt_email, txt_address;
    Button btn_saveProfile;
    private Bundle bundle;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_sign_up);

        btn_saveProfile = findViewById(R.id.btn_register_sign_up);
        btn_saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });
    }

    private void saveProfile() {

        txt_first_name = findViewById(R.id.et_f_name);
        txt_last_name = findViewById(R.id.et_l_name);
        txt_username = findViewById(R.id.et_username);
        txt_email = findViewById(R.id.et_email);
        txt_address = findViewById(R.id.et_address);

        String firstName = txt_first_name.getText().toString().trim();
        String lastName = txt_last_name.getText().toString().trim();
        String username = txt_username.getText().toString().trim();
        String email = txt_email.getText().toString().trim();
        String address = txt_address.getText().toString().trim();

        bundle= getIntent().getExtras();
        String number = bundle.getString("number");

        Log.d("TEST7", ""+firstName+" "+lastName+" "+email+" "+username+" "+address+" "+number);

        if(firstName.isEmpty()){
            txt_first_name.setError("This is required!");
            txt_first_name.requestFocus();
            return;
        }
        if(lastName.isEmpty()){
            txt_last_name.setError("This is required!");
            txt_last_name.requestFocus();
            return;
        }
        if(email.isEmpty()){
            txt_email.setError("This is required!");
            txt_email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            txt_email.setError("Please provide a valid email");
            txt_email.requestFocus();
            return;
        }
        if(username.isEmpty()){
            txt_username.setError("This is required!");
            txt_username.requestFocus();
            return;
        }
        if(address.isEmpty()){
            txt_address.setError("This is required!");
            txt_address.requestFocus();
            return;
        }

        User user = new User(firstName, lastName, email, username, address, number);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(PhoneSignUpActivity.this, "User profile saved", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(PhoneSignUpActivity.this, StatusCheckboxActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(PhoneSignUpActivity.this, "User profile failed", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}