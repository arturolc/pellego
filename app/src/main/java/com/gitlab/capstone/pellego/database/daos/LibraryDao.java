package com.gitlab.capstone.pellego.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.gitlab.capstone.pellego.database.entities.Books;

import org.geometerplus.fbreader.book.Book;

import java.util.List;

@Dao
public interface LibraryDao {

    @Insert
    void insert(Books book);

    @Query("SELECT BID, Book_Name, Author, Image_Url, Book_Url, Synopsis from Books")
    LiveData<List<Books>> getBooks();
}
