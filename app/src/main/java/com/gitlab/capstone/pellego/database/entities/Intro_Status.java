package com.gitlab.capstone.pellego.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(foreignKeys = {@ForeignKey(entity = Users.class, parentColumns = "UID", childColumns = "UID"),
    @ForeignKey(entity = LM_Intro.class, parentColumns = "IID", childColumns = "Intro")})
public class Intro_Status {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ISID")
    @SerializedName("ISID")
    @Expose
    private int iSID;

    @ColumnInfo(name = "UID")
    @SerializedName("UID")
    @Expose
    private int uID;

    @ColumnInfo(name = "Intro")
    @SerializedName("Intro")
    @Expose
    private int intro;

    @ColumnInfo(name = "Completed")
    @SerializedName("Completed")
    @Expose
    private boolean completed;

    public Intro_Status(int iSID, int uID, int intro, boolean completed) {
        this.iSID = iSID;
        this.uID = uID;
        this.intro = intro;
        this.completed = completed;
    }

    public int getISID() {
        return iSID;
    }

    public int getUID() {
        return uID;
    }

    public int getIntro() {
        return intro;
    }

    public boolean isCompleted() {
        return completed;
    }
}

