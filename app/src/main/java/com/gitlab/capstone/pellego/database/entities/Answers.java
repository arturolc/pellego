package com.gitlab.capstone.pellego.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Questions.class, parentColumns = "QUID", childColumns = "QUID"),
        @ForeignKey(entity = LM_Submodule.class, parentColumns = "SMID", childColumns = "SMID")})
public class Answers {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "AID")
    private int aID;

    @ColumnInfo(name = "SMID")
    private int sMID;

    @ColumnInfo(name = "QUID")
    private int qUID;

    @ColumnInfo(name = "AnswerAll")
    private String answer;

    @ColumnInfo(name = "Correct")
    private boolean correct;

    public Answers(int aID, int sMID, int qUID, String answer, boolean correct) {
        this.aID = aID;
        this.sMID = sMID;
        this.qUID = qUID;
        this.answer = answer;
        this.correct = correct;
    }

    public int getAID() {
        return aID;
    }

    public int getSMID() {
        return sMID;
    }

    public int getQUID() {
        return qUID;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isCorrect() {
        return correct;
    }
}
