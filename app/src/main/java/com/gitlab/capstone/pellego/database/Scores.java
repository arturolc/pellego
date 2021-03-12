package com.gitlab.capstone.pellego.database;

import androidx.navigation.dynamicfeatures.Constants;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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


