package com.gitlab.capstone.pellego.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(foreignKeys = @ForeignKey(entity = Users.class, parentColumns = "UID", childColumns = "UID"))
public class User_Analytics {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "AID")
    private int aID;

    @ColumnInfo(name = "UID")
    @NonNull
    private int uID;

    @ColumnInfo(name = "WordsReadToDate")
    private int wordsReadToDate;

    @ColumnInfo(name = "Joined")
    private Date joined;

    public User_Analytics(int aID, int uID, int wordsReadToDate, Date joined) {
        this.aID = aID;
        this.uID = uID;
        this.wordsReadToDate = wordsReadToDate;
        this.joined = joined;
    }

    public int getaID() {
        return aID;
    }

    public int getuID() {
        return uID;
    }

    public int getWordsReadToDate() {
        return wordsReadToDate;
    }

    public Date getJoined() {
        return joined;
    }
}
