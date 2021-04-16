package com.gitlab.capstone.pellego.database;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.gitlab.capstone.pellego.database.daos.UserDao;
import com.gitlab.capstone.pellego.database.entities.ProgressCompleted;
import com.gitlab.capstone.pellego.database.entities.UserWordValues;
import com.gitlab.capstone.pellego.database.entities.Users;
import com.gitlab.capstone.pellego.network.APIService;
import com.gitlab.capstone.pellego.network.RetroInstance;
import com.gitlab.capstone.pellego.network.models.AuthToken;
import com.gitlab.capstone.pellego.network.models.CompletionResponse;
import com.gitlab.capstone.pellego.network.models.LastRecordedDate;
import com.gitlab.capstone.pellego.network.models.ProgressResponse;
import com.gitlab.capstone.pellego.network.models.ProgressValuesResponse;
import com.gitlab.capstone.pellego.network.models.TotalWordsReadResponse;

import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private final UserDao dao;
    private final APIService apiService;
    private static UsersRepo INSTANCE;
    private final MutableLiveData<List<CompletionResponse>> completionResponse = new MutableLiveData<>();
    private final MutableLiveData<List<ProgressValuesResponse>> progressValuesResponse = new MutableLiveData<>();
    private final MutableLiveData<TotalWordsReadResponse> totalWordsReadResponse = new MutableLiveData<>();
//    private final MutableLiveData<Users> user = new MutableLiveData<>();
    private Users user;
    private boolean isNetworkConnected;
    private Application application;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private  UsersRepo(Application application) {
        this.application = application;
        db = PellegoDatabase.getDatabase(application);
        dao = db.userDao();
        apiService = RetroInstance.getRetroClient().create(APIService.class);
        AuthUser u = Amplify.Auth.getCurrentUser();

        dao.getUser(u.getUsername()).observeForever(new Observer<Users>() {
            @Override
            public void onChanged(Users users) {
                user = users;
                Log.d("UsersRepo", users.toString());
                sync();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    synchronized public static UsersRepo getInstance(Application app) {
        if (INSTANCE == null) {
            INSTANCE = new UsersRepo(app);
        }
        return INSTANCE;
    }

    public LiveData<Users> getUser() {
        AuthUser u = Amplify.Auth.getCurrentUser();
        MutableLiveData<Users> res = new MutableLiveData<>();
        dao.getUser(u.getUsername()).observeForever(new Observer<Users>() {
            @Override
            public void onChanged(Users users) {
                res.postValue(users);
                Log.d("UsersRepo", users.toString());
            }
        });

        return res;
    }

    public void setSubmoduleCompletion(String mID, String smID) {
        Call<Void> call = apiService.setSubmoduleCompletion(new AuthToken(user.getEmail()), mID, smID);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                Log.e("RETROFIT", t.toString());
            }
        });

        AsyncTask.execute(() -> {
            dao.setProgressCompleted(new ProgressCompleted(0, user.getUID(),
                    Integer.parseInt(smID)));
        });
    }

    public void setUserWordValues(int wordsRead, int wpm) {
        Call<Void> call = apiService.setUserWordValues(
                new AuthToken(user.getEmail()),
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

        AsyncTask.execute(() -> {
            dao.setUserWordValues(new UserWordValues(0, user.getUID(),
             wordsRead, wpm, new Date(System.currentTimeMillis())));
        });

    }

    public LiveData<List<CompletionResponse>> getUserLearningModulesCompletionCount() {
        if (isNetworkConnected) {
            Call<List<CompletionResponse>> call =
                    apiService.getUserLearningModulesCompletionCount(new AuthToken(user.getEmail()));
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
        }
        else {
            List<CompletionResponse> list = new ArrayList<>();
            dao.getProgressCompleted(user.getUID()).observeForever(new Observer<List<ProgressCompleted>>() {
                @Override
                public void onChanged(List<ProgressCompleted> progressCompleteds) {
                    for (ProgressCompleted el:
                         progressCompleteds) {
                        list.add(new CompletionResponse(el.getMID(), el.getSMID()));
                    }
                }
            });
        }

        return completionResponse;
    }

    public LiveData<List<ProgressValuesResponse>> getProgressValues() {
        if(isNetworkConnected) {
            Call<List<ProgressValuesResponse>> call =
                    apiService.getProgressValues(new AuthToken(user.getEmail()));
            call.enqueue(new Callback<List<ProgressValuesResponse>>() {

                @Override
                public void onResponse(@NotNull Call<List<ProgressValuesResponse>> call, Response<List<ProgressValuesResponse>> response) {
                    Log.d("RETROFIT: ", response.body().toString());
                    Log.d("RETROFIT: ", response.body().size() + "");
                    progressValuesResponse.setValue(response.body());
                }

                @Override
                public void onFailure(@NotNull Call<List<ProgressValuesResponse>> call, @NotNull Throwable t) {
                    t.printStackTrace();
                    Log.e("RETROFIT: ", t.toString());
                }
            });
        }
        else {
            List<ProgressValuesResponse> res = new ArrayList<>();
            Date d = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(d);

            c.add(Calendar.DATE, -7);

            dao.getProgressLast7Days(user.getUID(),c.getTime()).observeForever(new Observer<List<UserWordValues>>() {
                @Override
                public void onChanged(List<UserWordValues> userWordValues) {
                    for (UserWordValues el:
                         userWordValues) {
                        res.add(new ProgressValuesResponse(el.getRecorded(), el.getWordsRead(),
                                el.getWpm()));
                    }
                }
            });

            c.add(Calendar.DATE, 7);
            c.add(Calendar.MONTH, -12);

            dao.getProgressLast12Months(user.getUID(), c.getTime()).observeForever(new Observer<List<UserWordValues>>() {
                @Override
                public void onChanged(List<UserWordValues> userWordValues) {
                    for (UserWordValues el:
                         userWordValues) {
                        res.add(new ProgressValuesResponse(el.getRecorded(), el.getWordsRead(),
                                el.getWpm()));
                    }
                    progressValuesResponse.postValue(res);
                    Log.d("getProgValues", res.toString());
                }
            });
        }

        return progressValuesResponse;
    }

    public LiveData<TotalWordsReadResponse> getTotalWordsRead() {
        if (isNetworkConnected) {
            Call<TotalWordsReadResponse> call =
                    apiService.getTotalWordsRead(new AuthToken(user.getEmail()));
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
        }
        else {
            dao.getTotalWordsRead(user.getUID()).observeForever(new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    totalWordsReadResponse.postValue(new TotalWordsReadResponse(integer));
                }
            });
        }

        return totalWordsReadResponse;
    }

    public void sync() {
        Call<LastRecordedDate> call =
                apiService.getLastRecordedDate(new AuthToken(user.getEmail())) ;
        call.enqueue(new Callback<LastRecordedDate>() {
            @Override
            public void onResponse(Call<LastRecordedDate> call, Response<LastRecordedDate> response) {

                dao.getLastRecordedDate(1).observeForever(new Observer<Date>() {
                    @Override
                    public void onChanged(Date date) {
                        Date localDate = date == null ? new Date(0) : date;
                        Date networkDate = response.body() == null ? new Date(0) : response.body().getDate();
                        Log.d("localDate", localDate.toString());
                        Log.d("networkDate", networkDate.toString());

                        if (localDate.compareTo(networkDate) > 0) {
                            // local date occurs after networkDate
                            dao.getUpdatedRecords(networkDate, user.getUID()).observeForever(new Observer<List<UserWordValues>>() {
                                @Override
                                public void onChanged(List<UserWordValues> userWordValues) {
                                    Log.d("userWordValues", userWordValues.toString());
                                    for (UserWordValues el :
                                            userWordValues) {
                                        setUserWordValues(el.getWordsRead(), el.getWpm());
                                    }
                                    Log.d("SYNC", "updated network records with local db");
                                }
                            });

                        }
                        else if (localDate.compareTo(networkDate) < 0) {
                            // local date occurs before networkDate
                            Call<ProgressResponse> call =
                                    apiService.getProgressResponse(new AuthToken(user.getEmail(),
                                            new Timestamp(localDate.getTime()).toString()));
                            call.enqueue(new Callback<ProgressResponse>() {
                                @Override
                                public void onResponse(Call<ProgressResponse> call, Response<ProgressResponse> response) {
                                    Log.d("ProgressResponse", response.body().toString());

                                    for (UserWordValues el:
                                         response.body().getValues()) {
                                        el.setUID(user.getUID());
                                        el.setUwID(0);
                                        AsyncTask.execute(() -> {
                                            dao.setUserWordValues(el);
                                        });
                                    }
                                    Log.d("SYNC", "updated local db with network records");
                                }

                                @Override
                                public void onFailure(Call<ProgressResponse> call, Throwable t) {
                                    Log.e("ProgressResponse", t.toString());
                                }
                            });
                        }
                        else {
                            // same date
                            Log.d("SYNC", "up to date");
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<LastRecordedDate> call, Throwable t) {
                Log.e("SYNC", t.toString());
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
                connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback() {
                   @Override
                   public void onAvailable(Network network) {
                       isNetworkConnected = true;
                       sync();
                   }
                   @Override
                   public void onLost(Network network) {
                       isNetworkConnected = false;
                   }
                });
            }
            isNetworkConnected = false;
        }catch (Exception e){
            isNetworkConnected = false;
        }
    }
}
