package com.gitlab.capstone.pellego.database.entities;

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
    @ColumnInfo(name = "Text")
    private String text;

    public LM_Module(int mID, @NonNull String name, @NonNull String text) {
        this.mID = mID;
        this.name = name;
        this.text = text;
    }

    public int getMID() {
        return mID;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getText() {
        return text;
    }
}
