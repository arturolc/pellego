package com.gitlab.capstone.pellego.network.models;

import com.gitlab.capstone.pellego.fragments.auth.AuthActivity;

public class AuthToken {
    private String email;
    private String date;

    public AuthToken(String email) {
        this.email = email;
    }

    public AuthToken(String email, String date) {
        this.email = email;
        this.date = date;
    }
}
