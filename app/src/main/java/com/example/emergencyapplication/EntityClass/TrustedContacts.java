package com.example.emergencyapplication.EntityClass;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TrustedContacts {

    @PrimaryKey(autoGenerate = true)
    public int idNum;

    @ColumnInfo(name = "contactName")
    public String contactName;

    @ColumnInfo(name = "contactNum")
    public String contactNum;

    @ColumnInfo(name = "gender")
    public String gender;


    public TrustedContacts(int idNum, String contactName, String contactNum, String gender) {
        this.idNum = idNum;
        this.contactName = contactName;
        this.contactNum = contactNum;
        this.gender = gender;

    }

}

