package com.gitlab.capstone.pellego.fragments.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.amplifyframework.core.Amplify;

/**********************************************
 Eli Hebdon & Arturo Lara

 Profile View Model
 **********************************************/

public class ProfileModel {
    private final MutableLiveData<String> userName;
    private final MutableLiveData<String> email;
    private static ProfileModel INSTANCE;

    private ProfileModel() {
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

    public static ProfileModel getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        return new ProfileModel();
    }

    public LiveData<String> getUserName() {
        return userName;
    }

    public LiveData<String> getEmail() {
        return email;
    }
}
