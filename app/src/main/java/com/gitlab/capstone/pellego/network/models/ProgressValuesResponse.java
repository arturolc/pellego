package com.gitlab.capstone.pellego.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**********************************************
 Chris Bordoy

 Models a Progress Reports Value Response
 **********************************************/

public class ProgressValuesResponse {
    @SerializedName("Recorded")
    @Expose
    private String recorded;
    @SerializedName("WordsRead")
    @Expose
    private Integer wordsRead;
    @SerializedName("WPM")
    @Expose
    private Integer wpm;

    public String getRecorded() {
        return recorded;
    }

    public void setRecorded(String recorded) {
        this.recorded = recorded;
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

    @Override
    public String toString() {
        return "ProgressValuesResponse{" +
                "recorded='" + recorded + '\'' +
                ", wordsRead=" + wordsRead +
                ", wpm=" + wpm +
                '}';
    }
}
