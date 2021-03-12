package com.gitlab.capstone.pellego.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Users.class, parentColumns = "UID", childColumns = "UID"),
    @ForeignKey(entity = LM_Intro.class, parentColumns = "IID", childColumns = "Intro")})
public class Intro_Status {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ISID")
    private int iSID;

    @ColumnInfo(name = "UID")
    private int uID;

    @ColumnInfo(name = "Intro")
    private int intro;

    @ColumnInfo(name = "Completed")
    private boolean completed;

    public Intro_Status(int iSID, int uID, int intro, boolean completed) {
        this.iSID = iSID;
        this.uID = uID;
        this.intro = intro;
        this.completed = completed;
    }

    public int getiSID() {
        return iSID;
    }

    public int getuID() {
        return uID;
    }

    public int getIntro() {
        return intro;
    }

    public boolean isCompleted() {
        return completed;
    }
}

