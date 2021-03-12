package com.gitlab.capstone.pellego.database;

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

    public int getqID() {
        return qID;
    }

    public int getsMID() {
        return sMID;
    }
}
