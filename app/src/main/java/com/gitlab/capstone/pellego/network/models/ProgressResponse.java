package com.gitlab.capstone.pellego.network.models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProgressResponse {

    @SerializedName("WPM")
    @Expose
    private List<Wpm> wpm = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public ProgressResponse() {
    }


    public ProgressResponse(List<Wpm> wpm) {
        super();
        this.wpm = wpm;
    }

    public List<Wpm> getWpm() {
        return wpm;
    }

    public void setWpm(List<Wpm> wpm) {
        this.wpm = wpm;
    }

    @Override
    public String toString() {
        return "ProgressResponse{" +
                "wpm=" + wpm +
                '}';
    }
}
