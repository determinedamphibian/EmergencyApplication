package com.example.emergencyapplication.CovidWatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emergencyapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView btn_menu;
    RecyclerView recyclerView;
    private TextView tv_statusProfile;
    private Button btn_logout, btn_update, btn_update_stats;
    private FirebaseUser user;
    private DatabaseReference reference, referenceStatus;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        drawerLayout = findViewById(R.id.drawer_covid_layout);
        recyclerView = findViewById(R.id.covid_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CovidAdapter(this, CovidDashboard.arrayList));

        btn_menu = findViewById(R.id.bt_menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView tv_f_name, tv_l_name, tv_email, tv_address, tv_number, tv_username;

        tv_f_name = findViewById(R.id.tv_re_firstName);
        tv_l_name = findViewById(R.id.tv_re_last_name);
        tv_email = findViewById(R.id.tv_re_email);
        tv_address = findViewById(R.id.tv_re_address);
        tv_number = findViewById(R.id.tv_re_number);
        tv_username = findViewById(R.id.tv_re_username);

        //for retrieving from Users database
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userProfile = snapshot.getValue(User.class);

//                if (userProfile != null){
                    String firstName = userProfile.f_name;
                    String lastName = userProfile.l_name;
                    String userName = userProfile.username;
                    String email = userProfile.email;
                    String address = userProfile.address;
                    String number = userProfile.number;

                    tv_f_name.setText(firstName);
                    tv_l_name.setText(lastName);
                    tv_email.setText(email);
                    tv_username.setText(userName);
                    tv_address.setText(address);
                    tv_number.setText(number);


//                }
//                else{
//                    Toast.makeText(ProfileActivity.this, "Your profile is empty",Toast.LENGTH_LONG).show();
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(ProfileActivity.this, "Error", Toast.LENGTH_LONG).show();

            }
        });

        referenceStatus = FirebaseDatabase.getInstance().getReference("UserStatus");
        referenceStatus.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userStatus = snapshot.getValue(User.class);
                String profileStatus = userStatus.status;
                tv_statusProfile = findViewById(R.id.tv_statusProfile);
                tv_statusProfile.setText(profileStatus);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(ProfileActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });


        btn_update = findViewById(R.id.btn_update_profile);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ReProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_update_stats = findViewById(R.id.btn_update_status);
        btn_update_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ReStatusCheckboxActivity.class);
                startActivity(intent);
            }
        });

        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent( ProfileActivity.this, CovidWatcherActivity.class);
                startActivity(intent);
                Toast.makeText(ProfileActivity.this, "Logout successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}