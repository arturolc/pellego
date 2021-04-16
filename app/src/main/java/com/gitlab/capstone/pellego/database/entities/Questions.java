package com.gitlab.capstone.pellego.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(foreignKeys = @ForeignKey(entity = LM_Submodule.class, parentColumns = "SMID", childColumns = "SMID"))
public class Questions {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "QUID")
    @SerializedName("QUID")
    @Expose
    private int qUID;

    @ColumnInfo(name = "SMID")
    @SerializedName("SMID")
    @Expose
    private int sMID;

    @ColumnInfo(name = "Question")
    @SerializedName("Question")
    @Expose
    private String question;

    public Questions(int qUID, int sMID, String question) {
        this.qUID = qUID;
        this.sMID = sMID;
        this.question = question;
    }

    public int getQUID() {
        return qUID;
    }

    public int getSMID() {
        return sMID;
    }

    public String getQuestion() {
        return question;
    }
}
