package com.example.emergencyapplication.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.emergencyapplication.EntityClass.TrustedContacts;

import java.util.List;

import androidx.room.Room;

public class TrustedContactsRepository {

    private String DB_NAME = "trustedcontacts.db";

    private TrustedContactDatabase trustedContactDatabase;
    Context context;

    //database creation
    public TrustedContactsRepository(Context context){
        this.context = context;
        trustedContactDatabase = Room.databaseBuilder(context, TrustedContactDatabase.class, DB_NAME).build();

    }


    //============== insert task starts ====================
    @SuppressLint("StaticFieldLeak")
    public void insertTask(TrustedContacts trustedContacts){

        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {

                trustedContactDatabase.trustedContactDAO().insertTask(trustedContacts);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(context, trustedContacts.contactName+" is Inserted", Toast.LENGTH_LONG).show();
            }
        }.execute();

        trustedContactDatabase.close();

    }
    //============== insert task ends ======================



    //========================get data task starts ==========================================================

    public List<TrustedContacts> getTrustedContacts(){

        List<TrustedContacts> trustedContactsList = trustedContactDatabase.trustedContactDAO().getAll();

        return trustedContactsList;
    }
    //========================= get data task ends ============================================================




    //=============== get all contact numbers only from database starts============================================
    public List<String> getTrustedContactNumbers(){

        List<String> trustedContactNumberList = trustedContactDatabase.trustedContactDAO().getContacts();

        return  trustedContactNumberList;
    }
    //=============== get all contact numbers only from database ends==============================================



    //======update task starts ================
    @SuppressLint("StaticFieldLeak")
    public void UpdateTask(final TrustedContacts trustedContacts){

        new AsyncTask<Void, Void, Void>(){

            protected Void doInBackground(Void... voids){
                trustedContactDatabase.trustedContactDAO().updateTask(trustedContacts);
                return null;
            }

        }.execute();

    }
    //======update task ends ================


    //======delete task starts ================
    @SuppressLint("StaticFieldLeak")
    public void DeleteTask(final TrustedContacts trustedContacts){

        new AsyncTask<Void, Void, Void>(){

            protected Void   doInBackground(Void... voids){
                trustedContactDatabase.trustedContactDAO().deleteTask(trustedContacts);
                return null;
            }

        }.execute();

    }
    //======delete task ends ================
}
