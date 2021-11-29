package com.example.emergencyapplication.CovidWatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emergencyapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReProfileActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private TextInputEditText re_f_name, re_l_name, re_username, re_email, re_number, re_address;
    private Button btn_update_re_profile;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_profile);



        btn_update_re_profile = findViewById(R.id.btn_re_profile);
        btn_update_re_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateProfile();
            }
        });
    }

    private void updateProfile() {

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        re_f_name = findViewById(R.id.et_re_f_name);
        re_l_name = findViewById(R.id.et_re_l_name);
        re_username = findViewById(R.id.et_re_username);
        re_email = findViewById(R.id.et_re_email);
        re_number = findViewById(R.id.et_re_number);
        re_address = findViewById(R.id.et_re_address);

        String firstName = re_f_name.getText().toString().trim();
        String lastName = re_l_name.getText().toString().trim();
        String userName = re_username.getText().toString().trim();
        String email = re_email.getText().toString().trim();
        String number = "+639"+re_number.getText().toString().trim();
        String address = re_address.getText().toString().trim();

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        User userReProfile = new User(firstName, lastName, email, userName, address, number);
        reference.child(userID).setValue(userReProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ReProfileActivity.this, "User has been updated!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(ReProfileActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(ReProfileActivity.this, "Updating failed!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}