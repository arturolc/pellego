package com.gitlab.capstone.pellego.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.gitlab.capstone.pellego.database.entities.Books;

import java.util.List;

/****************************************
 * Arturo Lara
 *
 * Represents a Library
 * data access object for caching
 ***************************************/

@Dao
public interface LibraryDao {

    @Insert
    void insert(Books book);

    @Query("SELECT BID, Book_Name, Author, Image_Url, Book_Url, Synopsis from Books")
    LiveData<List<Books>> getBooks();
}
