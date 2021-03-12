package com.gitlab.capstone.pellego.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = LM_Module.class,
    parentColumns = "MID", childColumns = "MID", onDelete = CASCADE))
public class LM_Submodule {
    @PrimaryKey(autoGenerate = true)
    private int sMID;

    @ColumnInfo(name = "MID")
    private int mID;

    @ColumnInfo(name = "Name")
    @NonNull
    private String name;

    @ColumnInfo(name = "Text")
    private String text;

    public LM_Submodule(int sMID, int mID, @NonNull String name, String text) {
        this.sMID = sMID;
        this.mID = mID;
        this.name = name;
        this.text = text;
    }

    public int getsMID() {
        return sMID;
    }

    public int getmID() {
        return mID;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }
}
