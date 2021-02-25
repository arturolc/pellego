package com.gitlab.capstone.pellego.fragments.pellegolibrary;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gitlab.capstone.pellego.app.BookModel;
import com.example.pellego.SingletonRequestQueue;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**********************************************
 Arturo Lara
 Model for BookPreviewActivity class. Pulls data from remote server
 using Volley. Loads images using Glide.
 **********************************************/
public class BookPreviewModel extends AndroidViewModel {
    private MutableLiveData<String> synopsis;
    private String id;
    public BookPreviewModel(@NonNull Application application, String id) {
        super(application);
        this.id = id;
        synopsis = new MutableLiveData<>();
        // Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://54.176.198.201:5000/library/synopsis/" + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleData(response);
                        Log.d("BookPreviewModel", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("BookPreviewModel", error.toString());
                    }
                });

        SingletonRequestQueue.getInstance(getApplication()).addToRequestQueue(stringRequest);
    }

    private void handleData(String response) {
        try {
            String syn = "";
            JSONArray arr = new JSONArray(response);
            for (int i = 0; i < arr.length(); i++) {
                syn = arr.getJSONObject(i).get("Synopsis").toString();
            }
            synopsis.setValue(syn);

        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + t.toString() + "\"");
        }
    }

    public MutableLiveData<String> getData(){
        return synopsis;
    }

    public void addBookToUserLibrary() {
//        getFilesDir()
    }

}
