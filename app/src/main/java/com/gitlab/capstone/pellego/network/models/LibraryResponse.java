package com.gitlab.capstone.pellego.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LibraryResponse {

    @SerializedName("BID")
    @Expose
    private Integer bid;
    @SerializedName("Book_Name")
    @Expose
    private String bookName;
    @SerializedName("Author")
    @Expose
    private String author;
    @SerializedName("Image_Url")
    @Expose
    private String imageUrl;
    @SerializedName("Book_Url")
    @Expose
    private String bookUrl;

    /**
     * No args constructor for use in serialization
     *
     */
    public LibraryResponse() {
    }

    /**
     *
     * @param author
     * @param imageUrl
     * @param bid
     * @param bookUrl
     * @param bookName
     */
    public LibraryResponse(Integer bid, String bookName, String author, String imageUrl, String bookUrl) {
        super();
        this.bid = bid;
        this.bookName = bookName;
        this.author = author;
        this.imageUrl = imageUrl;
        this.bookUrl = bookUrl;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }


    @Override
    public String toString() {
        return "LibraryResponse{" +
                "bid=" + bid +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", bookUrl='" + bookUrl + '\'' +
                '}';
    }
}
