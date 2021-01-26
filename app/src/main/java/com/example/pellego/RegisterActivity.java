package com.example.pellego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

import java.util.ArrayList;
import java.util.List;

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

        List<AuthUserAttribute> list = new ArrayList<AuthUserAttribute>();
        list.add(new AuthUserAttribute(AuthUserAttributeKey.name(), firstName + " " + lastName));
        list.add(new AuthUserAttribute(AuthUserAttributeKey.email(), email));

        Amplify.Auth.signUp(
                email,
                password.getText().toString(),
                AuthSignUpOptions.builder().userAttributes(list).build(),
                result -> {
                    Log.i("AUTHENTICATION", "Result: " + result.toString());
                    Intent i  = new Intent(RegisterActivity.this, VerifyActivity.class);
                    i.putExtra("EMAIL", email);
                    startActivity(i);
                },
                error -> Log.e("AUTHENTICATION", "Sign up failed", error)
        );
    }

}