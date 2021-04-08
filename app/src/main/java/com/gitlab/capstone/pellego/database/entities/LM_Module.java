package com.gitlab.capstone.pellego.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class LM_Module {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "MID")
    private int mID;

    @NonNull
    @ColumnInfo(name = "Name")
    private String name;

    @NonNull
    @ColumnInfo(name = "Subheader")
    private String subheader;

    @ColumnInfo(name = "Description")
    private String description;

    @NonNull
    @ColumnInfo(name = "Icon")
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
