package com.gitlab.capstone.pellego.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(foreignKeys = @ForeignKey(entity = Users.class, parentColumns = "UID", childColumns = "User"))
public class Average_WPM_History {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "WPMID")
    private int wpmID;

    @ColumnInfo(name = "User")
    private int user;

    @ColumnInfo(name = "Day")
    @TypeConverters({TimestampConverter.class})
    private Date day;

    public Average_WPM_History(int wpmID, int user, Date day) {
        this.wpmID = wpmID;
        this.user = user;
        this.day = day;
    }

    public int getWpmID() {
        return wpmID;
    }

    public int getUser() {
        return user;
    }

    public Date getDay() {
        return day;
    }
}
