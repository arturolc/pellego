package com.example.pellego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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


}