package com.gitlab.capstone.pellego.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IntroContentModel {
    @SerializedName("Header")
    @Expose
    private String header;
    @SerializedName("Content")
    @Expose
    private String content;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {this.header = header;}

    public String getContent() { return content; }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "IntroContentModel{" +
                "header='" + header + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
