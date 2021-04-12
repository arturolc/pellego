package com.gitlab.capstone.pellego.database;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.amazonaws.mobileconnectors.cognitoauth.Auth;
import com.gitlab.capstone.pellego.database.daos.LearningModulesDao;
import com.gitlab.capstone.pellego.database.entities.Answers;
import com.gitlab.capstone.pellego.database.entities.LM_Intro;
import com.gitlab.capstone.pellego.database.entities.LM_Module;
import com.gitlab.capstone.pellego.database.entities.LM_Submodule;
import com.gitlab.capstone.pellego.database.entities.Questions;
import com.gitlab.capstone.pellego.network.APIService;
import com.gitlab.capstone.pellego.network.RetroInstance;
import com.gitlab.capstone.pellego.network.models.AllContentsResponse;
import com.gitlab.capstone.pellego.network.models.AuthToken;
import com.gitlab.capstone.pellego.network.models.LMDescResponse;
import com.gitlab.capstone.pellego.network.models.LMResponse;
import com.gitlab.capstone.pellego.network.models.QuizResponse;
import com.gitlab.capstone.pellego.network.models.SMResponse;

import org.jetbrains.annotations.NotNull;

import java.nio.channels.NetworkChannel;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*****************************************************
 * Arturo Lara & Chris Bordoy
 *
 * Repository for all endpoint calls
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
    private final Application application;
    private boolean isNetworkConnected;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private LearningModulesRepo(Application application) {
        this.application = application;
        db = PellegoDatabase.getDatabase(application);
        dao = db.learningModulesDao();
        apiService = RetroInstance.getRetroClient().create(APIService.class);
        registerNetworkCallback();

        // check to see if we have anything in local db
        AsyncTask.execute(()-> {
            int count = dao.getModulesCount();
            Log.d("ModulesCount", "" + count);
            if (count == 0) {
                cacheModules();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    synchronized public static LearningModulesRepo getInstance(Application app) {
        if (INSTANCE == null) {
            INSTANCE = new LearningModulesRepo(app);
        }
        return INSTANCE;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LiveData<List<LMResponse>> getModules() {
        Log.d("LMRepo", isNetworkConnected + "");
        if (isNetworkConnected) {
            Call<List<LMResponse>> call = apiService.getModules(new AuthToken("Chris.Bordoy@gmail.com"));
            call.enqueue(new Callback<List<LMResponse>>() {
                @Override
                public void onResponse(@NotNull Call<List<LMResponse>> call, @NotNull Response<List<LMResponse>> response) {
                    Log.i("RETROFITsucc", response.body().toString());
                    List<LMResponse> r = response.body();
                    lmResponse.setValue(r);
                }

                @Override
                public void onFailure(@NotNull Call<List<LMResponse>> call, Throwable t) {
                    Log.e("RETROFITerror", t.toString());
                }
            });
        }
        else {
            AsyncTask.execute(() -> {
                List<LM_Module> mn = dao.getModules();
                Log.d("LMDAO", mn.toString());
                List<LMResponse> res = new ArrayList<>();
                for(int i = 0; i < mn.size(); i++) {
                    LM_Module m = mn.get(i);
                    res.add(new LMResponse(m.getMID(), m.getName(), m.getSubheader(), m.getIcon(),
                            dao.getModuleProgress(1, mn.get(i).getMID()),
                            dao.getSubmodulesCount(mn.get(i).getMID())));
                }

                lmResponse.postValue(res);
            });
        }
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
                //callback.onResponse(call, response);
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

    public void cacheModules() {
        Call<AllContentsResponse> call = apiService.getAllContentsModules(new AuthToken("Chris.Bordoy@gmail.com", "2021-01-01"));
        call.enqueue(new Callback<AllContentsResponse>() {

            @Override
            public void onResponse(Call<AllContentsResponse> call, Response<AllContentsResponse> response) {
                AsyncTask.execute(()-> {
                    Log.d("cacheModules", "Caching...");
                    Log.d("cacheModules", response.body().toString());
                    for (LM_Module el : response.body().getModules()) {
                        dao.insertModules(el);
                    }

                    for (LM_Intro el : response.body().getIntros()) {
                        dao.insertIntros(el);
                    }

                    for (LM_Submodule el : response.body().getSubmodule()) {
                        dao.insertSubmodules(el);
                    }

                    for (Questions el : response.body().getQuestions()) {
                        dao.insertQuestions(el);
                    }

                    for (Answers el : response.body().getAnswers()) {
                        dao.insertAnswers(el);
                    }
                });
            }

            @Override
            public void onFailure(Call<AllContentsResponse> call, Throwable t) {
                Log.d("cacheModules", t.toString());
            }
        });
    }


    // Network Check
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void registerNetworkCallback()
    {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) application.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkRequest.Builder builder = new NetworkRequest.Builder();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback(){
                                                                       @Override
                                                                       public void onAvailable(Network network) {
                                                                           isNetworkConnected = true; // Global Static Variable
                                                                       }
                                                                       @Override
                                                                       public void onLost(Network network) {
                                                                           isNetworkConnected = false; // Global Static Variable
                                                                       }
                                                                   }

                );
            }
            isNetworkConnected = false;
        }catch (Exception e){
            isNetworkConnected = false;
        }
    }
}
