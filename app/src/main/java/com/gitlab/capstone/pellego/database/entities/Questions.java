package com.gitlab.capstone.pellego.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = LM_Quiz.class, parentColumns = "QID", childColumns = "Quiz_ID"))
public class Questions {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "QUID")
    private int qUID;

    @ColumnInfo(name = "SMID")
    private int sMID;

    @ColumnInfo(name = "Question")
    private String question;

    public Questions(int qUID, int sMID, String question) {
        this.qUID = qUID;
        this.sMID = sMID;
        this.question = question;
    }

    public int getGUID() {
        return qUID;
    }

    public int getSMID() {
        return sMID;
    }

    public String getQuestion() {
        return question;
    }
}
