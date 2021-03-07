package com.gitlab.capstone.pellego.network.models;

import java.util.List;
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
    @SerializedName("Submodules")
    @Expose
    private List<Submodule> submodules = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public LMDescResponse() {
    }

    /**
     *
     * @param submodules
     * @param name
     * @param mID
     * @param description
     */
    public LMDescResponse(Integer mID, String name, String description, List<Submodule> submodules) {
        super();
        this.mID = mID;
        this.name = name;
        this.description = description;
        this.submodules = submodules;
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
                ", submodules=" + submodules +
                '}';
    }

    public List<Submodule> getSubmodules() {
        return submodules;
    }

    public void setSubmodules(List<Submodule> submodules) {
        this.submodules = submodules;
    }

}