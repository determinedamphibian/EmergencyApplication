package com.example.emergencyapplication.TrustedContactsActivites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.emergencyapplication.EntityClass.TrustedContacts;
import com.example.emergencyapplication.Database.TrustedContactsRepository;
import com.example.emergencyapplication.R;

public class InsertActivity extends AppCompatActivity {

    EditText  edt_trustedContact_name, edt_trustedContact_no;
    Button btn_submit;
    RadioButton rbtn_male, rbtn_female;
    String  s_trustedContact_name, s_trustedContact_no, s_gender = "Male";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //=============== Variables To ID References======================
        edt_trustedContact_name = (EditText) findViewById(R.id.edit_trustedContact_name);
        edt_trustedContact_no = (EditText) findViewById(R.id.edit_trustedContact_no);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        rbtn_male = (RadioButton) findViewById(R.id.rbtn_male);
        rbtn_female= (RadioButton) findViewById(R.id.rbtn_female);
        //================================================================

        //====================Getting Data from User's Input===================
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edt_trustedContact_name.getText().toString().isEmpty()
                        ||edt_trustedContact_no.getText().toString().isEmpty()){

                    Toast.makeText(getApplicationContext(),"Fill Detail", Toast.LENGTH_SHORT).show();
                }else
                    {
                        //getting values from edit text
                    s_trustedContact_name = edt_trustedContact_name.getText().toString().trim();
                    s_trustedContact_no = edt_trustedContact_no.getText().toString().trim();


                    //=================== TOAST roll no, name, contact no, gender ===================

                    if(rbtn_male.isChecked()){

                        s_gender = "Male";

                    }else if(rbtn_female.isChecked()){

                        s_gender = "Female";
                    }

                    TrustedContactsRepository trustedContactsRepository = new TrustedContactsRepository(getApplicationContext());
                    TrustedContacts trustedContacts = new TrustedContacts(0,s_trustedContact_name,s_trustedContact_no, s_gender);
                    trustedContactsRepository.insertTask(trustedContacts);

                    edt_trustedContact_name.setText("");
                    edt_trustedContact_no.setText("");

                }
            }
        });
    }
}