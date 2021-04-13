package com.gitlab.capstone.pellego.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TodayProgressValueResponse {
    @SerializedName("WordsRead")
    @Expose
    private Integer wordsRead;
    @SerializedName("WPM")
    @Expose
    private Integer wpm;

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
        return "TodayProgressValueResponse{" +
                "wordsRead=" + wordsRead +
                ", wpm=" + wpm +
                '}';
    }
}
