package com.example.pellego.ui.profile;

import com.google.gson.annotations.SerializedName;

public class PostObject {
    private int userid;
    private int id;
    private String title;

    //to name custom variables outside of JSON declared name
    @SerializedName("body")
    private String text;

    public int getUserid() {
        return userid;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
