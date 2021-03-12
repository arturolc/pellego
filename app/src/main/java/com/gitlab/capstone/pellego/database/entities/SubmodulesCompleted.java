package com.gitlab.capstone.pellego.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Users.class, parentColumns = "UID", childColumns = "UID"),
    @ForeignKey(entity = LM_Submodule.class, parentColumns = "SMID", childColumns = "SMID")})
public class SubmodulesCompleted {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "SCID")
    private int scID;

    @ColumnInfo(name = "UID")
    @NonNull
    private int uID;

    @ColumnInfo
    @NonNull
    private int sMID;

    public SubmodulesCompleted(int scID, int uID, int sMID) {
        this.scID = scID;
        this.uID = uID;
        this.sMID = sMID;
    }

    public int getScID() {
        return scID;
    }

    public int getuID() {
        return uID;
    }

    public int getsMID() {
        return sMID;
    }
}
