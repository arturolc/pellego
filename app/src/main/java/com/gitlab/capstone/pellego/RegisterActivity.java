package com.gitlab.capstone.pellego;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

import java.util.ArrayList;
import java.util.List;

/**********************************************
 Arturo Lara
 Registers a user. Sends verification code after submitting form
 **********************************************/
public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }


    public void register(View view) {
        String firstName = ((EditText)findViewById(R.id.firstNameText)).getText().toString();
        String lastName = ((EditText)findViewById(R.id.lastNameText)).getText().toString();
        String email = ((EditText)findViewById(R.id.emailText)).getText().toString();
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
                    i.putExtra("fName", firstName);
                    i.putExtra("lName", lastName);
                    i.putExtra("email", email);
                    startActivity(i);
                },
                error -> Log.e("AUTHENTICATION", "Sign up failed" + error.toString())
        );
    }

}