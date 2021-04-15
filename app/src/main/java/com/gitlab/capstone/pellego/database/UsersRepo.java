package com.gitlab.capstone.pellego.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gitlab.capstone.pellego.network.APIService;
import com.gitlab.capstone.pellego.network.RetroInstance;
import com.gitlab.capstone.pellego.network.models.AuthToken;
import com.gitlab.capstone.pellego.network.models.CompletionResponse;
import com.gitlab.capstone.pellego.network.models.ProgressResponse;
import com.gitlab.capstone.pellego.network.models.ProgressValuesResponse;
import com.gitlab.capstone.pellego.network.models.TotalWordsReadResponse;

import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
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
    private final MutableLiveData<List<ProgressValuesResponse>> progressValuesResponse = new MutableLiveData<>();
    private final MutableLiveData<TotalWordsReadResponse> totalWordsReadResponse = new MutableLiveData<>();
    private final MutableLiveData<ProgressResponse> progressResponse = new MutableLiveData<>();

    private  UsersRepo(Application application) {
        db = PellegoDatabase.getDatabase(application);
        apiService = RetroInstance.getRetroClient().create(APIService.class);

        // check cached data

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

    public void setUserWordValues(int wordsRead, int wpm) {
        Call<Void> call = apiService.setUserWordValues(
                new AuthToken("Chris.Bordoy@gmail.com"),
                wordsRead,
                wpm);
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

    public LiveData<List<ProgressValuesResponse>> getProgressValues() {
        Call<List<ProgressValuesResponse>> call =
                apiService.getProgressValues(new AuthToken("Chris.Bordoy@gmail.com"));
        call.enqueue(new Callback<List<ProgressValuesResponse>>() {

            @Override
            public void onResponse(@NotNull Call<List<ProgressValuesResponse>> call, Response<List<ProgressValuesResponse>> response) {
                Log.d("RETROFIT: ", response.body().toString());
                progressValuesResponse.setValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<List<ProgressValuesResponse>> call, @NotNull Throwable t) {
                t.printStackTrace();
                Log.e("RETROFIT: ", t.toString());
            }
        });

        return progressValuesResponse;
    }

    public LiveData<TotalWordsReadResponse> getTotalWordsRead() {
        Call<TotalWordsReadResponse> call =
                apiService.getTotalWordsRead(new AuthToken("Chris.Bordoy@gmail.com"));
        call.enqueue(new Callback<TotalWordsReadResponse>() {

            @Override
            public void onResponse(Call<TotalWordsReadResponse> call, Response<TotalWordsReadResponse> response) {
                Log.d("RETROFIT: ", response.body().toString());
                totalWordsReadResponse.setValue(response.body());
            }

            @Override
            public void onFailure(Call<TotalWordsReadResponse> call, Throwable t) {
                t.printStackTrace();
                Log.e("RETROFIT: ", t.toString());
            }
        });

        return totalWordsReadResponse;
    }

    public LiveData<ProgressResponse> getProgress(long millis) {
        Timestamp t = new java.sql.Timestamp(millis);
        Call<ProgressResponse> call =
                apiService.getProgressResponse(new AuthToken("Chris.Bordoy@gmail.com",
                        t.toString()));
        call.enqueue(new Callback<ProgressResponse>() {
            @Override
            public void onResponse(Call<ProgressResponse> call, Response<ProgressResponse> response) {
                Log.d("ProgressResponse", response.body().toString());
                progressResponse.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ProgressResponse> call, Throwable t) {
                Log.e("ProgressReponse", t.toString());
            }
        });

        return progressResponse;
    }
}
