package com.gitlab.capstone.pellego.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(foreignKeys = {
    @ForeignKey(entity = Users.class, parentColumns = "UID", childColumns = "UID"),
    @ForeignKey(entity = LM_Module.class, parentColumns = "MID", childColumns = "MID"),
    @ForeignKey(entity = LM_Submodule.class, parentColumns = "SMID", childColumns = "SMID")
    }, primaryKeys = {"UID", "MID", "SMID"})
public class ProgressCompleted {
    @ColumnInfo(name = "UID")
    @NonNull
    @SerializedName("UID")
    @Expose
    private int uID;

    @ColumnInfo(name = "MID")
    @NonNull
    @SerializedName("MID")
    @Expose
    private int MID;

    @ColumnInfo(name = "SMID")
    @NonNull
    @SerializedName("SMID")
    @Expose
    private int sMID;

    public ProgressCompleted(int uID, int MID, int sMID) {
        this.uID = uID;
        this.MID = MID;
        this.sMID = sMID;
    }

    public int getUID() {
        return uID;
    }

    public int getMID() {
        return MID;
    }

    public int getSMID() {
        return sMID;
    }
}
