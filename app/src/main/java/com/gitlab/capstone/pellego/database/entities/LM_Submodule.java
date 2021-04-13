package com.gitlab.capstone.pellego.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = LM_Module.class,
    parentColumns = "MID", childColumns = "MID", onDelete = CASCADE))
public class LM_Submodule {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "SMID")
    @SerializedName("SMID")
    @Expose
    private int sMID;

    @ColumnInfo(name = "MID")
    @SerializedName("MID")
    @Expose
    private int mID;

    @ColumnInfo(name = "Name")
    @NonNull
    @SerializedName("Name")
    @Expose
    private String name;

    @ColumnInfo(name = "Subheader")
    @SerializedName("Subheader")
    @Expose
    private String subheader;

    @ColumnInfo(name = "Text")
    @SerializedName("Text")
    @Expose
    private String text;


    public LM_Submodule(int sMID, int mID, @NonNull String name, String subheader, String text) {
        this.sMID = sMID;
        this.mID = mID;
        this.name = name;
        this.subheader = subheader;
        this.text = text;
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

    public String getText() {
        return text;
    }
}
