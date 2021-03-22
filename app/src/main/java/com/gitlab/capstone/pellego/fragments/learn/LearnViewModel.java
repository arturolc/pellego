package com.gitlab.capstone.pellego.fragments.learn;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gitlab.capstone.pellego.database.LearningModulesRepo;
import com.gitlab.capstone.pellego.network.models.LMDescResponse;
import com.gitlab.capstone.pellego.network.models.LMResponse;
import com.gitlab.capstone.pellego.network.models.SMResponse;

import java.util.List;

public class LearnViewModel extends AndroidViewModel {
    private LearningModulesRepo repo;
    private LiveData<List<LMResponse>> lmResponse = new MutableLiveData<>();
//    private MutableLiveData<List<LMDescResponse>> lmDescResponse = new MutableLiveData<>();
//    private MutableLiveData<List<SMResponse>> smResponse = new MutableLiveData<>();

    public LearnViewModel(@NonNull Application application) {
        super(application);
        repo = LearningModulesRepo.getInstance(application);
    }

    public LiveData<List<LMResponse>> getLMResponse() {
        if (lmResponse.getValue() == null)
            lmResponse = repo.getModules();

        return lmResponse;
    }
}
