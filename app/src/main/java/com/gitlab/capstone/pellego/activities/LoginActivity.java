package com.gitlab.capstone.pellego.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.auth.AuthProvider;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.gitlab.capstone.pellego.R;

/**********************************************
 Arturo Lara
 User authentication using AWS Amplify and AWS Cognito
 **********************************************/
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Amplify.Auth.handleWebUISignInResponse(data);
        }
    }

    public void signIn(View view) {
        EditText email = findViewById(R.id.editTextTextEmailAddress);
        EditText password = findViewById(R.id.editTextTextPassword);

        Amplify.Auth.signIn(email.getText().toString(),
                            password.getText().toString(),
                            success -> {
                                Log.i("AUTHENTICATION", success.toString());
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            },
                            error -> Log.e("AUTHENTICATION", error.toString()));
    }

    public void moveToRegister(View view) {
        Intent i = new Intent(LoginActivity.this,
                RegisterActivity.class);
        startActivity(i);
        finish();
    }

    public void googleSignIn(View view) {
        Amplify.Auth.signInWithSocialWebUI(
                AuthProvider.google(),
                this,
                result -> Log.i("AUTHENTICATION", result.toString()),
                error -> Log.e("AUTHENTICATION", error.toString())
        );


    }

}