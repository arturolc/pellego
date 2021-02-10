package com.example.pellego;

/**********************************************
 Arturo Lara
 UserModel class to keep track of Book and Library info
 **********************************************/
public class BookModel {
    private String bID;
    private String bookName;
    private String author;
    private String bookFilePath;
    private String imgFilePath;

    public BookModel(String bID, String bookName, String author, String bookFilePath, String imgFilePath) {
        this.bID = bID;
        this.bookName = bookName;
        this.author = author;
        this.bookFilePath = bookFilePath;
        this.imgFilePath = imgFilePath;
    }

    @Override
    public String toString() {
        return "BookModel{" +
                "bID='" + bID + '\'' +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", bookFilePath='" + bookFilePath + '\'' +
                ", imgFilePath='" + imgFilePath + '\'' +
                '}';
    }

    public String getbID() {
        return bID;
    }

    public void setbID(String bID) {
        this.bID = bID;
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

    public String getBookFilePath() {
        return bookFilePath;
    }

    public void setBookFilePath(String bookFilePath) {
        this.bookFilePath = bookFilePath;
    }

    public String getImgFilePath() {
        return imgFilePath;
    }

    public void setImgFilePath(String imgFilePath) {
        this.imgFilePath = imgFilePath;
    }


}