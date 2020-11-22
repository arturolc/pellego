package com.example.pellego;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.pellego.ui.learn.LearnFragment;
import com.example.pellego.ui.library.LibraryFragment;
import com.example.pellego.ui.profile.ProfileFragment;
import com.example.pellego.ui.progress.ProgressFragment;
import com.example.pellego.ui.settings.SettingsFragment;
import com.example.pellego.ui.termsAndConditions.TermsAndConditionsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

/**
 * Eli Hebdon & Chris Bordoy
 * Startup activity that the user sees when first opening the app after the splash
 * screen is displayed.
 */
public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private NavController navController;
    ActionBarDrawerToggle toggle;

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
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation view item clicks here.
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                toggle.setDrawerIndicatorEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
                switch (item.getItemId()) {
                    case R.id.nav_profile:
                        // Navigation.findNavController(view).navigate(R.id.nav_technique);
                        fragmentTransaction.replace(R.id.host_fragment_container, new ProfileFragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        break;
                    case R.id.nav_terms_and_conditions:
                        // Navigation.findNavController(view).navigate(R.id.nav_technique);
                        fragmentTransaction.replace(R.id.host_fragment_container, new TermsAndConditionsFragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        break;
                    case R.id.nav_settings:
                        fragmentTransaction.replace(R.id.host_fragment_container, new SettingsFragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        break;
                }
                //close navigation drawer
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        // Open & close nav drawer button
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState(); // rotates hamburger icon
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().popBackStack();
                toggle.setDrawerIndicatorEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
            }
        });

        // Setup footer menu navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_library, R.id.nav_learn, R.id.nav_progress, R.id.nav_settings)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        toggle.setDrawerIndicatorEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportFragmentManager().beginTransaction().add(R.id.host_fragment_container, new LibraryFragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

    }

    /**
     * Makes sure that the activity isn't exited if
     * the nav drawer is open. Instead, close it.
     */
    @Override
    public void onBackPressed() {
        System.out.println("here");
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START); // closes drawer that is at START of screen aka the right side
        }
        else if (false) {
//            Navigation.findNavController(view).navigate(R.id.nav_technique);

        }
        else {
            toggleActionBarIcon();
            super.onBackPressed();
        }

    }

    public void toggleActionBarIcon() {
        toggle.setDrawerIndicatorEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        toggle.setDrawerIndicatorEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        switch (item.getItemId()) {
            case R.id.nav_library:
                // Navigation.findNavController(view).navigate(R.id.nav_technique);
                fragmentTransaction.replace(R.id.host_fragment_container, new LibraryFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.nav_learn:
                // Navigation.findNavController(view).navigate(R.id.nav_technique);
                fragmentTransaction.replace(R.id.host_fragment_container, new LearnFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.nav_settings:
                fragmentTransaction.replace(R.id.host_fragment_container, new SettingsFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.nav_progress:
                fragmentTransaction.replace(R.id.host_fragment_container, new ProgressFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
        }
        return true;
    }



}