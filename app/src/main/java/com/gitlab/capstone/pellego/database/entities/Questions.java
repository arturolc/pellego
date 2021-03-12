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

    @ColumnInfo(name = "Quiz_ID")
    private int quiz_ID;

    @ColumnInfo(name = "Question")
    private String question;

    public Questions(int qUID, int quiz_ID, String question) {
        this.qUID = qUID;
        this.quiz_ID = quiz_ID;
        this.question = question;
    }

    public int getqUID() {
        return qUID;
    }

    public int getQuiz_ID() {
        return quiz_ID;
    }

    public String getQuestion() {
        return question;
    }
}
