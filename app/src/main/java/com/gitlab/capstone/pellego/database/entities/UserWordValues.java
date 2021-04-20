package com.gitlab.capstone.pellego.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity(tableName = "User_Word_Values")
public class UserWordValues {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uwID")
    @SerializedName("uwID")
    @Expose
    private int uwID;

    @ColumnInfo(name = "UID")
    @SerializedName("UID")
    @Expose
    private int uID;

    @ColumnInfo(name = "WordsRead")
    @SerializedName("WordsRead")
    @Expose
    @NonNull
    private int wordsRead;

    @ColumnInfo(name = "WPM")
    @SerializedName("WPM")
    @Expose
    @NonNull
    private int wpm;

    @ColumnInfo(name = "Recorded")
    @SerializedName("Recorded")
    @Expose
    private Date recorded;

    public UserWordValues(int uwID, int uID, int wordsRead, int wpm, Date recorded) {
        this.uwID = uwID;
        this.uID = uID;
        this.wordsRead = wordsRead;
        this.wpm = wpm;
        this.recorded = recorded;
    }

    public int getUwID() {
        return uwID;
    }

    public int getUID() {
        return uID;
    }

    public int getWordsRead() {
        return wordsRead;
    }

    public int getWpm() {
        return wpm;
    }

    public Date getRecorded() {
        return recorded;
    }

    public void setUwID(int uwID) {
        this.uwID = uwID;
    }

    public void setUID(int uID) {
        this.uID = uID;
    }

    public void setWordsRead(int wordsRead) {
        this.wordsRead = wordsRead;
    }

    public void setWpm(int wpm) {
        this.wpm = wpm;
    }

    public void setRecorded(Date recorded) {
        this.recorded = recorded;
    }
}
