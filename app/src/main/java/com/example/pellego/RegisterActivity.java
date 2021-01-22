package com.example.pellego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

public class RegisterActivity extends AppCompatActivity {

    private String firstName;
    private String lastName;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }


    public void register(View view) {
        firstName = ((EditText)findViewById(R.id.firstNameText)).getText().toString();
        lastName = ((EditText)findViewById(R.id.lastNameText)).getText().toString();
        email = ((EditText)findViewById(R.id.emailText)).getText().toString();
        EditText password = findViewById(R.id.passwordText);

        Amplify.Auth.signUp(
                email,
                password.getText().toString(),
                AuthSignUpOptions.builder().userAttribute(AuthUserAttributeKey.email(), email).build(),
                result -> Log.i("AUTHENTICATION", "Result: " + result.toString()),
                error -> Log.e("AUTHENTICATION", "Sign up failed", error)
        );

        findViewById(R.id.layout1).setVisibility(8);
        findViewById(R.id.layout2).setVisibility(0);
    }

    public void confirm(View view) {
        EditText confirmationCode = findViewById(R.id.confirmCodeText);

        Amplify.Auth.confirmSignUp(
                email,
                confirmationCode.getText().toString(),
                result ->
                {
                    Intent i = new Intent(RegisterActivity.this,
                            LoginActivity.class);
                    startActivity(i);
                    finish();
                    Log.i("AUTHENTICATION", result.toString());
                },
                error -> Log.e("AUTHENTICATION", error.toString())
        );

    }




}