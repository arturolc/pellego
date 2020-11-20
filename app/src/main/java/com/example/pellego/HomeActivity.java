package com.example.pellego;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Notification;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.pellego.ui.learn.ModuleItem;
import com.example.pellego.ui.learn.ModuleListAdapter;
import com.example.pellego.ui.profile.ProfileFragment;
import com.example.pellego.ui.settings.SettingsFragment;
import com.example.pellego.ui.technique.TechniqueOverviewFragment;
import com.example.pellego.ui.termsAndConditions.TermsAndConditionsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

/**
 * Eli Hebdon & Chris Bordoy
 * Startup activity that the user sees when first opening the app after the splash
 * screen is displayed.
 */
public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // toolbar at top of screen that contains hamburger drawer menu button
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // layout that contains the side menu drawer
        drawer = findViewById(R.id.home_layout);
        NavigationView navigationView = findViewById(R.id.side_nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_profile, R.id.nav_terms_and_conditions, R.id.nav_settings, R.id.nav_library, R.id.nav_learn, R.id.nav_progress)
                .build();
        NavController navController1 = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController1, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController1);

        // Open & close nav drawer button
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState(); // rotates hamburger icon
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        // Setup footer menu navigation
        BottomNavigationView navView = findViewById(R.id.bottom_nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_library, R.id.nav_learn, R.id.nav_progress, R.id.nav_settings, R.id.nav_technique)
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

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}