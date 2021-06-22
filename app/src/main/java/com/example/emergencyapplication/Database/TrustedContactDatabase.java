package com.example.emergencyapplication.Database;

import com.example.emergencyapplication.EntityClass.TrustedContacts;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TrustedContacts.class}, version =  1, exportSchema = false)
public abstract class TrustedContactDatabase extends RoomDatabase {

    public abstract TrustedContactDAO trustedContactDAO();
}
