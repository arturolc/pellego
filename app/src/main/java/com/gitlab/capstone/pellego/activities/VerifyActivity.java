
package com.gitlab.capstone.pellego.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;
import com.gitlab.capstone.pellego.app.DatabaseHelper;
import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.UserModel;


/**********************************************
 Arturo Lara
 Email Verification with Code
 **********************************************/
public class VerifyActivity extends AppCompatActivity {

    private String email;
    private String firstName;
    private String lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        email = getIntent().getStringExtra("email");
        firstName = getIntent().getStringExtra("fName");
        lastName = getIntent().getStringExtra("lName");
    }

    public void confirm(View view) {
        EditText confirmationCode = findViewById(R.id.confirmCodeText);
        DatabaseHelper dh = new DatabaseHelper(VerifyActivity.this, null, null, 1);
        Amplify.Auth.confirmSignUp(
                email,
                confirmationCode.getText().toString(),
                result ->
                {
                    dh.addUser(new UserModel(-1, firstName, lastName, email));
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