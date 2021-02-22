package com.example.pellego.ui.auth;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pellego.UserModel;

/*****************************************
 * Arturo Lara
 * AuthViewModel will handle authentication using AWS Amplify,
 * user data collection for Pellego Remote and Local Databases.
 *****************************************/
public class AuthViewModel extends ViewModel {

//    private final MutableLiveData<UserModel> data;
    UserModel user;
    public AuthViewModel() {

//        this.data = new MutableLiveData<>(user);
        this.user = new UserModel();
    }

//    public void updateUser() {
//        data.setValue
//    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public UserModel getUser() {
        return user;
    }

}
