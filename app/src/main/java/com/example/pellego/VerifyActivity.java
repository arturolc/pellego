
package com.example.pellego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;

/**********************************************
 Arturo Lara

 Email Verification with Code
 **********************************************/
public class VerifyActivity extends AppCompatActivity {

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        email = getIntent().getStringExtra("EMAIL");
    }

    public void confirm(View view) {
        EditText confirmationCode = findViewById(R.id.confirmCodeText);

        Amplify.Auth.confirmSignUp(
                email,
                confirmationCode.getText().toString(),
                result ->
                {
                    Intent i = new Intent(VerifyActivity.this,
                            LoginActivity.class);
                    startActivity(i);
                    finish();
                    Log.i("AUTHENTICATION", result.toString());
                },
                error -> Log.e("AUTHENTICATION", error.toString())
        );

    }

}