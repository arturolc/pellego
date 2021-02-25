package com.gitlab.capstone.pellego.fragments.pellegolibrary;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gitlab.capstone.pellego.app.BookModel;
import com.example.pellego.SingletonRequestQueue;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**********************************************
 Arturo Lara
 Model for PellegoLibrary fragment. Pulls data from remote server
 **********************************************/
public class PellegoLibraryViewModel extends AndroidViewModel {

//    BookModel books[] = {};
    private MutableLiveData<List<BookModel>> books;

    // TODO: Implement the ViewModel
    public PellegoLibraryViewModel(@NonNull Application application) {
        super(application);
        books = new MutableLiveData<List<BookModel>>();
        // Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://54.176.198.201:5000/library",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.i("LIBRARY", response);
                        handleData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("LIBRARY", error.toString());
                    }
                });

        SingletonRequestQueue.getInstance(getApplication()).addToRequestQueue(stringRequest);

    }

    public MutableLiveData<List<BookModel>> getBooks() {
        return books;
    }

    private void handleData(String response) {
        try {
            ArrayList<BookModel> bks = new ArrayList<>();
            JSONArray arr = new JSONArray(response);
            for (int i = 0; i < arr.length(); i++) {
                String bID = arr.getJSONObject(i).get("BID").toString();
                String name = arr.getJSONObject(i).get("Book_Name").toString();
                String author = arr.getJSONObject(i).get("Author").toString();
                String imageUrl = arr.getJSONObject(i).get("Image_Url").toString();
                String bookUrl = arr.getJSONObject(i).get("Book_Url").toString();

                BookModel b = new BookModel(bID, name, author, bookUrl, imageUrl);
                bks.add(b);
            }
            books.setValue(bks);

        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + t.toString() + "\"");
        }
    }

}