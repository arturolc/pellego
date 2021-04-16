package com.gitlab.capstone.pellego.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gitlab.capstone.pellego.database.daos.LibraryDao;
import com.gitlab.capstone.pellego.database.entities.Books;
import com.gitlab.capstone.pellego.database.entities.Library;
import com.gitlab.capstone.pellego.network.APIService;
import com.gitlab.capstone.pellego.network.RetroInstance;
import com.gitlab.capstone.pellego.network.models.LibraryResponse;
import com.gitlab.capstone.pellego.network.models.SynopsisResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksRepo {
    private LibraryDao libDao;
    private static BooksRepo INSTANCE;
    private final PellegoDatabase db;
    private final APIService apiService;
    private final MutableLiveData<List<LibraryResponse>> libResponse;
    private final MutableLiveData<List<SynopsisResponse>> synResponse;

    public BooksRepo(Application application) {
        db = PellegoDatabase.getDatabase(application);
        libDao = db.libraryDao();
        apiService = RetroInstance.getRetroClient().create(APIService.class);
        libResponse = new MutableLiveData<>();
        synResponse = new MutableLiveData<>();
    }

    synchronized public static BooksRepo getInstance(Application app) {
        if (INSTANCE == null) {
            INSTANCE = new BooksRepo(app);
        }
        return INSTANCE;
    }

    public LiveData<List<LibraryResponse>> getLibrary() {
        Call<List<LibraryResponse>> call = apiService.getLibrary();
        call.enqueue(new Callback<List<LibraryResponse>>() {

            @Override
            public void onResponse(Call<List<LibraryResponse>> call, Response<List<LibraryResponse>> response) {
                Log.i("LIBRARYREPO", response.body().toString());
                libResponse.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<LibraryResponse>> call, Throwable t) {
                Log.e("LIBRARYREPO", t.toString());
            }
        });

        return libResponse;
    }

    public LiveData<List<SynopsisResponse>> getSynopsis(int bID) {
        Call<List<SynopsisResponse>> call = apiService.getSynopsis("" + bID);
        call.enqueue(new Callback<List<SynopsisResponse>>() {
            @Override
            public void onResponse(Call<List<SynopsisResponse>> call, Response<List<SynopsisResponse>> response) {
                Log.i("LIBRARYREPO", response.body().toString());
                synResponse.setValue(response.body());

            }

            @Override
            public void onFailure(Call<List<SynopsisResponse>> call, Throwable t) {
                Log.e("LIBRARYREPO", t.toString());
            }
        });

        return synResponse;
    }





}
