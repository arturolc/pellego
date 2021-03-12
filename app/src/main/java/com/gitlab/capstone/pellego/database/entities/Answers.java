package com.gitlab.capstone.pellego.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Questions.class, parentColumns = "QUID", childColumns = "Question"))
public class Answers {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "AID")
    private int aID;

    @ColumnInfo(name = "Question")
    private int question;

    @ColumnInfo(name = "Correct")
    private boolean correct;

    public Answers(int aID, int question, boolean correct) {
        this.aID = aID;
        this.question = question;
        this.correct = correct;
    }

    public int getaID() {
        return aID;
    }

    public int getQuestion() {
        return question;
    }

    public boolean isCorrect() {
        return correct;
    }
}
