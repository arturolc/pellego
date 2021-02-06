package com.example.pellego;

public class BookModel {
    private String bookName;
    private String author;
    private String filePath;

    public BookModel(String bookName, String author, String filePath) {
        this.bookName = bookName;
        this.author = author;
        this.filePath = filePath;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthor() {
        return author;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public String toString() {
        return "BookModel{" +
                "bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
