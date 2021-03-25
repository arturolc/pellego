package com.gitlab.capstone.pellego.app;

import android.content.Context;
import android.content.res.Resources;

import com.github.axet.androidlibrary.app.MainApplication;
import com.github.axet.androidlibrary.net.HttpClient;
import com.gitlab.capstone.pellego.R;

import org.geometerplus.zlibrary.ui.android.library.ZLAndroidApplication;

public class App extends MainApplication {
    public static String PREFERENCE_THEME = "theme";
    public static String READER_THEME= "readerTheme";
    public static String PREFERENCE_FONTFAMILY_FBREADER = "fontfamily_fb";
    public static String PREFERENCE_FONTSIZE_FBREADER = "fontsize_fb";
    public static String PREFERENCE_FONTSIZE_REFLOW = "fontsize_reflow";
    public static float PREFERENCE_FONTSIZE_REFLOW_DEFAULT = 0.8f;
    public static String PREFERENCE_LIBRARY_LAYOUT = "layout_";
    public static String PREFERENCE_SCREENLOCK = "screen_lock";
    public static String PREFERENCE_VOLUME_KEYS = "volume_keys";
    public static String PREFERENCE_LAST_PATH = "last_path";
    public static String PREFERENCE_ROTATE = "rotate";
    public static String PREFERENCE_VIEW_MODE = "view_mode";
    public static String PREFERENCE_STORAGE = "storage_path";
    public static String PREFERENCE_SORT = "sort";
    public static String PREFERENCE_LANGUAGE = "tts_pref";
    public static String PACKAGE_NAME;

    public ZLAndroidApplication zlib;

    private static Resources resources;

    public static Resources getAppResources() {
        return resources;
    }
    public static int getTheme(Context context, int light, int dark) {
        return MainApplication.getTheme(context, PREFERENCE_THEME, light, dark, context.getString(R.string.Theme_Dark));
    }

    public static int getStringIdentifier(String name) {
        return resources.getIdentifier(name, "string", PACKAGE_NAME);
    }

    public static int getArrayIdentifier(String name) {
        return resources.getIdentifier(name, "array", PACKAGE_NAME);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        resources = getResources();
        PACKAGE_NAME = getApplicationContext().getPackageName();

        zlib = new ZLAndroidApplication() {
            {
                attachBaseContext(App.this);
                onCreate();
            }
        };
        new HttpClient.SpongyLoader(this, false);
    }
}
