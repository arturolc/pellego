package com.example.pellego;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;

/**********************************************
 Eli Hebdon
 The main entry point for the app on startup.
 Displays a splash screen before navigating to the home activity.
 **********************************************/
public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // delay transition to home activity with splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // TODO: uncomment when merging back into development
//                try {
//                    Amplify.addPlugin(new AWSCognitoAuthPlugin());
//                    Amplify.configure(getApplicationContext());
//                    Log.i("Amplify", "Initialized Amplify");
//                } catch (AmplifyException error) {
//                    Log.e("Amplify", "Could not initialize Amplify", error);
//                }
//
//                Amplify.Auth.fetchAuthSession(
//                        result -> {
//                            Log.i("AmplifyQuickstart", result.toString());
//                            if (result.isSignedIn()) {
//                                Intent i = new Intent(MainActivity.this,
//                                        HomeActivity.class);
//                                startActivity(i);
//
//                            } else {
//                                Intent i = new Intent(MainActivity.this,
//                                        LoginActivity.class);
//                                startActivity(i);
//                            }
//                        },
//                        error -> {
//                            Log.e("AmplifyQuickstart", error.toString());
//                        }
//                );

                Intent i=new Intent(MainActivity.this,
                        HomeActivity.class);

                startActivity(i);



                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);


    }

}