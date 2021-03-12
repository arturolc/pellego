package com.gitlab.capstone.pellego.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class LM_Module {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "MID")
    private int mID;

    @NonNull
    @ColumnInfo(name = "Name")
    private String name;

    @NonNull
    @ColumnInfo(name = "description")
    private String description;

    public LM_Module(int mID, @NonNull String name, @NonNull String description) {
        this.mID = mID;
        this.name = name;
        this.description = description;
    }

    public int getmID() {
        return mID;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getDescription() {
        return description;
    }
}
