package com.gitlab.capstone.pellego.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Module {

    @SerializedName("MID")
    @Expose
    private Integer mid;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Subheader")
    @Expose
    private String subheader;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Icon")
    @Expose
    private String icon;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Module() {
    }

    public Module(Integer mid, String name, String subheader, String description, String icon) {
        super();
        this.mid = mid;
        this.name = name;
        this.subheader = subheader;
        this.description = description;
        this.icon = icon;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
