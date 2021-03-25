package com.gitlab.capstone.pellego.fragments.learn;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gitlab.capstone.pellego.database.LearningModulesRepo;
import com.gitlab.capstone.pellego.network.models.LMResponse;

import java.util.List;

/**********************************************
 Chris Bordoy & Arturo Lara

 The Learn Fragment view model
 **********************************************/

public class LearnViewModel extends AndroidViewModel {
    private final LearningModulesRepo repo;
    private LiveData<List<LMResponse>> lmResponse;

    public LearnViewModel(@NonNull Application application) {
        super(application);
        repo = LearningModulesRepo.getInstance(application);
        lmResponse = new MutableLiveData<>();
    }

    public LiveData<List<LMResponse>> getLMResponse() {
        if (lmResponse.getValue() == null) {
            lmResponse = repo.getModules();
        }

        return lmResponse;
    }
}
