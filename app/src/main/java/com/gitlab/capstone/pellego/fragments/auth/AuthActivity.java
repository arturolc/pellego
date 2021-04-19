package com.gitlab.capstone.pellego.fragments.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.gitlab.capstone.pellego.R;

/**********************************************
 Arturo Lara
 User authentication using AWS Amplify and AWS Cognito
 **********************************************/
public class AuthActivity extends AppCompatActivity {
    NavController nav;

    private AuthViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        model = new ViewModelProvider(this).get(AuthViewModel.class);

        Button btn = findViewById(R.id.signupBtn);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Amplify.Auth.handleWebUISignInResponse(data);
        }
    }


}