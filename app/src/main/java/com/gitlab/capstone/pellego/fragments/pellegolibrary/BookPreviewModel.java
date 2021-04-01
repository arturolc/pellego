package com.gitlab.capstone.pellego.fragments.pellegolibrary;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gitlab.capstone.pellego.database.BooksRepo;
import com.gitlab.capstone.pellego.network.models.SynopsisResponse;

import java.util.List;

/**********************************************
 Arturo Lara
 Model for BookPreviewActivity class. Pulls data from remote server
 using Volley. Loads images using Glide.
 **********************************************/
public class BookPreviewModel extends AndroidViewModel {
    private LiveData<List<SynopsisResponse>> synopsis;
    private BooksRepo repo;
    private String id;
    public BookPreviewModel(@NonNull Application application, String id) {
        super(application);
        this.id = id;
        repo = BooksRepo.getInstance(application);
        synopsis = new MutableLiveData<>();
    }

    public LiveData<List<SynopsisResponse>> getSynopsisResponse(){
        if (synopsis.getValue() == null) {
            synopsis = repo.getSynopsis((int)Integer.parseInt(id));
        }
        return synopsis;
    }


}
