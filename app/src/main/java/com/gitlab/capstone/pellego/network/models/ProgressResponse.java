package com.gitlab.capstone.pellego.network.models;

import java.util.List;

import com.gitlab.capstone.pellego.database.entities.UserWordValues;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProgressResponse {

    @SerializedName("values")
    @Expose
    private List<UserWordValues> values = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public ProgressResponse() {
    }


    public ProgressResponse(List<UserWordValues> values) {
        this.values = values;
    }

    public List<UserWordValues> getValues() {
        return values;
    }

    public void setValues(List<UserWordValues> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "ProgressResponse{" +
                "values=" + values +
                '}';
    }
}
