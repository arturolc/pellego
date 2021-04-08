package com.gitlab.capstone.pellego.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gitlab.capstone.pellego.network.APIService;
import com.gitlab.capstone.pellego.network.RetroInstance;
import com.gitlab.capstone.pellego.network.models.AuthToken;
import com.gitlab.capstone.pellego.network.models.CompletionResponse;
import com.gitlab.capstone.pellego.network.models.LMDescResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*****************************************************
 * Chris Bordoy
 *
 * Repository for all User endpoint calls
 *****************************************************/

public class UsersRepo {
    private final PellegoDatabase db;
    private final APIService apiService;
    private static UsersRepo INSTANCE;
    private final MutableLiveData<List<CompletionResponse>> completionResponse = new MutableLiveData<>();

    private  UsersRepo(Application application) {
        db = PellegoDatabase.getDatabase(application);
        apiService = RetroInstance.getRetroClient2().create(APIService.class);
    }

    synchronized public static UsersRepo getInstance(Application app) {
        if (INSTANCE == null) {
            INSTANCE = new UsersRepo(app);
        }
        return INSTANCE;
    }

    public void setSubmoduleCompletion(String mID, String smID) {
        Call<Void> call = apiService.setSubmoduleCompletion(new AuthToken("Chris.Bordoy@gmail.com"), mID, smID);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                Log.e("RETROFIT", t.toString());
            }
        });
    }

    public LiveData<List<CompletionResponse>> getUserLearningModulesCompletionCount() {
        Call<List<CompletionResponse>> call =
                apiService.getUserLearningModulesCompletionCount(new AuthToken("Chris.Bordoy@gmail.com"));
        call.enqueue(new Callback<List<CompletionResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<CompletionResponse>> call, Response<List<CompletionResponse>> response) {
                Log.i("RETROFIT", response.body().toString());
                completionResponse.setValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<List<CompletionResponse>> call, @NotNull Throwable t) {
                Log.e("RETROFIT", t.toString());
            }
        });

        return completionResponse;
    }
}
