package com.gitlab.capstone.pellego.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SMResponse {

    @SerializedName("SMID")
    @Expose
    private Integer sMID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Text")
    @Expose
    private String text;

    /**
     * No args constructor for use in serialization
     *
     */
    public SMResponse() {
    }

    /**
     *
     * @param sMID
     * @param name
     * @param text
     */
    public SMResponse(Integer sMID, String name, String text) {
        super();
        this.sMID = sMID;
        this.name = name;
        this.text = text;
    }

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "SMResponse{" +
                "sMID=" + sMID +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
