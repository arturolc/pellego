package com.gitlab.capstone.pellego.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.gitlab.capstone.pellego.database.TimestampConverter;

import java.util.Date;

@Entity(foreignKeys = {@ForeignKey(entity = LM_Quiz.class, parentColumns = "QID", childColumns = "Quiz"),
        @ForeignKey(entity = Users.class, parentColumns = "UID", childColumns = "User")})
public class Scores {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "SID")
    private int sID;

    @ColumnInfo(name = "Quiz")
    private int quiz;

    @ColumnInfo(name = "User")
    private int user;

    @ColumnInfo(name = "Date_Taken")
    @TypeConverters({TimestampConverter.class})
    private Date dateTaken;
}


