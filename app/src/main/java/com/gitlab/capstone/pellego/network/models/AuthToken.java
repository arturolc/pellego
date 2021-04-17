package com.gitlab.capstone.pellego.network.models;

/****************************************
 * Arturo Lara
 *
 * Represents an Authorization Token that
 * is used for all API calls
 ***************************************/

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
