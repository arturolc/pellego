package com.gitlab.capstone.pellego.network.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*****************************************************
 * Chris Bordoy, Arturo Lara
 *
 * Model that represents a modules introduction content
 * It implements the Parcelable interface so it can be
 * passed as an object through a bundle
 *****************************************************/

public class IntroContentModel implements Parcelable {
    @SerializedName("Header")
    @Expose
    private String header;
    @SerializedName("Content")
    @Expose
    private String content;

    public IntroContentModel(String header, String content) {
        this.header = header;
        this.content = content;
    }

    protected IntroContentModel(Parcel in) {
        header = in.readString();
        content = in.readString();
    }

    public static final Creator<IntroContentModel> CREATOR = new Creator<IntroContentModel>() {
        @Override
        public IntroContentModel createFromParcel(Parcel in) {
            return new IntroContentModel(in);
        }

        @Override
        public IntroContentModel[] newArray(int size) {
            return new IntroContentModel[size];
        }
    };

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {this.header = header;}

    public String getContent() { return content; }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "IntroContentModel{" +
                "header='" + header + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(header);
        parcel.writeString(content);
    }
}
