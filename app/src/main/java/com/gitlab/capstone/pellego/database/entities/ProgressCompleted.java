package com.gitlab.capstone.pellego.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/****************************************
 * Chris Bordoy
 *
 * Represents a Progress Completion object
 * used for Learning Submodules
 ***************************************/

@Entity(foreignKeys = {@ForeignKey(entity = Users.class, parentColumns = "UID", childColumns = "UID"),
    @ForeignKey(entity = LM_Submodule.class, parentColumns = "SMID", childColumns = "SMID")})
public class ProgressCompleted {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "SCID")
    @SerializedName("SCID")
    @Expose
    private int scID;

    @ColumnInfo(name = "UID")
    @NonNull
    @SerializedName("UID")
    @Expose
    private int uID;

    @ColumnInfo(name = "SMID")
    @NonNull
    @SerializedName("SMID")
    @Expose
    private int sMID;

    public ProgressCompleted(int scID, int uID, int sMID) {
        this.scID = scID;
        this.uID = uID;
        this.sMID = sMID;
    }

    public int getScID() {
        return scID;
    }

    public int getUID() {
        return uID;
    }

    public int getSMID() {
        return sMID;
    }
}
