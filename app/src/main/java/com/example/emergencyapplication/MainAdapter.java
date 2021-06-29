package com.example.emergencyapplication;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.emergencyapplication.EntityClass.TrustedContacts;
import com.example.emergencyapplication.SideDockContents.AboutUsSideDock;
import com.example.emergencyapplication.SideDockContents.GovernmentAgenciesSideDock;
import com.example.emergencyapplication.SideDockContents.SirenAndFlickerLightSideDock;
import com.example.emergencyapplication.SideDockContents.TrustedContactsSideDock;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    //Initialize variable activity and arrayList located in MainDashboardActivity
    Activity activity;
    ArrayList<String > arrayList;


    //Constructor
    public MainAdapter(Activity activity, ArrayList<String> arrayList){
        this.activity = activity;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Initialize View located in item_drawer_main
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer_main,parent, false);

        //Return viewHolder
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //set text on textView
        holder.textView.setText(arrayList.get(position));

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //when an item gets clicked it redirects to a signed position/activity class
                int position = holder.getAdapterPosition();

                //check position
                switch (position){
                    case 0:
                        activity.startActivity(new Intent(activity, MainDashboardActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case 1:
                        activity.startActivity(new Intent(activity, TrustedContactsSideDock.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case 2:
                        activity.startActivity(new Intent(activity, GovernmentAgenciesSideDock.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case 3:
                        activity.startActivity(new Intent(activity, SirenAndFlickerLightSideDock.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case 4:
                        activity.startActivity(new Intent(activity, AboutUsSideDock.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //Initialize variable textView located in item_drawer_main
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Assign variable
            textView = itemView.findViewById(R.id.text_view);
        }
    }
}