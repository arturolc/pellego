package com.example.pellego;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.amplifyframework.core.Amplify;
import com.github.axet.androidlibrary.activities.AppCompatFullscreenThemeActivity;
import com.example.pellego.app.BookApplication;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class FullscreenActivity extends AppCompatFullscreenThemeActivity {
    public Toolbar toolbar;
    private AppBarConfiguration appBarConfiguration;
    private DrawerLayout drawer;
    private NavController navController;
    BottomNavigationView bottomNavigationView;

    public interface FullscreenListener {
        void onFullscreenChanged(boolean f);

        void onUserInteraction();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Pellego);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);


        // setup bottom nav and drawer nav menus
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        drawer = findViewById(R.id.home_layout);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_settings, R.id.nav_library, R.id.nav_learn, R.id.nav_progress,
                R.id.nav_profile, R.id.nav_terms_and_conditions, R.id.nav_privacy_policy,
                R.id.nav_sign_out)
                .setDrawerLayout(drawer)
                .build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        // Attach nav drawer to nav controller
        NavigationView drawerNavigationView = findViewById(R.id.side_nav_view);
        NavigationUI.setupWithNavController(drawerNavigationView, navController);

        // TODO: refactor this so it's just a click listener for the sign out button, otherwise navigation to other views doesn't work
        drawerNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.nav_sign_out) {
                    Amplify.Auth.signOut(
                            () -> {
                                Log.i("AUTHENTICATION", "Signed out succesfully");
                                finish();
                            },
                            error -> Log.e("AUTHENTICATION", error.toString())
                    );
                } else {
                    navController.navigate(id);
                    drawer.close();
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public int getAppTheme() {
        return BookApplication.getTheme(this, R.style.AppThemeLight_NoActionBar, R.style.AppThemeDark_NoActionBar);
    }

    @Override
    public int getAppThemePopup() {
        return BookApplication.getTheme(this, R.style.AppThemeLight_PopupOverlay, R.style.AppThemeDark_PopupOverlay);
    }

    @SuppressLint({"InlinedApi", "RestrictedApi"})
    @Override
    public void setFullscreen(boolean b) {
        super.setFullscreen(b);
        FragmentManager fm = getSupportFragmentManager();
        List<Fragment> ff = fm.getFragments();
        if (ff != null) {
            for (Fragment f : ff) {
                if (f instanceof com.example.pellego.FullscreenActivity.FullscreenListener)
                    ((com.example.pellego.FullscreenActivity.FullscreenListener) f).onFullscreenChanged(b);
            }
        }
    }

    @Override
    public void hideSystemUI() {
        super.hideSystemUI();
        setFitsSystemWindows(this, false);
    }

    @Override
    public void showSystemUI() {
        super.showSystemUI();
        setFitsSystemWindows(this, true);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        FragmentManager fm = getSupportFragmentManager();
        List<Fragment> ff = fm.getFragments();
        if (ff != null) {
            for (Fragment f : ff) {
                if (f instanceof FullscreenListener)
                    ((FullscreenListener) f).onUserInteraction();
            }
        }
    }
}
