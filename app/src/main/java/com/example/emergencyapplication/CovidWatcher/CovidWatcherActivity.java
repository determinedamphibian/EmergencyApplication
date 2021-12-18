package com.example.emergencyapplication.CovidWatcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;;
import android.content.SharedPreferences;
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
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.facebook.FacebookSdk;

import java.util.Arrays;

public class CovidWatcherActivity extends AppCompatActivity {

    Button btn_login;
    ImageView btn_back, btn_phone, btn_fb;
    TextView tv_sign_up, tv_forgot_password;
    ProgressBar progressBar;
    FirebaseApp firebaseApp;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private CallbackManager callbackManager;
    private static String MAIN_TAG = "FacebookAuth";
    private AccessTokenTracker accessTokenTracker;
    TextInputEditText et_username, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_covid_watcher);

        firebaseApp.initializeApp(CovidWatcherActivity.this);
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
                Intent intent = new Intent(CovidWatcherActivity.this, OTPAuthActivity.class);
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

        tv_forgot_password = findViewById(R.id.forgot_password);
        tv_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CovidWatcherActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });


        callbackManager = CallbackManager.Factory.create();
        btn_fb = findViewById(R.id.image_facebook);
        btn_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(CovidWatcherActivity.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        handleFacebookToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d(MAIN_TAG, "onCancel:");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(MAIN_TAG, "onError: "+error);
                    }
                });
            }
        });

//        authStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = mAuth.getCurrentUser();
//                if(user!= null){
//                    updateUI(user, isNewUSer);
//                }
//                else{
//                    updateUI(null, isNewUSer);
//                }
//            }
//        };
//
//        accessTokenTracker = new AccessTokenTracker() {
//            @Override
//            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//                if(currentAccessToken == null){
//                    mAuth.signOut();
//                }
//            }
//        };
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//
//        if(currentUser != null){
//            updateUI(currentUser);
//        }
//    }

    private void handleFacebookToken(AccessToken token) {

        Log.d(MAIN_TAG, "handleFacebookToken: "+token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Log.d(MAIN_TAG, "signInWithCredential: successful");
                    FirebaseUser user = mAuth.getCurrentUser();
                    boolean isNewUSer = task.getResult().getAdditionalUserInfo().isNewUser();

                    if(isNewUSer == true){
                        updateUI(user, isNewUSer);
                    }
                    else{
                        updateUI(user, isNewUSer);
                    }

                }
                else{
                    try{
                    Log.d(MAIN_TAG, "signInWithCredential: failed", task.getException());
                    Toast.makeText(CovidWatcherActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception FirebaseAuthUserCollisionException){
                        Toast.makeText(CovidWatcherActivity.this, "Email already exists!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateUI(FirebaseUser user, boolean isNewUSer) {
        if(user != null){

            if(isNewUSer == true){
                Intent intent = new Intent(CovidWatcherActivity.this, FacebookSignUpActivity.class);
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("authentication", "facebook");
                startActivity(intent);
                finish();
            }
            else{
                Intent intent = new Intent(CovidWatcherActivity.this, StatusCheckboxActivity.class);
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("authentication", "facebook");
                startActivity(intent);
                finish();
            }


        }
        else{
            Toast.makeText(CovidWatcherActivity.this, "Please login to continue", Toast.LENGTH_LONG).show();
        }
    }


    private void userLogin() {
        String email = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        //storing password for user's session
        SharedPreferences sharedPreferences =  getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.putString("authentication", "email");
        editor.commit();

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
        //login via email and password
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    //getting current used email
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()){
                        Intent intent = new Intent(CovidWatcherActivity.this, StatusCheckboxActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        user.sendEmailVerification();
                        Toast.makeText(CovidWatcherActivity.this, "Check your email to verify account", Toast.LENGTH_LONG).show();
                    }

                }
                else {
                    Toast.makeText(CovidWatcherActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }


}