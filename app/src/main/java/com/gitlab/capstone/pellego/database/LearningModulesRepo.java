package com.gitlab.capstone.pellego.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.amazonaws.mobileconnectors.cognitoauth.Auth;
import com.gitlab.capstone.pellego.database.daos.LearningModulesDao;
import com.gitlab.capstone.pellego.network.APIService;
import com.gitlab.capstone.pellego.network.RetroInstance;
import com.gitlab.capstone.pellego.network.models.AuthToken;
import com.gitlab.capstone.pellego.network.models.LMDescResponse;
import com.gitlab.capstone.pellego.network.models.LMResponse;
import com.gitlab.capstone.pellego.network.models.SMResponse;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LearningModulesRepo {
    private LearningModulesDao dao;
    private final PellegoDatabase db;
    private APIService apiService;
    private MutableLiveData<List<LMResponse>> lmResponse = new MutableLiveData<>();
    private MutableLiveData<List<LMDescResponse>> lmDescResponse = new MutableLiveData<>();
    private MutableLiveData<List<SMResponse>> smResponse = new MutableLiveData<>();

    public LearningModulesRepo(Application application) {
        db = PellegoDatabase.getDatabase(application);
        dao = db.learningModulesDao();
        apiService = RetroInstance.getRetroClient().create(APIService.class);
        getModules();
        getModuleDesc("1");
        getSubmodule("1", "2");
    }

    public LiveData<List<LMResponse>> getModules() {
        Call<List<LMResponse>> call = apiService.getModules(new AuthToken("Arturo.Lara@gmail.com"));
        call.enqueue(new Callback<List<LMResponse>>() {
            @Override
            public void onResponse(Call<List<LMResponse>> call, Response<List<LMResponse>> response) {
                Log.i("RETROFIT", response.body().toString());
                List<LMResponse> r = response.body();
                lmResponse.setValue(r);
            }

            @Override
            public void onFailure(Call<List<LMResponse>> call, Throwable t) {
                Log.e("RETROFIT", t.toString());
            }
        });

        return lmResponse;
    }

    public LiveData<List<LMDescResponse>> getModuleDesc(String moduleID) {
        Call<List<LMDescResponse>> call = apiService.getModuleDescription(moduleID);
        call.enqueue(new Callback<List<LMDescResponse>>() {
            @Override
            public void onResponse(Call<List<LMDescResponse>> call, Response<List<LMDescResponse>> response) {
                Log.i("RETROFIT", response.body().toString());
                lmDescResponse.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<LMDescResponse>> call, Throwable t) {
                Log.e("RETROFIT", t.toString());
            }
        });

        return lmDescResponse;
    }

    public LiveData<List<SMResponse>> getSubmodule(String mID, String sMID) {
        Call<List<SMResponse>> call = apiService.getSubmodule(mID, sMID);
        call.enqueue(new Callback<List<SMResponse>>() {

            @Override
            public void onResponse(Call<List<SMResponse>> call, Response<List<SMResponse>> response) {
                Log.i("RETROFIT", response.body().toString());
                smResponse.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<SMResponse>> call, Throwable t) {
                Log.e("RETROFIT", t.toString());
            }
        });
        return smResponse;
    }
}
