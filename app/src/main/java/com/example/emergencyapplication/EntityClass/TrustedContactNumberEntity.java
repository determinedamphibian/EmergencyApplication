package com.example.emergencyapplication.EntityClass;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class TrustedContactNumberEntity {

    @PrimaryKey
    public int idNum;

    @ColumnInfo(name = "contactNum")
    public String contactNum;

    public TrustedContactNumberEntity(int idNum, String contactNum) {
        this.idNum = idNum;
        this.contactNum = contactNum;
    }
}
