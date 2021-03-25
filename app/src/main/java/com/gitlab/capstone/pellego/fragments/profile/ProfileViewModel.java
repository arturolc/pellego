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

public class ProfileViewModel extends ViewModel {
    private final MutableLiveData<String> userName;
    private final MutableLiveData<String> email;

    public ProfileViewModel() {
        userName = new MutableLiveData<>();
        email = new MutableLiveData<>();
        Amplify.Auth.fetchUserAttributes(success ->  {
            Log.i("user", success.get(2).getValue());
            Log.i("user", success.get(3).getValue());

            userName.postValue(success.get(2).getValue());
            email.postValue(success.get(3).getValue());
        }, fail -> Log.i("user", fail.toString()));
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }

    public LiveData<String> getUserName() {
        return userName;
    }

    public LiveData<String> getEmail() {
        return email;
    }
}
