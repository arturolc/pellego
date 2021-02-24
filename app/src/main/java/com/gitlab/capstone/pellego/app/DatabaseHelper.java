package com.gitlab.capstone.pellego.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**********************************************
 Arturo Lara and Joanna Lowry
 DatabaseHelper uses SQLite local database to cache online data
 so application can work offline
 **********************************************/
public class DatabaseHelper extends SQLiteOpenHelper {
    private Context ctx;

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "pellego.db", null, 1);
        ctx = context;
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

        // TODO: Add learning modules, progress reports, statistics tables

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

        //talk to the cloud database here
        //what happens if we are unable to establish a connection to the aws server?
        //we could queue the users and add them whenever we have to talk to the database again
        //or we could rollback adding the user...TODO
        getApiData(user.getFirstName(), user.getLastName(), user.getEmail(), new DatabaseHelper.ResponseCallback() {
            @Override
            public void onResponse(Object response) {
                //currently does nothing
            }
        });


        return res != -1;
    }

    public interface ResponseCallback
    {
        void onResponse(Object response);
    }

    private void getApiData(String first, String last, String email, DatabaseHelper.ResponseCallback responseCallBack)
    {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(ctx);
        String url ="http://54.176.198.201/user/add";
        JSONObject user = new JSONObject();
        try {
            user.put("email", email);
            user.put("name", first + " " + last);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        JSONArray request = new JSONArray();
        request.put(user);

        // Request a json response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.POST, url, request, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        //Currently does nothing but we could consider doing something in the future
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", error.toString());
                        //Currently does nothing. If it fails we should rollback changes to the local database and not let the user log in
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);
    }
    public boolean addBook(BookModel book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("BookName", book.getBookName());
        cv.put("Author", book.getAuthor());
        cv.put("FilePath", book.getBookFilePath());

        long res = db.insert("Users", null, cv);

        return res != 1;
    }
}
