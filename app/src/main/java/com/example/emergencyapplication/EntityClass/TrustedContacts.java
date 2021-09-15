package com.example.emergencyapplication.EntityClass;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TrustedContacts {

    @PrimaryKey
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


    public int getIdNum() {
        return idNum;
    }

    public void setIdNum(int idNum) {
        this.idNum = idNum;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

