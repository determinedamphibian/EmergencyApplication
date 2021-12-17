package com.example.emergencyapplication.CovidWatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.emergencyapplication.R;
import com.example.emergencyapplication.databinding.ActivityOTPAuthBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class OTPAuthActivity extends AppCompatActivity {

    private ActivityOTPAuthBinding binding;

    //if code send failed, will used to resend code OTP
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private String mVerificationId; // will hold OTP/Verification code

    private static final String TAG = "MAIN_TAG";

    private FirebaseAuth auth;

    //progress dialog
    private ProgressDialog pd;


    //global variable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_auth);

        binding = ActivityOTPAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.phoneLinearLayout.setVisibility(View.VISIBLE); //show the phone layout
        binding.codeLinearLayout.setVisibility(View.GONE); //hides the otp layout

        auth = FirebaseAuth.getInstance();
//        db = FirebaseFirestore.getInstance();

        pd = new ProgressDialog(this);
        pd.setTitle("Please wait...");
        pd.setCanceledOnTouchOutside(false);


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                /*This callback will be invoked in two situations:
                1 - Instant verification. In some cases the phone number can be instantly
                      verified without needing to send or enter a verification code.
                  2 - Auto-retrieval. On some devices Google Play services can automatically
                      detect the incoming verification SMS and perform verification without
                      user action*/

                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
              /* This callback is invoked in an invalid request for verification is made,
                  for instance if the phone number format is not valid */

                pd.dismiss();
                Toast.makeText(OTPAuthActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                super.onCodeSent(verificationId, forceResendingToken);
                /* The SMS verification code has been sent to the provided phone number, we
                 now need to ask the user to enter the code and then construct a credential by
                 combining the code with a verification ID*/

                Log.d(TAG, "onCodSent: "+mVerificationId);

                mVerificationId = verificationId;
                forceResendingToken = token;
                pd.dismiss();

                //hide phone layout, show code layout
                binding.phoneLinearLayout.setVisibility(View.GONE);
                binding.codeLinearLayout.setVisibility(View.VISIBLE);

                Toast.makeText(OTPAuthActivity.this, "Verification code sent", Toast.LENGTH_SHORT).show();
                binding.codeSentDescription.setText("Please type the verification code we sent\nto"+"+639"+binding.etNumber.getText().toString().trim());
            }
        };

        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = "+639"+binding.etNumber.getText().toString().trim();
                if (TextUtils.isEmpty(phone)){
                    Toast.makeText(OTPAuthActivity.this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
                }
                else {
                    startPhoneNumberVerification(phone);
                }
            }
        });
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = binding.etCode.getText().toString().trim();
                if (TextUtils.isEmpty(code)){
                    Toast.makeText(OTPAuthActivity.this, "Please enter a verification code", Toast.LENGTH_SHORT).show();
                }
                else {
                    verifyPhoneNumberWithCode(mVerificationId, code);
                }
            }
        });

        binding.resendCodeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = "+639"+binding.etNumber.getText().toString().trim();
                if (TextUtils.isEmpty(phone)){
                    Toast.makeText(OTPAuthActivity.this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
                }
                else {
                    resendVerificationCode(phone, forceResendingToken);
                }
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OTPAuthActivity.this, CovidWatcherActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    //method for startPhoneNumberVerification
    private void startPhoneNumberVerification(String phone) {
        pd.setMessage("Verifying Phone Number");
        pd.show();

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    //method for resend code
    private void resendVerificationCode(String phone, PhoneAuthProvider.ForceResendingToken token) {
        pd.setMessage("Resending code");
        pd.show();

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phone)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .setForceResendingToken(token)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    //method for verifying phone number and calling signInWithPhoneAuthCredential
    private void verifyPhoneNumberWithCode(String verificationID, String code) {
        pd.setMessage("Verifying code");
        pd.show();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, code);
        signInWithPhoneAuthCredential(credential);
    }


    //method for signInWithPhoneAuthCredential
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        pd.setMessage("Logging In");

        auth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                //successfully signed in

                boolean isNewUser = authResult.getAdditionalUserInfo().isNewUser();
                String phone = auth.getCurrentUser().getPhoneNumber();
                Log.d("CURRENT PHONE NUMBER", phone);

                if(isNewUser == true){
                    Intent intent = new Intent(OTPAuthActivity.this, PhoneSignUpActivity.class);
                    intent.putExtra("number", phone);
                    startActivity(intent);
                    finish();

                }
                else{
                    Intent intent = new Intent(OTPAuthActivity.this, StatusCheckboxActivity.class);
                    startActivity(intent);
                    finish();
                }
                pd.dismiss();


//                String phone = auth.getCurrentUser().getPhoneNumber();
//                Toast.makeText(OTPAuthActivity.this, "Logged in as "+phone, Toast.LENGTH_LONG).show();
//
//                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//                Intent intent;
//
//                if(uid == null){
//                    intent = new Intent(OTPAuthActivity.this, PhoneSignUpActivity.class);
//                }
//                else{
//                    intent = new Intent(OTPAuthActivity.this, StatusCheckboxActivity.class);
//                }
//                startActivity(intent);
//                finish();

            }

        }).addOnFailureListener(new OnFailureListener() {
            //failed signing in
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(OTPAuthActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


//
//    public void getUserInformation(HashMap user){
//        this.userClone = user;
//    }
//
//    private void insertUser(HashMap user){
//
//        FirebaseUser currentUser = auth.getCurrentUser();
//
//        String uid = currentUser.getUid();
//
//        Log.d("uid",uid);
//
//        db.collection("users").document(uid).set(user);
//    }
}