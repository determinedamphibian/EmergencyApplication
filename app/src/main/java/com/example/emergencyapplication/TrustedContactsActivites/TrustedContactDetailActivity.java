package com.example.emergencyapplication.TrustedContactsActivites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.emergencyapplication.EntityClass.TrustedContacts;
import com.example.emergencyapplication.Database.TrustedContactsRepository;
import com.example.emergencyapplication.R;

public class TrustedContactDetailActivity extends AppCompatActivity {

    EditText edt_idNum, edt_trustedContact_name, edt_trustedContact_num;

    Button btn_update, btn_delete;

    int sidNum;

    String strustedContact_name ="", strustedContactNum = "", sgender= "";

    String sidNum_to_update = "", strustedContact_name_to_update = "", strustedContactNum_to_update = "", sgender_to_update ="";

    String sidNum_to_delete = "", strustedContact_name_to_delete = "", strustedContactNum_to_delete = "", sgender_to_delete ="";

    RadioButton rbtn_male, rbtn_female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trusted_contact_detail);


        //==========find view id starts===============================================================
        edt_idNum = (EditText)findViewById(R.id.edit_idNum);
        edt_trustedContact_name = (EditText)findViewById(R.id.edit_trustedContact_name);
        edt_trustedContact_num = (EditText)findViewById(R.id.edit_trustedContact_no);
        rbtn_male =  (RadioButton) findViewById(R.id.rbtn_male);
        rbtn_female =  (RadioButton) findViewById(R.id.rbtn_female);
        btn_update = (Button)findViewById(R.id.btn_Update);
        btn_delete = (Button)findViewById(R.id.btn_Delete);
        // ==========find view id ends================================================================



        //===========get value from custom adapter starts=============================================
        Bundle data = getIntent().getExtras();
        if(data!=null){
            sidNum = data.getInt("idNum");
            strustedContact_name = data.getString("trustedContact_name");
            strustedContactNum = data.getString("trustedContact_num");
            sgender = data.getString("gender");
        }
        // ==========get value from custom adapter ends==============================================



        //===========set value to UI starts==========================================================
        edt_idNum.setText(sidNum + "");
        edt_trustedContact_name.setText(strustedContact_name+"");
        edt_trustedContact_num.setText(strustedContactNum+"");

        if(sgender.trim().toLowerCase().equalsIgnoreCase("male")){
            rbtn_male.setChecked(true);
        }else if(sgender.trim().toLowerCase().equalsIgnoreCase("female")){
            rbtn_female.setChecked(true);
        }
        //===========set value to UI ends=============================================================




        //=============Button Update Listener Starts===================================================
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edt_trustedContact_name.getText().toString().isEmpty()
                        || edt_trustedContact_num.getText().toString().isEmpty()){

                    Toast.makeText(getApplicationContext(), "Fill detail", Toast.LENGTH_LONG).show();
                }else{
                    sidNum_to_update = edt_idNum.getText().toString().trim();
                    strustedContact_name_to_update = edt_trustedContact_name.getText().toString().trim();
                    strustedContactNum_to_update = edt_trustedContact_num.getText().toString().trim();

                    if(rbtn_male.isChecked()){
                        sgender_to_update = "Male";
                    }else if (rbtn_female.isChecked()){
                        sgender_to_update ="Female";
                    }

                    TrustedContactsRepository trustedContactsRepository = new TrustedContactsRepository(getApplicationContext());
                    TrustedContacts trustedContacts = new TrustedContacts(Integer.parseInt(sidNum_to_update),strustedContact_name_to_update, strustedContactNum_to_update, sgender_to_update);
                    trustedContactsRepository.UpdateTask(trustedContacts);

                    Toast.makeText(getApplicationContext(), "Values Updated", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
        //=============Button Update Listener Ends====================================================



        //=============Button Delete Listener Ends========================================================
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sidNum_to_delete = edt_idNum.getText().toString().trim();
                strustedContact_name_to_delete = edt_trustedContact_name.getText().toString().trim();
                strustedContactNum_to_delete = edt_trustedContact_num.getText().toString().trim();

                if(rbtn_male.isChecked()){

                    sgender_to_delete = "Male";

                }else if(rbtn_female.isChecked()){

                    sgender_to_delete = "Female";

                }

                TrustedContacts trustedContacts_to_delete = new TrustedContacts(Integer.parseInt(sidNum_to_delete),strustedContact_name_to_delete,strustedContactNum_to_delete,sgender_to_delete);
                generate_delete_dialog(trustedContacts_to_delete);
            }
        });
        //=============Button Delete Listener Ends=========================================================
    }




    //================= generate alert dialog delete starts==================

    public void generate_delete_dialog(TrustedContacts trustedContacts){

        final TrustedContacts student_about_to_delete = trustedContacts;

        AlertDialog.Builder builder = new AlertDialog.Builder(TrustedContactDetailActivity.this); //do not write getApplicationContext
        builder.setTitle("Warning");
        builder.setMessage("Are you sure you want to delete ?\n"+"ID Num ="+student_about_to_delete.idNum+"\n"+"Name="+student_about_to_delete.contactName);
        builder.setIcon(android.R.drawable.ic_delete);

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //============delete code=======================
                TrustedContactsRepository studentRepository = new TrustedContactsRepository(getApplicationContext());
                studentRepository.DeleteTask(student_about_to_delete);
                Toast.makeText(TrustedContactDetailActivity.this,"Contact deleted", Toast.LENGTH_LONG).show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog deleteDialog = builder.create();
        deleteDialog.show();
    }
    //================= generate alert dialog delete end=====================

}