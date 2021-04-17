package com.gitlab.capstone.pellego.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceFragmentCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.App;
import com.gitlab.capstone.pellego.app.Storage;
import com.github.axet.androidlibrary.activities.AppCompatSettingsThemeActivity;
import com.github.axet.androidlibrary.preferences.RotatePreferenceCompat;
import com.github.axet.androidlibrary.preferences.StoragePathPreferenceCompat;

/****************************************
 * Eli Hebdon
 *
 * Activity for Settings
 ***************************************/
public class SettingsActivity extends AppCompatSettingsThemeActivity {
    public static final int RESULT_STORAGE = 1;

    Storage storage;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Log.d("back pressed", "back from activity");
        Intent i = new Intent(SettingsActivity.this,
                MainActivity.class);
        startActivity(i);
    }

    @Override
    public int getAppTheme() {
        return App.getTheme(this, R.style.Theme_Pellego, R.style.Theme_Pellego_Dark);
    }

    @Override
    public String getAppThemeKey() {
        return App.PREFERENCE_THEME;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storage = new Storage(this);
        setContentView(R.layout.fragment_settings);
        RotatePreferenceCompat.onCreate(this, App.PREFERENCE_ROTATE);

        if (savedInstanceState == null && getIntent().getParcelableExtra(SAVE_INSTANCE_STATE) == null)
            showSettingsFragment(new GeneralPreferenceFragment());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        super.onSharedPreferenceChanged(sharedPreferences, key);
        if (key.equals(App.PREFERENCE_STORAGE))
            storage.migrateLocalStorageDialog(this);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            setHasOptionsMenu(true);

            bindPreferenceSummaryToValue(findPreference(App.PREFERENCE_SCREENLOCK));
            bindPreferenceSummaryToValue(findPreference(App.PREFERENCE_THEME));
            bindPreferenceSummaryToValue(findPreference(App.READER_THEME));
            bindPreferenceSummaryToValue(findPreference(App.PREFERENCE_VIEW_MODE));

            StoragePathPreferenceCompat s = (StoragePathPreferenceCompat) findPreference(App.PREFERENCE_STORAGE);
            s.setStorage(new Storage(getContext()));
            s.setPermissionsDialog(this, Storage.PERMISSIONS_RW, RESULT_STORAGE);
            if (Build.VERSION.SDK_INT >= 21)
                s.setStorageAccessFramework(this, RESULT_STORAGE);
        }

        @Override
        public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            final RecyclerView rv = getListView(); // This holds the PreferenceScreen's items
            rv.setPadding(0, 200, 0, 0); // (left, top, right, bottom)
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            getActivity().setTheme(R.style.PreferenceStyle);
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            StoragePathPreferenceCompat s = (StoragePathPreferenceCompat) findPreference(App.PREFERENCE_STORAGE);
            switch (requestCode) {
                case RESULT_STORAGE:
                    s.onRequestPermissionsResult(permissions, grantResults);
                    break;
            }
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            StoragePathPreferenceCompat s = (StoragePathPreferenceCompat) findPreference(App.PREFERENCE_STORAGE);
            switch (requestCode) {
                case RESULT_STORAGE:
                    s.onActivityResult(resultCode, data);
                    break;
            }
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                Activity a = getActivity();
                a.finish();
                startActivity(new Intent(getActivity(), MainActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }
}
