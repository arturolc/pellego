package com.gitlab.capstone.pellego.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.gitlab.capstone.pellego.database.TimestampConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity(foreignKeys = {@ForeignKey(entity = LM_Quiz.class, parentColumns = "QID", childColumns = "Quiz"),
        @ForeignKey(entity = Users.class, parentColumns = "UID", childColumns = "User")})
public class Scores {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "SID")
    @SerializedName("SID")
    @Expose
    private int sID;

    @ColumnInfo(name = "Quiz")
    @SerializedName("Quiz")
    @Expose
    private int quiz;

    @ColumnInfo(name = "User")
    @SerializedName("User")
    @Expose
    private int user;

    @ColumnInfo(name = "Date_Taken")
    @TypeConverters({TimestampConverter.class})
    @SerializedName("Date_Taken")
    @Expose
    private Date dateTaken;

    public Scores(int sID, int quiz, int user, Date dateTaken) {
        this.sID = sID;
        this.quiz = quiz;
        this.user = user;
        this.dateTaken = dateTaken;
    }

    public int getSID() {
        return sID;
    }

    public int getQuiz() {
        return quiz;
    }

    public int getUser() {
        return user;
    }

    public Date getDateTaken() {
        return dateTaken;
    }
}


