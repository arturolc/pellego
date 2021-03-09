package com.gitlab.capstone.pellego.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.amplifyframework.core.Amplify;
import com.github.axet.androidlibrary.activities.AppCompatFullscreenThemeActivity;
import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.App;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class FullscreenActivity extends AppCompatFullscreenThemeActivity {
    public Toolbar toolbar;
    private AppBarConfiguration appBarConfiguration;
    private DrawerLayout drawer;
    private NavController navController;
    private CoordinatorLayout bottomContent;
    BottomNavigationView bottomNavigationView;

    public interface FullscreenListener {
        void onFullscreenChanged(boolean f);

        void onUserInteraction();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        bottomContent = findViewById(R.id.container_bottom);
        toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_action_button_overflow));


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
        FrameLayout constraintLayout = this.findViewById(R.id.host_fragment_container);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) constraintLayout.getLayoutParams();
//        layoutParams.bottomToTop = R.id.container_bottom;
        constraintLayout.setLayoutParams(layoutParams);
        
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

    // TODO: update theme to pellego theme
    @Override
    public int getAppTheme() {
        return App.getTheme(this, R.style.Theme_Pellego, R.style.Theme_Pellego_Dark);
    }

    @Override
    public int getAppThemePopup() {
        return App.getTheme(this, R.style.AppThemeLight_PopupOverlay, R.style.AppThemeDark_PopupOverlay);
    }

    @SuppressLint({"InlinedApi", "RestrictedApi"})
    @Override
    public void setFullscreen(boolean b) {
        super.setFullscreen(b);
        FragmentManager fm = getSupportFragmentManager();
        List<Fragment> ff = fm.getFragments();
        if (ff != null) {
            for (Fragment f : ff) {
                if (f instanceof FullscreenActivity.FullscreenListener)
                    ((FullscreenActivity.FullscreenListener) f).onFullscreenChanged(b);
            }
        }
    }

    @Override
    public void hideSystemUI() {
        super.hideSystemUI();
//        bottomContent.setVisibility(View.INVISIBLE);
        setFitsSystemWindows(this, false);
    }

    @Override
    public void showSystemUI() {
        super.showSystemUI();
        bottomContent.setVisibility(View.VISIBLE);
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
