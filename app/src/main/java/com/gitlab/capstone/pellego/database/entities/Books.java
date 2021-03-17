package com.gitlab.capstone.pellego.database.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.geometerplus.android.fbreader.sync.SyncOperations;

@Entity
public class Books {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "BID")
    private int bID;

    @ColumnInfo(name = "Book_Name")
    @NonNull
    private String bookName;

    @ColumnInfo(name = "Author")
    @NonNull
    private String author;

    @ColumnInfo(name = "Synopsis")
    @NonNull
    private String synopsis;

    @ColumnInfo(name = "Book_Url")
    @NonNull
    private String bookUrl;

    @ColumnInfo(name = "Image_Url")
    @NonNull
    private String imageUrl;

    public Books(int bID, @NonNull String bookName, @NonNull String author,
                 @NonNull String synopsis, @NonNull String bookUrl, @NonNull String imageUrl) {
        this.bID = bID;
        this.bookName = bookName;
        this.author = author;
        this.synopsis = synopsis;
        this.bookUrl = bookUrl;
        this.imageUrl = imageUrl;
    }

    public int getBID() {
        return bID;
    }

    @NonNull
    public String getBookName() {
        return bookName;
    }

    @NonNull
    public String getAuthor() {
        return author;
    }

    @NonNull
    public String getSynopsis() {
        return synopsis;
    }

    @NonNull
    public String getBookUrl() {
        return bookUrl;
    }

    @NonNull
    public String getImageUrl() {
        return imageUrl;
    }
}
