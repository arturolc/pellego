package com.gitlab.capstone.pellego.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TotalWordsReadResponse {
    @SerializedName("TotalWordsRead")
    @Expose
    public int totalWordsRead;

    public TotalWordsReadResponse(int totalWordsRead) {
        this.totalWordsRead = totalWordsRead;
    }

    public int getTotalWordsRead() {
        return totalWordsRead;
    }

    public void setTotalWordsRead(int totalWordsRead) {
        this.totalWordsRead = totalWordsRead;
    }

    @Override
    public String toString() {
        return "TotalWordsReadResponse{" +
                "totalWordsRead=" + totalWordsRead +
                '}';
    }
}
