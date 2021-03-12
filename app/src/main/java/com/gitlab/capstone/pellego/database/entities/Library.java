package com.gitlab.capstone.pellego.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Users.class, parentColumns = "UID", childColumns = "UID"),
        @ForeignKey(entity = Books.class, parentColumns = "BID", childColumns = "BID")})
public class Library {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "LID")
    private int lID;

    @ColumnInfo(name = "UID")
    @NonNull
    private int uID;

    @ColumnInfo
    @NonNull
    private int BID;

    public Library(int lID, int uID, int BID) {
        this.lID = lID;
        this.uID = uID;
        this.BID = BID;
    }

    public int getlID() {
        return lID;
    }

    public int getuID() {
        return uID;
    }

    public int getBID() {
        return BID;
    }
}
