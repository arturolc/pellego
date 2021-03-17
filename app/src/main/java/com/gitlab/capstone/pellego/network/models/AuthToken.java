package com.gitlab.capstone.pellego.network.models;

import com.gitlab.capstone.pellego.fragments.auth.AuthActivity;

public class AuthToken {
    private String email;

    public AuthToken(String email) {
        this.email = email;
    }
}
