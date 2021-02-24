package com.gitlab.capstone.pellego.activities;

import android.content.Intent;
import android.os.Build;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.gitlab.capstone.pellego.R;

import com.example.pellego.ui.auth.AuthActivity;


/**********************************************
 Eli Hebdon & Arturo Lara
 The main entry point for the app on startup.
 Displays a splash screen before navigating to the home activity.
 **********************************************/
public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT=4000;
    private Intent i;

    LottieAnimationView lottieAnimationView;
    TextView title;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.light_blue));
        setContentView(R.layout.activity_splash);
        lottieAnimationView = findViewById(R.id.book_anim);
        title = findViewById(R.id.title_splash);
        title.setX(1400);
        // animation
        title.animate().translationX(55).setDuration(1000).withEndAction(new Runnable() {
            @Override
            public void run() {
                lottieAnimationView.playAnimation();
            }
        });

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
                        i = new Intent(SplashActivity.this,
                                MainActivity.class);

                    } else {

                        i = new Intent(SplashActivity.this,
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