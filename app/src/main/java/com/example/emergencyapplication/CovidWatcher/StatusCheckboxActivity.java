package com.example.emergencyapplication.CovidWatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.emergencyapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StatusCheckboxActivity extends AppCompatActivity {

    private CheckBox  cb_fever, cb_dryCough, cb_fatigue, cb_aches, cb_runnyNose, cb_soreThroat, cb_shortnessBreath, cb_diarrhea, cb_headache, cb_smellAndTaste;
    private Button btn_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_checkbox);

        cb_fever = findViewById(R.id.checkbox_fever);
        cb_dryCough = findViewById(R.id.checkbox_dry_cough);
        cb_fatigue = findViewById(R.id.checkbox_fatigue);
        cb_aches = findViewById(R.id.checkbox_aches);
        cb_runnyNose = findViewById(R.id.checkbox_runny_nose);
        cb_soreThroat = findViewById(R.id.checkbox_sore_throat);
        cb_shortnessBreath = findViewById(R.id.checkbox_shortness);
        cb_diarrhea = findViewById(R.id.checkbox_diarrhea);
        cb_headache = findViewById(R.id.checkbox_headache);
        cb_smellAndTaste = findViewById(R.id.checkbox_smellAndTaste);
        btn_confirm = findViewById(R.id.btn_confirm);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userStatus();
            }
        });
    }

    private void userStatus() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        String userID = user.getUid();

        //for retrieving name and number from Users database
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userProfile = snapshot.getValue(User.class);

                String status;
                String firstName = userProfile.f_name;
                String lastName = userProfile.l_name;
                String number = userProfile.number;

                //condition for checkboxes of symptoms
                if(cb_fever.isChecked()||cb_headache.isChecked()||cb_dryCough.isChecked()
                        ||cb_fatigue.isChecked()||cb_aches.isChecked()||cb_runnyNose.isChecked() ||cb_soreThroat.isChecked()
                        ||cb_shortnessBreath.isChecked()||cb_diarrhea.isChecked() ||cb_smellAndTaste.isChecked())
                {
                    status = "active case";

                    User userStatus = new User(firstName, lastName, number, status);

                    //database for UserStatus
                    database.getReference("UserStatus").child(userID).setValue(userStatus)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(StatusCheckboxActivity.this, "User has been registered", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(StatusCheckboxActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
                else {
                    status = "clear";
                    Log.d("CHECKBOX: ", status);
                    User userStatus = new User(firstName, lastName, number, status);

                    database.getReference("UserStatus").child(userID).setValue(userStatus)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(StatusCheckboxActivity.this, "User has been registered", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(StatusCheckboxActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }

                String hasFever = String.valueOf(cb_fever.isChecked());
                String hasCough = String.valueOf(cb_dryCough.isChecked());
                String hasFatigue = String.valueOf(cb_fatigue.isChecked());
                String hasAches = String.valueOf(cb_aches.isChecked());
                String hasRunnyNose = String.valueOf(cb_runnyNose.isChecked());
                String hasSoreThroat = String.valueOf(cb_soreThroat.isChecked());
                String hasShortnessOfBreath = String.valueOf(cb_shortnessBreath.isChecked());
                String hasDiarrhea = String.valueOf(cb_diarrhea.isChecked());
                String hasHeadache = String.valueOf(cb_headache.isChecked());
                String hasNoSmellAndTaste = String.valueOf(cb_smellAndTaste.isChecked());

                User symptoms = new User(hasFever, hasCough, hasFatigue, hasAches, hasRunnyNose, hasSoreThroat,hasShortnessOfBreath, hasDiarrhea,
                        hasHeadache, hasNoSmellAndTaste);

                //database for symptoms
                database.getReference("Symptoms").child(userID).setValue(symptoms).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(StatusCheckboxActivity.this, "Health Check is completed", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(StatusCheckboxActivity.this, "Health Check is Error", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(StatusCheckboxActivity.this, CovidWatcherActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });


                Intent intent = new Intent(StatusCheckboxActivity.this, CovidDashboard.class);
                startActivity(intent);
                finish();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(StatusCheckboxActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });

    }
}