package com.gitlab.capstone.pellego.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = LM_Module.class,
        parentColumns = "MID", childColumns = "MID",
        onDelete = CASCADE))
public class LM_Intro {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IID")
    @SerializedName("IID")
    @Expose
    private int iID;

    @ColumnInfo(name = "MID")
    @SerializedName("MID")
    @Expose
    private int mID;

    @ColumnInfo(name = "Header")
    @SerializedName("Header")
    @Expose
    private String header;

    @ColumnInfo(name = "Content")
    @SerializedName("Content")
    @Expose
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
