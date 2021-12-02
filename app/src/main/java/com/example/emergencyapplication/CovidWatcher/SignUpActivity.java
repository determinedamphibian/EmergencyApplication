package com.example.emergencyapplication.CovidWatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.emergencyapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    TextInputEditText txt_first_name, txt_last_name, txt_username, txt_email, txt_number, txt_address, txt_password, txt_re_password;
    Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        txt_first_name = findViewById(R.id.et_f_name);
        txt_last_name = findViewById(R.id.et_l_name);
        txt_email = findViewById(R.id.et_email);
        txt_username = findViewById(R.id.et_username);
        txt_number = findViewById(R.id.et_number);
        txt_address = findViewById(R.id.et_address);
        txt_password = findViewById(R.id.et_password);
        txt_re_password = findViewById(R.id.et_re_password);

        btn_register = findViewById(R.id.btn_register_sign_up);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser() {
        String firstName = txt_first_name.getText().toString().trim();
        String lastName = txt_last_name.getText().toString().trim();
        String email = txt_email.getText().toString().trim();
        String username = txt_username.getText().toString().trim();
        String number = "+639"+txt_number.getText().toString().trim();
        String address = txt_address.getText().toString().trim();
        String password = txt_password.getText().toString().trim();
        String re_password = txt_re_password.getText().toString().trim();

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
        if(number.isEmpty()){
            txt_number.setError("This is required!");
            txt_number.requestFocus();
            return;
        }
        if(address.isEmpty()){
            txt_address.setError("This is required!");
            txt_address.requestFocus();
            return;
        }
        if(password.isEmpty()){
            txt_password.setError("This is required!");
            txt_password.requestFocus();
            return;
        }
        if(password.length() < 9){
            txt_password.setError("At least 9 characters are required");
            txt_password.requestFocus();
            return;
        }
        if(re_password.isEmpty()){
            txt_re_password.setError("This is required!");
            txt_re_password.requestFocus();
            return;
        }
        if(!password.equals(re_password)){
            txt_password.setError("Password does not match!");
            txt_re_password.setError("Password does not match!");
            txt_password.requestFocus();
            txt_re_password.requestFocus();
            return;
        }

        //sign-in with email and password
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            //passing the user object through the database
                            User user = new User(firstName, lastName, email, username, address, number);
                            Log.d("Database", ""+FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()));

                            //assigning and creation of database
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            database.getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUpActivity.this, "User has been registered", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                    else {
                                            Toast.makeText(SignUpActivity.this, "Registration failed!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(SignUpActivity.this, "Registration failed!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}