package com.example.pellego;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "pellego.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersQuery =   "CREATE TABLE IF NOT EXISTS Users (" +
                                    "UID INTEGER PRIMARY KEY AUTOINCREMENT," +
                                    "UniqueIdentifier VARCHAR(320) UNIQUE NOT NULL," +
                                    "FirstName VARCHAR(50) NOT NULL," +
                                    "LastName VARCHAR(50) NOT NULL" +
                                    ")";

        String createLibraryQuery = "CREATE TABLE IF NOT EXISTS Library (" +
                                    "UID INT NOT NULL," +
                                    "BID INT NOT NULL," +
                                    "PRIMARY KEY (UID, BID)," +
                                    "FOREIGN KEY (UID) REFERENCES Users(UID)," +
                                    "FOREIGN KEY (BID) REFERENCES Books(BID))";

        String createBooksQuery =   "CREATE TABLE IF NOT EXISTS Books (" +
                                    "BID INTEGER PRIMARY KEY AUTOINCREMENT," +
                                    "BookName VARCHAR(200) NOT NULL," +
                                    "Author VARCHAR(100) NOT NULL," +
                                    "FilePath VARCHAR(255) NOT NULL" +
                                    ")";

        db.execSQL(createUsersQuery);
        db.execSQL(createLibraryQuery);
        db.execSQL(createBooksQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addUser(UserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("UniqueIdentifier", user.getEmail());
        cv.put("FirstName", user.getFirstName());
        cv.put("LastName", user.getLastName());

        long res = db.insert("Users", null, cv);

        return res != -1;
    }

    public boolean addBook(BookModel book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("BookName", book.getBookName());
        cv.put("Author", book.getAuthor());
        cv.put("FilePath", book.getFilePath());

        long res = db.insert("Users", null, cv);

        return res != 1;
    }
}
