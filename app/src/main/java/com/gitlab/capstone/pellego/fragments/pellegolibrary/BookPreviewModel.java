package com.gitlab.capstone.pellego.fragments.pellegolibrary;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gitlab.capstone.pellego.app.Storage;
import com.gitlab.capstone.pellego.database.BooksRepo;
import com.gitlab.capstone.pellego.network.models.SynopsisResponse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**********************************************
 Arturo Lara
 Model for BookPreviewActivity class. Pulls data from remote server
 using Volley. Loads images using Glide.
 **********************************************/
public class BookPreviewModel extends AndroidViewModel {
    private LiveData<List<SynopsisResponse>> synopsis;
    private BooksRepo repo;
    private HashSet<String> set;

    public BookPreviewModel(@NonNull Application application) {
        super(application);
        repo = BooksRepo.getInstance(application);
        synopsis = new MutableLiveData<>();
        set = new HashSet<>();
        Storage s = new Storage(application.getApplicationContext());
        ArrayList<Storage.Book> b = s.list();
        for (int i = 0; i < b.size(); i++) {
            String title = Storage.getTitle(b.get(i).info);
            Log.d("BookPreviewModel", title);
            set.add(title);
        }
    }

    public LiveData<List<SynopsisResponse>> getSynopsisResponse(String id){
        if (synopsis.getValue() == null) {
            synopsis = repo.getSynopsis((int)Integer.parseInt(id));
        }
        return synopsis;
    }

    public boolean inStorage(String name) {
        return set.contains(name);
    }
}
