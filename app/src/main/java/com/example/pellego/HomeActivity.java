package com.example.pellego;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

//import com.example.pellego.ui.rsvp.RsvpIntroFragment;
import com.amplifyframework.auth.options.AuthSignOutOptions;
import com.amplifyframework.core.Amplify;

import com.example.pellego.ui.defaultPager.PagerAdapter;
import com.example.pellego.ui.defaultPager.PagerTask;
import com.example.pellego.ui.metaguiding.MetaguidingModuleFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Map;

/**********************************************
 Eli Hebdon & Chris Bordoy
 Startup activity that the user sees when first opening the app after the splash
 screen is displayed.
 **********************************************/

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
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


        // setup bottom nav and drawer nav menus
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        drawer = findViewById(R.id.home_layout);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_settings, R.id.nav_library, R.id.nav_learn, R.id.nav_progress, R.id.nav_profile, R.id.nav_terms_and_conditions, R.id.nav_sign_out)
                .setDrawerLayout(drawer)
                .build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        // Attach nav drawer to nav controller
        NavigationView drawerNavigationView = findViewById(R.id.side_nav_view);
        NavigationUI.setupWithNavController(drawerNavigationView, navController);

        // TODO: refactor this so it's just a click listener for the sign out button, otherwise navigation to other views doesn't work
//        drawerNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                int id = menuItem.getItemId();
//
//                if (id == R.id.nav_sign_out) {
//                    Amplify.Auth.signOut(
//                            () -> {
//                                Log.i("AUTHENTICATION", "Signed out succesfully");
//                                finish();
//                            },
//                            error -> Log.e("AUTHENTICATION", error.toString())
//                    );
//                }
//                return true;
//            }
//        });


    }

}