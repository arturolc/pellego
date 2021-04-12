package com.gitlab.capstone.pellego.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Question {

    @SerializedName("QUID")
    @Expose
    private Integer quid;
    @SerializedName("SMID")
    @Expose
    private Integer smid;
    @SerializedName("Question")
    @Expose
    private String question;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Question() {
    }

    /**
     * 
     * @param smid
     * @param question
     * @param quid
     */
    public Question(Integer quid, Integer smid, String question) {
        super();
        this.quid = quid;
        this.smid = smid;
        this.question = question;
    }

    public Integer getQuid() {
        return quid;
    }

    public void setQuid(Integer quid) {
        this.quid = quid;
    }

    public Integer getSmid() {
        return smid;
    }

    public void setSmid(Integer smid) {
        this.smid = smid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

}
