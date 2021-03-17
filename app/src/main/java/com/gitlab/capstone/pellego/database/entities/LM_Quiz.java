package com.gitlab.capstone.pellego.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = LM_Submodule.class, parentColumns = "SMID", childColumns = "SMID"))
public class LM_Quiz {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "QID")
    private int qID;

    @ColumnInfo(name = "SMID")
    private int sMID;

    public LM_Quiz(int qID, int sMID) {
        this.qID = qID;
        this.sMID = sMID;
    }

    public int getQID() {
        return qID;
    }

    public int getSMID() {
        return sMID;
    }
}
