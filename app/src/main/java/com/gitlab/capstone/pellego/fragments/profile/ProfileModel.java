package com.gitlab.capstone.pellego.fragments.profile;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.amplifyframework.core.Amplify;
import com.gitlab.capstone.pellego.database.UsersRepo;
import com.gitlab.capstone.pellego.network.models.TotalWordsReadResponse;

/**********************************************
 Eli Hebdon & Arturo Lara

 Profile Singleton Model
 **********************************************/

public class ProfileModel extends AndroidViewModel {
    private final MutableLiveData<String> userName;
    private final MutableLiveData<String> email;
    private static ProfileModel INSTANCE;
    private final UsersRepo usersRepo;
    private LiveData<TotalWordsReadResponse> totalWordsReadResponse;

    private ProfileModel(@NonNull Application application) {
        super(application);
        this.usersRepo = UsersRepo.getInstance(application);
        totalWordsReadResponse = new MutableLiveData<>();
        userName = new MutableLiveData<>();
        email = new MutableLiveData<>();

        Amplify.Auth.fetchUserAttributes(success ->  {
            Log.i("user", success.get(2).getValue());
            Log.i("user", success.toString());
            Log.i("user", success.get(3).getValue());

            for(int i = 0; i < success.size(); i++) {
                if (success.get(i).getKey().getKeyString().equals("name")) {
                    userName.postValue(success.get(i).getValue());
                }
                else if (success.get(i).getKey().getKeyString().equals("email")) {
                    email.postValue(success.get(i).getValue());
                }
            }
        }, fail -> Log.i("user", fail.toString()));
    }

    public static ProfileModel getInstance(@NonNull Application application) {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        return new ProfileModel(application);
    }

    public LiveData<TotalWordsReadResponse> getTotalWordsReadResponse() {
        totalWordsReadResponse = usersRepo.getTotalWordsRead();

        return totalWordsReadResponse;
    }

    public LiveData<String> getUserName() {
        return userName;
    }

    public LiveData<String> getEmail() {
        return email;
    }
}
