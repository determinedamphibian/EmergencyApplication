package com.example.emergencyapplication.CovidWatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emergencyapplication.MainDashboardActivity;
import com.example.emergencyapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CovidWatcherActivity extends AppCompatActivity {

    Button btn_login;
    ImageView btn_back, btn_phone;
    TextView tv_sign_up;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    TextInputEditText et_username, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_watcher);

        et_username = findViewById(R.id.et_email_login);
        et_password = findViewById(R.id.et_password_login);
        progressBar = findViewById(R.id.progress_bar);
        mAuth = FirebaseAuth.getInstance();


        btn_back = findViewById(R.id.bt_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CovidWatcherActivity.this, MainDashboardActivity.class);
                startActivity(intent);
            }
        });

        btn_phone = findViewById(R.id.image_phone);
        btn_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CovidWatcherActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        tv_sign_up = findViewById(R.id.tv_sign_up);
        tv_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CovidWatcherActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

    private void userLogin() {
        String email = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        if(email.isEmpty()){
            et_username.setError("This is required!");
            et_username.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_username.setError("Please provide a valid email");
            et_username.requestFocus();
            return;
        }
        if(password.isEmpty()){
            et_password.setError("Entered a wrong password");
            et_password.requestFocus();
            return;
        }
        if(password.length() < 9){
            et_password.setError("At least 9 characters are required");
            et_password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Intent intent = new Intent(CovidWatcherActivity.this, CovidDashboard.class);
                    startActivity(intent);
                }else
                {
                    Toast.makeText(CovidWatcherActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}