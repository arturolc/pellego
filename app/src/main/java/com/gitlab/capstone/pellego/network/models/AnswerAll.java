package com.gitlab.capstone.pellego.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AnswerAll {

    @SerializedName("AID")
    @Expose
    private Integer aid;
    @SerializedName("SMID")
    @Expose
    private Integer smid;
    @SerializedName("QUID")
    @Expose
    private Integer quid;
    @SerializedName("AnswerAll")
    @Expose
    private String answer;
    @SerializedName("Correct")
    @Expose
    private Integer correct;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AnswerAll() {
    }

    /**
     * 
     * @param smid
     * @param answer
     * @param correct
     * @param quid
     * @param aid
     */
    public AnswerAll(Integer aid, Integer smid, Integer quid, String answer, Integer correct) {
        super();
        this.aid = aid;
        this.smid = smid;
        this.quid = quid;
        this.answer = answer;
        this.correct = correct;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getSmid() {
        return smid;
    }

    public void setSmid(Integer smid) {
        this.smid = smid;
    }

    public Integer getQuid() {
        return quid;
    }

    public void setQuid(Integer quid) {
        this.quid = quid;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }

}
