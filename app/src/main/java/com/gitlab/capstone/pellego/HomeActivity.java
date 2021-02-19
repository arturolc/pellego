package com.gitlab.capstone.pellego;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.amplifyframework.core.Amplify;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

//import com.example.pellego.ui.rsvp.RsvpIntroFragment;

/**********************************************
 Eli Hebdon & Chris Bordoy

 Startup activity that the user sees when first opening the app after the splash
 screen is displayed.
 **********************************************/

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private DrawerLayout drawer;
    private NavController navController;
    BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Pellego);
        setContentView(R.layout.activity_home);
        // Set toolbar as action bar
        toolbar = findViewById(R.id.toolbar_v1);
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
}