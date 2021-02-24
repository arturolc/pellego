package com.example.pellego;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.example.pellego.ui.auth.AuthActivity;

/**********************************************
 Eli Hebdon & Arturo Lara
 The main entry point for the app on startup.
 Displays a splash screen before navigating to the home activity.
 **********************************************/
public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT=2000;
    private Intent i;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("Amplify", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("Amplify", "Could not initialize Amplify", error);
        }

        Amplify.Auth.fetchAuthSession(
                result -> {
                    Log.d("AmplifyQuickstart", result.toString());
                    if (result.isSignedIn()) {
                        i = new Intent(MainActivity.this,
                                HomeActivity.class);

                    } else {
                        i = new Intent(MainActivity.this,
                                AuthActivity.class);
                    }
                },
                error -> {
                    Log.e("AmplifyQuickstart", error.toString());
                }
        );

        // delay transition to home activity with splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(i);
                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}