package com.gitlab.capstone.pellego.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class LastRecordedDate {
    @SerializedName("date")
    @Expose
    private Date date;

    public LastRecordedDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "LastRecordedDate{" +
                "date=" + date +
                '}';
    }
}
