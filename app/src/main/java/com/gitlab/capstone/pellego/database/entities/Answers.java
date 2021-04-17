package com.gitlab.capstone.pellego.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(foreignKeys = {@ForeignKey(entity = Questions.class, parentColumns = "QUID", childColumns = "QUID"),
        @ForeignKey(entity = LM_Submodule.class, parentColumns = "SMID", childColumns = "SMID")})
public class Answers {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "AID")
    @SerializedName("AID")
    @Expose
    private int aID;

    @ColumnInfo(name = "SMID")
    @SerializedName("SMID")
    @Expose
    private int sMID;

    @ColumnInfo(name = "QUID")
    @SerializedName("QUID")
    @Expose
    private int qUID;

    @ColumnInfo(name = "Answer")
    @SerializedName("Answer")
    @Expose
    private String answer;

    @ColumnInfo(name = "Correct")
    @SerializedName("Correct")
    @Expose
    private int correct;

    public Answers(int aID, int sMID, int qUID, String answer, int correct) {
        this.aID = aID;
        this.sMID = sMID;
        this.qUID = qUID;
        this.answer = answer;
        this.correct = correct;
    }

    public int getCorrect() {
        return this.correct;
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

}
