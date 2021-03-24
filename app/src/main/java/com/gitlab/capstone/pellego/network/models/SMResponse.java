package com.gitlab.capstone.pellego.network.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SMResponse implements Parcelable {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Subheader")
    @Expose
    private String subheader;
    @SerializedName("Text")
    @Expose
    private String text;
    @SerializedName("Content")
    @Expose
    private List<IntroContentModel> introContent;

    public SMResponse(String name, String subheader, String text, List<IntroContentModel> introContent) {
        this.name = name;
        this.subheader = subheader;
        this.text = text;
        this.introContent = introContent;
    }

    protected SMResponse(Parcel in) {
        name = in.readString();
        subheader = in.readString();
        text = in.readString();
    }

    public static final Creator<SMResponse> CREATOR = new Creator<SMResponse>() {
        @Override
        public SMResponse createFromParcel(Parcel in) {
            return new SMResponse(in);
        }

        @Override
        public SMResponse[] newArray(int size) {
            return new SMResponse[size];
        }
    };

    public List<IntroContentModel> getIntroContent() {
        return introContent;
    }

    public void setIntroContent(List<IntroContentModel> introContent) {
        this.introContent = introContent;
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

    @Override
    public String toString() {
        return "SMResponse{" +
                "name='" + name + '\'' +
                ", subheader='" + subheader + '\'' +
                ", text='" + text + '\'' +
                ", introContent=" + introContent +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(subheader);
        parcel.writeString(text);
    }
}

