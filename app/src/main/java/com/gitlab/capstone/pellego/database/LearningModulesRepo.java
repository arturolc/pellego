package com.gitlab.capstone.pellego.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gitlab.capstone.pellego.database.daos.LearningModulesDao;
import com.gitlab.capstone.pellego.network.APIService;
import com.gitlab.capstone.pellego.network.RetroInstance;
import com.gitlab.capstone.pellego.network.models.AuthToken;
import com.gitlab.capstone.pellego.network.models.LMDescResponse;
import com.gitlab.capstone.pellego.network.models.LMResponse;
import com.gitlab.capstone.pellego.network.models.QuizResponse;
import com.gitlab.capstone.pellego.network.models.SMResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*****************************************************
 * Arturo Lara & Chris Bordoy
 *
 * Repository for all Learning Modules endpoint calls
 *****************************************************/

public class LearningModulesRepo {
    private final LearningModulesDao dao;
    private final PellegoDatabase db;
    private final APIService apiService;
    private final MutableLiveData<List<LMResponse>> lmResponse = new MutableLiveData<>();
    private final MutableLiveData<List<LMDescResponse>> lmDescResponse = new MutableLiveData<>();
    private final MutableLiveData<List<SMResponse>> smResponse = new MutableLiveData<>();
    private final MutableLiveData<List<QuizResponse>> quizResponse = new MutableLiveData<>();
    private static LearningModulesRepo INSTANCE;

    private LearningModulesRepo(Application application) {
        db = PellegoDatabase.getDatabase(application);
        dao = db.learningModulesDao();
        apiService = RetroInstance.getRetroClient().create(APIService.class);
    }

    synchronized public static LearningModulesRepo getInstance(Application app) {
        if (INSTANCE == null) {
            INSTANCE = new LearningModulesRepo(app);
        }
        return INSTANCE;
    }

    public LiveData<List<LMResponse>> getModules() {
        Call<List<LMResponse>> call = apiService.getModules(new AuthToken("Chris.Bordoy@gmail.com"));
        call.enqueue(new Callback<List<LMResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<LMResponse>> call, @NotNull Response<List<LMResponse>> response) {
                Log.i("RETROFIT", response.body().toString());
                List<LMResponse> r = response.body();
                lmResponse.setValue(r);
            }

            @Override
            public void onFailure(@NotNull Call<List<LMResponse>> call, Throwable t) {
                Log.e("RETROFIT", t.toString());
            }
        });

        return lmResponse;
    }

    public LiveData<List<LMDescResponse>> getModuleDesc(String moduleID) {
        Call<List<LMDescResponse>> call = apiService.getModuleDescription(moduleID);
        call.enqueue(new Callback<List<LMDescResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<LMDescResponse>> call, Response<List<LMDescResponse>> response) {
                Log.i("RETROFIT", response.body().toString());
                lmDescResponse.setValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<List<LMDescResponse>> call, @NotNull Throwable t) {
                Log.e("RETROFIT", t.toString());
            }
        });

        return lmDescResponse;
    }

    public LiveData<List<SMResponse>> getSubmodules(String mID) {
        Call<List<SMResponse>> call = apiService.getSubmodules(mID);
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

    public LiveData<List<QuizResponse>> getQuizzes(String mID, String smID, String qID) {
        Call<List<QuizResponse>> call = apiService.getQuizzes(mID, smID, qID);
        call.enqueue(new Callback<List<QuizResponse>>() {

            @Override
            public void onResponse(Call<List<QuizResponse>> call, Response<List<QuizResponse>> response) {
                Log.i("RETROFIT", response.body().toString());
                quizResponse.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<QuizResponse>> call, Throwable t) {
                Log.e("RETROFIT", t.toString());
            }
        });

        return quizResponse;
    }
}
