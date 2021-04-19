package com.gitlab.capstone.pellego.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/****************************************
 * Arturo Lara
 *
 * Represents a Synopsis for all Pellego
 * Library books
 ***************************************/

public class SynopsisResponse {

    @SerializedName("Synopsis")
    @Expose
    private String synopsis;

    /**
     * No args constructor for use in serialization
     */
    public SynopsisResponse() {
    }

    /**
     * @param synopsis
     */
    public SynopsisResponse(String synopsis) {
        super();
        this.synopsis = synopsis;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    @Override
    public String toString() {
        return "SynopsisResponse{" +
                "synopsis='" + synopsis + '\'' +
                '}';
    }
}
