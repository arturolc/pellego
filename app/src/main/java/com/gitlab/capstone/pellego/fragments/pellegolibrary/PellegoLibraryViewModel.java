package com.gitlab.capstone.pellego.fragments.pellegolibrary;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gitlab.capstone.pellego.database.BooksRepo;
import com.gitlab.capstone.pellego.network.models.LibraryResponse;

import java.util.List;

/**********************************************
 Arturo Lara
 Model for PellegoLibraryFragment fragment. Pulls data from remote server
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