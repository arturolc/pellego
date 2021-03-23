package com.gitlab.capstone.pellego.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SMResponse {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Subheader")
    @Expose
    private String subheader;
    @SerializedName("Text")
    @Expose
    private String text;
    @SerializedName("Content")
    @Expose
    private List<IntroContentModel> introContent;

    public List<IntroContentModel> getIntroContent() {
        return introContent;
    }

    public void setIntroContent(List<IntroContentModel> introContent) {
        this.introContent = introContent;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "SMResponse{" +
                "name='" + name + '\'' +
                ", subheader='" + subheader + '\'' +
                ", text='" + text + '\'' +
                ", introContent=" + introContent +
                '}';
    }
}

