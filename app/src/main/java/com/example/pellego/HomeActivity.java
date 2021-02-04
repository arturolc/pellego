package com.example.pellego;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

/**********************************************
 Eli Hebdon & Chris Bordoy
 Startup activity that the user sees when first opening the app after the splash
 screen is displayed.
 **********************************************/

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private NavController navController;
    private ActionBarDrawerToggle toggle;
    BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Pellego);
        setContentView(R.layout.activity_home);

        // Set toolbar as action bar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        // setup bottom nav and side nav menus
        drawer = findViewById(R.id.home_activity_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        navController.navigate(R.id.nav_library);

        //Attach side nav drawer to nav controller
        NavigationView drawerNavigationView = findViewById(R.id.side_nav_view);
        NavigationUI.setupWithNavController(drawerNavigationView, navController);
    }
}