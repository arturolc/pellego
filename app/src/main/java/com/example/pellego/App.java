package com.example.pellego;

import android.app.Application;
import android.content.res.Resources;

/**
 * Chris Bordoy
 *
 * Class specifically made to access resources from anywhere in the application
 */
public class App extends Application {
    private static Resources resources;

    @Override
    public void onCreate() {
        super.onCreate();

        resources = getResources();
    }

    public static Resources getAppResources() {
        return resources;
    }
}
