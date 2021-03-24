package com.gitlab.capstone.pellego.fragments.profile;
import android.util.Log;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.amplifyframework.core.Amplify;
import com.gitlab.capstone.pellego.R;

/**********************************************
 Eli Hebdon

 Profile View Model
 **********************************************/
public class ProfileViewModel extends ViewModel {
    private MutableLiveData<String> userName;
    private MutableLiveData<String> email;

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

    public void setUserName(String userName) {
        this.userName.setValue(userName);
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
