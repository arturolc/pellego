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
    @SerializedName("Hash_String")
    @Expose
    private String hashString;

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
    public LibraryResponse(Integer bid, String bookName, String author, String imageUrl,
                           String bookUrl, String hashString) {
        super();
        this.bid = bid;
        this.bookName = bookName;
        this.author = author;
        this.imageUrl = imageUrl;
        this.bookUrl = bookUrl;
        this.hashString = hashString;
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

    public String getHashString() {
        return hashString;
    }

    public void setHashString(String hashString) {
        this.hashString = hashString;
    }

    @Override
    public String toString() {
        return "LibraryResponse{" +
                "bid=" + bid +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", bookUrl='" + bookUrl + '\'' +
                ", hashString='" + hashString + '\'' +
                '}';
    }
}
