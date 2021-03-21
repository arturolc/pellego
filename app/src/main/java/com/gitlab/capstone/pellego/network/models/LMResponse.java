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
    @SerializedName("Subheader")
    @Expose
    private String subheader;
    @SerializedName("Icon")
    @Expose
    private String icon;
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

    public LMResponse(Integer mID, String name, String subheader, String icon, Integer completed, Integer totalSubmodules) {
        this.mID = mID;
        this.name = name;
        this.subheader = subheader;
        this.icon = icon;
        this.completed = completed;
        this.totalSubmodules = totalSubmodules;
    }

    public String getSubheader() {
        return subheader;
    }

    public void setSubheader(String subheader) {
        this.subheader = subheader;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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
                ", subheader='" + subheader + '\'' +
                ", icon='" + icon + '\'' +
                ", completed=" + completed +
                ", totalSubmodules=" + totalSubmodules +
                '}';
    }
}

