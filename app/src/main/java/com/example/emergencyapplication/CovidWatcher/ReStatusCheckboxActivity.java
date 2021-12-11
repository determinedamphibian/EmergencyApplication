package com.example.emergencyapplication.CovidWatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
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

public class ReStatusCheckboxActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference, referenceStatus;
    private String userID;
    private ProgressBar progressBar;
    private CheckBox  cbFever, cbCough, cbFatigue, cbAches, cbRunnyNose, cbSoreThroat, cbShortness, cbDiarrhea, cbHeadache, cbNoSmellAndTaste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_status_checkbox);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Symptoms");
        userID = user.getUid();

         cbFever = findViewById(R.id.checkbox_fever);
         cbCough = findViewById(R.id.checkbox_dry_cough);
         cbFatigue = findViewById(R.id.checkbox_fatigue);
         cbAches = findViewById(R.id.checkbox_aches);
         cbRunnyNose = findViewById(R.id.checkbox_runny_nose);
         cbSoreThroat = findViewById(R.id.checkbox_sore_throat);
         cbShortness = findViewById(R.id.checkbox_shortness);
         cbDiarrhea = findViewById(R.id.checkbox_diarrhea);
         cbHeadache = findViewById(R.id.checkbox_headache);
         cbNoSmellAndTaste = findViewById(R.id.checkbox_smellAndTaste);

         progressBar = findViewById(R.id.progress_bar);

         //for retrieving values from Symptoms database
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userSymptoms = snapshot.getValue(User.class);

                String hasFever = userSymptoms.hasFever;
                String hasCough = userSymptoms.haCough;
                String hasFatigue = userSymptoms.hasFatigue;
                String hasAches = userSymptoms.hasAches;
                String hasRunnyNose = userSymptoms.hasRunnyNose;
                String hasSoreThroat = userSymptoms.hasSoreThroat;
                String hasShortnessOfBreath = userSymptoms.hasShortnessOfBreath;
                String hasDiarrhea = userSymptoms.hasDiarrhea;
                String hasHeadAche = userSymptoms.hasHeadAche;
                String hasNoSmellandTaste = userSymptoms.hasNoSmellandTaste;

                if(hasFever.equals("true")){
                    cbFever.setChecked(true);
                }
                else{
                    cbFever.setChecked(false);
                }

                if(hasCough.equals("true")){
                    cbCough.setChecked(true);
                }
                else{
                    cbCough.setChecked(false);
                }

                if(hasFatigue.equals("true")){
                    cbFatigue.setChecked(true);
                }
                else{
                    cbFatigue.setChecked(false);
                }

                if(hasAches.equals("true")){
                    cbAches.setChecked(true);
                }
                else{
                    cbAches.setChecked(false);
                }

                if(hasRunnyNose.equals("true")){
                    cbRunnyNose.setChecked(true);
                }
                else{
                    cbRunnyNose.setChecked(false);
                }

                if(hasSoreThroat.equals("true")){
                    cbSoreThroat.setChecked(true);
                }
                else{
                    cbSoreThroat.setChecked(false);
                }

                if(hasShortnessOfBreath.equals("true")){
                    cbShortness.setChecked(true);
                }
                else{
                    cbShortness.setChecked(false);
                }

                if(hasDiarrhea.equals("true")){
                    cbDiarrhea.setChecked(true);
                }
                else{
                    cbDiarrhea.setChecked(false);
                }

                if(hasHeadAche.equals("true")){
                    cbHeadache.setChecked(true);
                }
                else{
                    cbHeadache.setChecked(false);
                }

                if(hasNoSmellandTaste.equals("true")){
                    cbNoSmellAndTaste.setChecked(true);
                }
                else{
                    cbNoSmellAndTaste.setChecked(false);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReStatusCheckboxActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });

        Button btnUpdateStatus = findViewById(R.id.btn_update_symptoms);
        btnUpdateStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSymptoms();
            }
        });
    }

    private void updateSymptoms() {

        //retrieving values in UserStatus database
       referenceStatus = FirebaseDatabase.getInstance().getReference("UserStatus");
       referenceStatus.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {

               User userStatusDB = snapshot.getValue(User.class);
               String status;
               String firstName = userStatusDB.f_name;
               String lastName = userStatusDB.l_name;
               String number = userStatusDB.number;

               if(cbFever.isChecked()||cbHeadache.isChecked()||cbCough.isChecked()
                       ||cbFatigue.isChecked()||cbAches.isChecked()||cbRunnyNose.isChecked() ||cbSoreThroat.isChecked()
                       ||cbShortness.isChecked()||cbDiarrhea.isChecked() ||cbNoSmellAndTaste.isChecked()){

                   status = "active case";

                   User userStatus = new User(firstName, lastName, number, status);

                   //sending the new values again to the UserStatus database
                   referenceStatus.child(userID).setValue(userStatus).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {

                           if(task.isSuccessful()){
                               Log.d("User Status:", "Successfully imported "+userStatus);
                           }
                           else{
                               Log.d("User Status:", "Something went wrong "+userStatus);
                           }
                       }
                   });
               }
               else {
                   status = "clear case";
                   User userStatus = new User(firstName, lastName, number, status);

                   //sending the new values again to the UserStatus database
                   referenceStatus.child(userID).setValue(userStatus).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {

                           if(task.isSuccessful()){
                               Toast.makeText(ReStatusCheckboxActivity.this, "Health check updated", Toast.LENGTH_LONG).show();
                           }
                           else{
                               Toast.makeText(ReStatusCheckboxActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                           }
                       }
                   });
               }

               String hasFever = String.valueOf(cbFever.isChecked());
               String hasCough = String.valueOf(cbCough.isChecked());
               String hasFatigue = String.valueOf(cbFatigue.isChecked());
               String hasAches = String.valueOf(cbAches.isChecked());
               String hasRunnyNose = String.valueOf(cbRunnyNose.isChecked());
               String hasSoreThroat = String.valueOf(cbSoreThroat.isChecked());
               String hasShortnessOfBreathing = String.valueOf(cbShortness.isChecked());
               String hasDiarrhea = String.valueOf(cbDiarrhea.isChecked());
               String hasHeadache = String.valueOf(cbHeadache.isChecked());
               String hasNoSmellAndTaste = String.valueOf(cbNoSmellAndTaste.isChecked());

               User userSymptoms = new User(hasFever, hasCough, hasFatigue, hasAches, hasRunnyNose, hasSoreThroat, hasShortnessOfBreathing, hasDiarrhea, hasHeadache, hasNoSmellAndTaste);
               reference = FirebaseDatabase.getInstance().getReference("Symptoms");
               userID = user.getUid();
               progressBar.setVisibility(View.VISIBLE);

               //entering the value to the database
               reference.child(userID).setValue(userSymptoms).addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {

                       if(task.isSuccessful()){
                           Toast.makeText(ReStatusCheckboxActivity.this, "Health check has been updated!", Toast.LENGTH_LONG).show();
                           progressBar.setVisibility(View.GONE);
                           Intent intent = new Intent(ReStatusCheckboxActivity.this, ProfileActivity.class);
                           startActivity(intent);
                           finish();
                       }
                       else {
                           Toast.makeText(ReStatusCheckboxActivity.this, "Updating failed!", Toast.LENGTH_LONG).show();
                           progressBar.setVisibility(View.GONE);
                       }
                   }
               });
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Toast.makeText(ReStatusCheckboxActivity.this, "Error", Toast.LENGTH_LONG).show();
           }
       });

    }
}