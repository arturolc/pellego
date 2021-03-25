package com.gitlab.capstone.pellego.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.amplifyframework.core.Amplify;
import com.github.axet.androidlibrary.activities.AppCompatFullscreenThemeActivity;
import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.App;
import com.gitlab.capstone.pellego.fragments.auth.AuthActivity;
import com.gitlab.capstone.pellego.fragments.profile.ProfileModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import static com.gitlab.capstone.pellego.activities.MainActivity.bitmap;

/****************************************
 * Arturo Lara, Eli Hebdon
 ***************************************/
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
        requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(null);
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
        View headerView = drawerNavigationView.getHeaderView(0);

        ProfileModel p = ProfileModel.getInstance();
        p.getUserName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.i("SIDENAV", s);
                ((TextView)headerView.findViewById(R.id.headerUserName)).setText(s);
            }
        });


        NavigationUI.setupWithNavController(drawerNavigationView, navController);

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                invalidateOptionsMenu();
                ImageView im2 = (ImageView) findViewById(R.id.profile_image_drawer);
                im2.setImageBitmap(bitmap);
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                ImageView im2 = (ImageView) findViewById(R.id.profile_image_drawer);
                im2.setImageBitmap(bitmap);

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });

        // TODO: refactor this so it's just a click listener for the sign out button, otherwise navigation to other views doesn't work
        drawerNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.nav_sign_out) {
                    Amplify.Auth.signOut(
                            () -> {
                                finishAffinity();
                                Log.i("AUTHENTICATION", "Signed out successfully");
                                Intent i = new Intent(FullscreenActivity.this,
                                        AuthActivity.class);
                                startActivity(i);
                            },
                            error -> Log.e("AUTHENTICATION", error.toString())
                    );
                } else if (id == R.id.nav_settings) {
                    Intent i = new Intent(FullscreenActivity.this,
                            SettingsActivity.class);
                    startActivity(i);
                }
                else {
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

    // TODO: fix full screen mode
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void hideSystemUI() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.color, typedValue, true);
        int color = typedValue.data;
        findViewById(R.id.main_content).setBackgroundColor(color);
        super.hideSystemUI();
        setFitsSystemWindows(this, false);
    }

    @Override
    public void showSystemUI() {
        super.showSystemUI();
        findViewById(R.id.main_content).setBackgroundColor(Color.TRANSPARENT);
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
