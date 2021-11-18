package com.example.emergencyapplication.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emergencyapplication.EntityClass.TrustedContacts;
import com.example.emergencyapplication.R;
import com.example.emergencyapplication.SideDockContents.GovernmentAgenciesSideDock;
import com.example.emergencyapplication.TrustedContactsActivites.TrustedContactDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{

    private ArrayList<TrustedContacts> dataset;
    Context context;
    List<String> contactNumbersList = new ArrayList<>();
    private static final int REQUEST_CALL = 1;



    //=====================my Class view holder starts===========================
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_idNum, tv_trustedContacts_name, tv_trustedContact_no;
        ImageView img_gender, img_call;
        Button btn_title;
        LinearLayout ll_card_trusted_contacts;


        public MyViewHolder(View itemView){

            super(itemView);
            this.tv_idNum = (TextView) itemView.findViewById(R.id.tv_idNum);
            this.tv_trustedContacts_name = (TextView) itemView.findViewById(R.id.tv_trustedContact_name);
            this.tv_trustedContact_no = (TextView) itemView.findViewById(R.id.tv_trustedContact_num);
            this.img_gender = (ImageView) itemView.findViewById(R.id.img_gender);
            this.btn_title = (Button) itemView.findViewById(R.id.btn_title);
            this.img_call = (ImageView) itemView.findViewById(R.id.img_call);
            this.ll_card_trusted_contacts = (LinearLayout) itemView.findViewById(R.id.ll_card_trusted_contacts);

        }

    }
    //=====================my view holder ends===========================

    //=====================my Custom Adapter===========================
    public CustomAdapter(ArrayList<TrustedContacts> dataset, Context context){
        this.dataset = dataset;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_trsuted_contacts,parent,false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        TextView tv_trustedContact_name = holder.tv_trustedContacts_name;
        TextView tv_trustedContact_no = holder.tv_trustedContact_no;
        ImageView img_gender = holder.img_gender;
        ImageView img_call = holder.img_call;
        Button btn_title = holder.btn_title;
        LinearLayout ll_card_trusted_contacts = holder.ll_card_trusted_contacts;


        tv_trustedContact_name.setText(dataset.get(position).contactName+"");
        tv_trustedContact_no.setText(dataset.get(position).contactNum+"");


        //============= logic for color gender=============
        if(dataset.get(position).gender.equalsIgnoreCase("Male")){

            img_gender.setImageResource(R.drawable.male);
        }
        else if(dataset.get(position).gender.equalsIgnoreCase("Female")){

            img_gender.setImageResource(R.drawable.female);
        }

        //============= logic to set title =============
        btn_title.setText(dataset.get(position).contactName.toUpperCase().charAt(0)+"");

        //===========logic to getting the list of contact number============================
        getContactNumberList(position);
        //========== logic to getting the list of contact number ends ====================



        //============ random color Starts==========================
        Random random = new Random();
        int red = random.nextInt(255);
        int green = random.nextInt(255);
        int blue = random.nextInt(255);
        btn_title.setBackgroundColor(Color.rgb(red,green,blue));
        //============ random color Ends==============================

        img_call.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                }else{

                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+dataset.get(position).contactNum));
                    context.startActivity(intent);
                }

            }
        });

        //============== clickable Column of data Starts ==================
        ll_card_trusted_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idNum = dataset.get(position).idNum;
                String trustedContact_name = dataset.get(position).contactName;
                String trustedContact_num = dataset.get(position).contactNum;
                String gender = dataset.get(position).gender;

                Toast.makeText(context, idNum+"\n"+trustedContact_name+"\n"+trustedContact_num+"\n"+gender,
                        Toast.LENGTH_LONG).show();

                Intent intent = new Intent(context, TrustedContactDetailActivity.class);

                intent.putExtra("idNum",idNum);
                intent.putExtra("trustedContact_name",trustedContact_name);
                intent.putExtra("trustedContact_num",trustedContact_num);
                intent.putExtra("gender",gender);

                context.startActivity(intent);
            }
        });
        //============== clickable Column of data End==================
    }
    //=====================End of byViewHolder Method============================

    @Override
    public int getItemCount() {

        return dataset.size();

    }

    public void getContactNumberList(int position) {
        contactNumbersList.add(dataset.get(position).contactNum);
    }



}
