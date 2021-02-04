package com.example.pellego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.auth.AuthProvider;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthWebUISignInOptions;
import com.amplifyframework.core.Amplify;

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
                                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
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