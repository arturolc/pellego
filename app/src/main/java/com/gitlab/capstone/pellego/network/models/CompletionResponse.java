package com.gitlab.capstone.pellego.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/***************************************************
 *  Chris Bordoy
 *
 *  Model that represents a Completion object
 **************************************************/

public class CompletionResponse {
    @SerializedName("MID")
    @Expose
    private Integer mid;
    @SerializedName("SMID")
    @Expose
    private Integer smid;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getSmid() {
        return smid;
    }

    public void setSmid(Integer smid) {
        this.smid = smid;
    }
}
