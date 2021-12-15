package com.example.emergencyapplication.CovidWatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emergencyapplication.MedicalActivity;
import com.example.emergencyapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InfoDialogActivity extends AppCompatActivity {

    private static final int REQUEST_CALL = 1;
    TextView tv_number;
    String name, number, status;
    RadioButton rbtn_active, rbtn_clear, rbtn_death;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_dialog);


        Bundle bundle = getIntent().getExtras();
         name = bundle.getString("name");
         number = bundle.getString("number");
         status = bundle.getString("status");
         String user_id = bundle.getString("uid");

        Log.d("NNSU", name+" "+number+""+status+" "+user_id);

        TextView tv_fullName = findViewById(R.id.tv_fullName);
        tv_fullName.setText(name);

        tv_number = findViewById(R.id.tv_number);
        tv_number.setText(number);

        if(status.equals("potential active case")){
            RadioButton rbtn_active = findViewById(R.id.rbtn_active);
            rbtn_active.setChecked(true);
        }
        else if(status.equals("clear case")){
            RadioButton rbtn_clear = findViewById(R.id.rbtn_clear);
            rbtn_clear.setChecked(true);
        }
        else{
            RadioButton rbtn_death = findViewById(R.id.rbtn_death);
            rbtn_death.setChecked(true);
        }

        Button btn_dial = findViewById(R.id.btn_dial);
        btn_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makePhoneCall(tv_number);
            }
        });

        ImageView bt_back = findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateUserStatus();
            }
        });

    }

    private void updateUserStatus() {

        String newStatus;
        rbtn_active = findViewById(R.id.rbtn_active);
        rbtn_clear = findViewById(R.id.rbtn_clear);
        rbtn_death = findViewById(R.id.rbtn_death);

        Bundle userBundle = getIntent().getExtras();
        String user_id = userBundle.getString("uid");

        if(rbtn_active.isChecked()){
            newStatus = "potential active case";
        }
        else if(rbtn_clear.isChecked()){
            newStatus = "clear case";
        }
        else if(rbtn_death.isChecked()){
            newStatus = "death case";
        }
        else{
            newStatus = "null";
        }

        Log.d("TEST4", newStatus+" "+user_id);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UserStatus");
        reference.child(user_id).child("status").setValue(newStatus).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(InfoDialogActivity.this, "Resident updated successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(InfoDialogActivity.this, ViewUserDataActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(InfoDialogActivity.this, "Error! Updating Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if(requestCode == REQUEST_CALL){

            if(ActivityCompat.checkSelfPermission(InfoDialogActivity.this, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED)  {
                Intent intent = new Intent(Intent.ACTION_CALL);
                String number = tv_number.getText().toString();
                intent.setData(Uri.parse("tel:"+number));
                startActivity(intent);
                finish();
            }
        }
    }

    public void makePhoneCall(TextView tv_number)
    {

        String number =  tv_number.getText().toString();

        if(ActivityCompat.checkSelfPermission(InfoDialogActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(InfoDialogActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }
        else{
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+number));
            startActivity(intent);
            finish();
        }
    }
}