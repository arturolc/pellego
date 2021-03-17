package com.gitlab.capstone.pellego.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;



@Entity(tableName = "Users",
        indices = {@Index(value = {"Email"},
        unique = true)})
public class Users {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "UID")
    private int uID;

    @NonNull
    @ColumnInfo(name = "Name")
    private String name;

    @NonNull
    @ColumnInfo(name = "Email")
    private String email;

    public Users(int uID, @NonNull String name, @NonNull String email) {
        this.uID = uID;
        this.name = name;
        this.email = email;
    }

    public int getUID() {
        return uID;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getEmail() {
        return email;
    }
}
