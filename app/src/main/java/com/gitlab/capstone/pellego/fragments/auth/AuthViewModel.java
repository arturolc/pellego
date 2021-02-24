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
    private String name;
    private String email;
    private String password;
    private String confirmationCode;


    public AuthViewModel() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }
}
