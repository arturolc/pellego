package com.example.pellego;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

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
        setContentView(R.layout.activity_main);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(MainActivity.this,
                        HomeActivity.class);

                startActivity(i);

                finish();
                //the current activity will get finished.
            }
        }, SPLASH_SCREEN_TIME_OUT);


    }

}