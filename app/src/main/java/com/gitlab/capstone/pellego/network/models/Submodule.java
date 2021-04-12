
package com.gitlab.capstone.pellego.network.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Submodule {

    @SerializedName("SMID")
    @Expose
    private Integer smid;
    @SerializedName("MID")
    @Expose
    private Integer mid;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Subheader")
    @Expose
    private String subheader;
    @SerializedName("Text")
    @Expose
    private String text;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Submodule() {
    }

    /**
     * 
     * @param smid
     * @param subheader
     * @param name
     * @param mid
     * @param text
     */
    public Submodule(Integer smid, Integer mid, String name, String subheader, String text) {
        super();
        this.smid = smid;
        this.mid = mid;
        this.name = name;
        this.subheader = subheader;
        this.text = text;
    }

    public Integer getSmid() {
        return smid;
    }

    public void setSmid(Integer smid) {
        this.smid = smid;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
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

}
