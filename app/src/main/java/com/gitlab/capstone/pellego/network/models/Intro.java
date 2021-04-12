package com.gitlab.capstone.pellego.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Intro {

    @SerializedName("IID")
    @Expose
    private Integer iid;
    @SerializedName("MID")
    @Expose
    private Integer mid;
    @SerializedName("Header")
    @Expose
    private String header;
    @SerializedName("Content")
    @Expose
    private String content;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Intro() {
    }

    /**
     * 
     * @param iid
     * @param mid
     * @param header
     * @param content
     */
    public Intro(Integer iid, Integer mid, String header, String content) {
        super();
        this.iid = iid;
        this.mid = mid;
        this.header = header;
        this.content = content;
    }

    public Integer getIid() {
        return iid;
    }

    public void setIid(Integer iid) {
        this.iid = iid;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
