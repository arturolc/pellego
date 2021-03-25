package com.gitlab.capstone.pellego.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*****************************************************
 * Arturo Lara & Chris Bordoy
 *
 * Model that represents a learning submodule
 *****************************************************/

public class Submodule {

    @SerializedName("SMID")
    @Expose
    private Integer sMID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Subheader")
    @Expose
    private String subheader;

    public Integer getSMID() {
        return sMID;
    }

    public void setSMID(Integer sMID) {
        this.sMID = sMID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubheader() {
        return subheader;
    }

    public void setSubheader(String subheader) {
        this.subheader = subheader;
    }

    @Override
    public String toString() {
        return "Submodule{" +
                "sMID=" + sMID +
                ", name='" + name + '\'' +
                ", subheader='" + subheader + '\'' +
                '}';
    }
}