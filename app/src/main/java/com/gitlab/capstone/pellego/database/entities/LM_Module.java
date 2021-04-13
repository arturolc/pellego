package com.gitlab.capstone.pellego.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class LM_Module {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "MID")
    @SerializedName("MID")
    @Expose
    private int mID;

    @NonNull
    @ColumnInfo(name = "Name")
    @SerializedName("Name")
    @Expose
    private String name;

    @NonNull
    @ColumnInfo(name = "Subheader")
    @SerializedName("Subheader")
    @Expose
    private String subheader;

    @ColumnInfo(name = "Description")
    @SerializedName("Description")
    @Expose
    private String description;

    @NonNull
    @ColumnInfo(name = "Icon")
    @SerializedName("Icon")
    @Expose
    private String icon;

    public LM_Module(int mID, @NonNull String name, @NonNull String subheader, String description, @NonNull String icon) {
        this.mID = mID;
        this.name = name;
        this.subheader = subheader;
        this.description = description;
        this.icon = icon;
    }

    public int getMID() {
        return mID;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getSubheader() {
        return subheader;
    }

    public String getDescription() {
        return description;
    }

    @NonNull
    public String getIcon() {
        return icon;
    }
}
