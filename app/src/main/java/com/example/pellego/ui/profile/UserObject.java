package com.example.pellego.ui.profile;

import com.google.gson.annotations.SerializedName;

public class UserObject {

    @SerializedName("Name")
    private String userName;

    public String getUserName() {
        return userName;
    }
}
