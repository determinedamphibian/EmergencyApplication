package com.example.emergencyapplication.CovidWatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.emergencyapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewUserDataActivity extends AppCompatActivity {

    ImageView bt_back;
    ArrayList<String> nameList = new ArrayList<>();
    ArrayList<String> numberList = new ArrayList<>();
    ArrayList<String> statusList = new ArrayList<>();
    ArrayList<String> uidList = new ArrayList<>();
    ListView listView;
    RadioGroup radioGroup;
    RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_data);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_item, nameList);

        bt_back = findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewUserDataActivity.this, CovidDashboard.class);
                startActivity(intent);
                finish();
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("UserStatus");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User userInfo = dataSnapshot.getValue(User.class);
                    String name = userInfo.f_name+" "+userInfo.l_name;
                    String number = userInfo.number;
                    String status = userInfo.status;
                    String user_id = userInfo.user_id;

                    nameList.add(name);
                    numberList.add(number);
                    statusList.add(status);
                    uidList.add(user_id);


                }

                Log.d("Full Name", String.valueOf(nameList));
                Log.d("Number", String.valueOf(numberList));
                Log.d("Status", String.valueOf(statusList));
                Log.d("User_ID", String.valueOf(uidList));

                listView = findViewById(R.id.listViewID);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Log.d("Position", String.valueOf(position));
//                        AlertDialog.Builder builder = new AlertDialog.Builder(ViewUserDataActivity.this);
//                        builder.setTitle(""+parent.getItemAtPosition(position));
//                        builder.setMessage("Health Status: " +statusList.get(position));
//                        builder.setPositiveButton("Ok", null);
//                        builder.show();

                        String name = nameList.get(position);
                        String number = numberList.get(position);
                        String status  = statusList.get(position);
                        String uid = uidList.get(position);
//
//                        Log.d("NNS", name+" "+number+" "+status);
//
//                        showCustomDialog(name, number, status);

                        Intent intent = new Intent(ViewUserDataActivity.this, InfoDialogActivity.class);
                        intent.putExtra("name", name);
                        intent.putExtra("number", number);
                        intent.putExtra("status", status);
                        intent.putExtra("uid", uid);
                        startActivity(intent);
                    }

                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



//        DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference();
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }

}