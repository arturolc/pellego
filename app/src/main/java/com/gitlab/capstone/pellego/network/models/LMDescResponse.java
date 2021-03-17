package com.gitlab.capstone.pellego.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LMDescResponse {

    @SerializedName("MID")
    @Expose
    private Integer mID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Description")
    @Expose
    private String description;

    /**
     * No args constructor for use in serialization
     *
     */
    public LMDescResponse() {
    }

    /**
     *
     * @param name
     * @param mID
     * @param description
     */
    public LMDescResponse(Integer mID, String name, String description) {
        super();
        this.mID = mID;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "LMDescResponse{" +
                "mID=" + mID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
