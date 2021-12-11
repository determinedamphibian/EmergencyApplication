package com.example.emergencyapplication.CovidWatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emergencyapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReProfileActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private TextInputEditText re_f_name, re_l_name, re_email, re_username, re_number, re_address;
    private Button btn_update_re_profile;
    private ProgressBar progressBar;
    private ImageView btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        re_f_name = findViewById(R.id.et_re_f_name);
        re_l_name = findViewById(R.id.et_re_l_name);
        re_username = findViewById(R.id.et_re_username);
        re_number = findViewById(R.id.et_re_number);
        re_email = findViewById(R.id.et_re_email);
        re_address = findViewById(R.id.et_re_address);

        setValueToEditText();

        btn_update_re_profile = findViewById(R.id.btn_re_profile);
        btn_update_re_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateProfile();
            }
        });

        btn_back = findViewById(R.id.bt_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setValueToEditText() {

        //setting values from database to editText in app
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                String f_name = user.f_name;
                String l_name = user.l_name;
                String email = user.email;
                String username = user.username;
                String address = user.address;
                String number = user.number.substring(4);

                Log.d("TEST2", f_name);

                re_f_name.setText(f_name);
                re_l_name.setText(l_name);
                re_email.setText(email);
                re_username.setText(username);
                re_address.setText(address);
                re_number.setText(number);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateProfile() {

        String firstName = re_f_name.getText().toString().trim();
        String lastName = re_l_name.getText().toString().trim();
        String userName = re_username.getText().toString().trim();
        String email = re_email.getText().toString().trim();
        String number = "+639"+re_number.getText().toString().trim();
        String address = re_address.getText().toString().trim();

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE);
        String currentEmail = sharedPreferences.getString("email",  "");
        String password = sharedPreferences.getString("password", "");

//        Log.d("TEST 3",currentEmail+""+password);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        //validation if email was changed
        if(!currentEmail.equals(email)){
            AuthCredential credential = EmailAuthProvider.getCredential(currentEmail, password);
            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d("RE_AUTH", "User re-authenticated.");
                    user.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Log.d("EMAIL_UPDATE", "User email address updated.");
                            }
                        }
                    });
                }
            });
        }
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