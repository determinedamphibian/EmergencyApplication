package com.example.emergencyapplication.Database;

import com.example.emergencyapplication.EntityClass.TrustedContacts;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TrustedContactDAO {

    @Insert
    Long insertTask(TrustedContacts trustedContacts);

    @Update
    void updateTask(TrustedContacts trustedContacts);

    @Delete
    void deleteTask(TrustedContacts trustedContacts);

    @Query("select * from trustedcontacts order by idNum asc")
    List<TrustedContacts> getAll();

    @Query("SELECT contactNum FROM trustedcontacts")
    List<String> getContacts();
}
