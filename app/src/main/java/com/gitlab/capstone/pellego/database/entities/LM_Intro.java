package com.gitlab.capstone.pellego.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = LM_Module.class,
        parentColumns = "MID", childColumns = "MID",
        onDelete = CASCADE))
public class LM_Intro {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IID")
    private int iID;

    @ColumnInfo(name = "MID")
    private int mID;

    @ColumnInfo(name = "Header")
    private String header;

    @ColumnInfo(name = "Content")
    private String content;

    public LM_Intro(int iID, int mID, String header, String content) {
        this.iID = iID;
        this.mID = mID;
        this.header = header;
        this.content = content;
    }

    public int getIID() {
        return iID;
    }

    public int getMID() {
        return mID;
    }

    public String getHeader() {
        return header;
    }

    public String getContent() {
        return content;
    }
}
