package com.gitlab.capstone.pellego.database.entities;

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
    @ColumnInfo(name = "SMID")
    private int sMID;

    @ColumnInfo(name = "MID")
    private int mID;

    @ColumnInfo(name = "Name")
    @NonNull
    private String name;

    @ColumnInfo(name = "Subheader")
    private String subheader;

    public LM_Submodule(int sMID, int mID, @NonNull String name, String subheader) {
        this.sMID = sMID;
        this.mID = mID;
        this.name = name;
        this.subheader = subheader;
    }

    public int getSMID() {
        return sMID;
    }

    public int getMID() {
        return mID;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public String getSubheader() {
        return subheader;
    }
}
