package com.gitlab.capstone.pellego.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/****************************************
 * Arturo Lara
 *
 * Represents a Library object
 ***************************************/

@Entity(foreignKeys = {@ForeignKey(entity = Users.class, parentColumns = "UID", childColumns = "UID"),
        @ForeignKey(entity = Books.class, parentColumns = "BID", childColumns = "BID")})
public class Library {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "LID")
    private int lID;

    @ColumnInfo(name = "UID")
    @NonNull
    private int uID;

    @ColumnInfo(name = "BID")
    @NonNull
    private int bID;

    public Library(int lID, int uID, int bID) {
        this.lID = lID;
        this.uID = uID;
        this.bID = bID;
    }

    public int getLID() {
        return lID;
    }

    public int getUID() {
        return uID;
    }

    public int getBID() {
        return bID;
    }
}
