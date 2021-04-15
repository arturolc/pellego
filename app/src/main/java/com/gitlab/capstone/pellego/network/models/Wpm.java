package com.gitlab.capstone.pellego.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wpm {

    @SerializedName("uwID")
    @Expose
    private Integer uwID;
    @SerializedName("UID")
    @Expose
    private Integer uid;
    @SerializedName("WordsRead")
    @Expose
    private Integer wordsRead;
    @SerializedName("WPM")
    @Expose
    private Integer wpm;
    @SerializedName("Recorded")
    @Expose
    private String recorded;

    /**
     * No args constructor for use in serialization
     *
     */
    public Wpm() {
    }


    public Wpm(Integer uwID, Integer uid, Integer wordsRead, Integer wpm, String recorded) {
        super();
        this.uwID = uwID;
        this.uid = uid;
        this.wordsRead = wordsRead;
        this.wpm = wpm;
        this.recorded = recorded;
    }

    public Integer getUwID() {
        return uwID;
    }

    public void setUwID(Integer uwID) {
        this.uwID = uwID;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getWordsRead() {
        return wordsRead;
    }

    public void setWordsRead(Integer wordsRead) {
        this.wordsRead = wordsRead;
    }

    public Integer getWpm() {
        return wpm;
    }

    public void setWpm(Integer wpm) {
        this.wpm = wpm;
    }

    public String getRecorded() {
        return recorded;
    }

    public void setRecorded(String recorded) {
        this.recorded = recorded;
    }

}
