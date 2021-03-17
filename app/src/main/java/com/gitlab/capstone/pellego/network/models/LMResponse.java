package com.gitlab.capstone.pellego.network.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LMResponse {

    @SerializedName("MID")
    @Expose
    private Integer mID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("completed")
    @Expose
    private Integer completed;
    @SerializedName("totalSubmodules")
    @Expose
    private Integer totalSubmodules;

    /**
     * No args constructor for use in serialization
     *
     */
    public LMResponse() {
    }

    /**
     *
     * @param name
     * @param totalSubmodules
     * @param mID
     * @param completed
     */
    public LMResponse(Integer mID, String name, Integer completed, Integer totalSubmodules) {
        super();
        this.mID = mID;
        this.name = name;
        this.completed = completed;
        this.totalSubmodules = totalSubmodules;
    }

    public Integer getMID() {
        return mID;
    }

    public void setMID(Integer mID) {
        this.mID = mID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

    public Integer getTotalSubmodules() {
        return totalSubmodules;
    }

    public void setTotalSubmodules(Integer totalSubmodules) {
        this.totalSubmodules = totalSubmodules;
    }

    @Override
    public String toString() {
        return "LMResponse{" +
                "mID=" + mID +
                ", name='" + name + '\'' +
                ", completed=" + completed +
                ", totalSubmodules=" + totalSubmodules +
                '}';
    }
}

