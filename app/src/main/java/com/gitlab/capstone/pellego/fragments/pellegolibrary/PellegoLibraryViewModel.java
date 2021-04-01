package com.gitlab.capstone.pellego.fragments.pellegolibrary;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
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
import com.gitlab.capstone.pellego.database.BooksRepo;
import com.gitlab.capstone.pellego.network.models.LibraryResponse;

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
    private LiveData<List<LibraryResponse>> libResp;
    private BooksRepo repo;

    // TODO: Implement the ViewModel
    public PellegoLibraryViewModel(@NonNull Application application) {
        super(application);
        repo = BooksRepo.getInstance(application);
        libResp = new MutableLiveData<>();
    }

    public LiveData<List<LibraryResponse>> getLibResp() {
        if (libResp.getValue() == null) {
            libResp = repo.getLibrary();
        }
        return libResp;
    }
}