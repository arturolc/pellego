package com.example.pellego;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Notification;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Startup activity that the user sees when first opening the app after the splash
 * screen is displayed.
 */
public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // toolbar at top of screen that contains hamburger drawer menu button
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // layout that contains the side menu drawer
        drawer = findViewById(R.id.home_layout);

        // Open & close nav drawer button
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState(); // rotates hamburger icon
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.light_blue));
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_library, R.id.navigation_learn, R.id.navigation_progress, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
    }

    /**
     * Makes sure that the activity isn't exited if
     * the nav drawer is open. Instead, close it.
     */
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START); // closes drawer that is at START of screen aka the right side
        } else {
            super.onBackPressed();
        }

    }
}