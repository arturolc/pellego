package com.example.pellego;

import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.pellego.ui.rsvp.RsvpIntroPagerAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**********************************************
 Eli Hebdon and Chris Bordoy
 Rapid Serial Visualization activity that begins once
 the user selects a (beginner, intermediate, advanced) submodule
 in the RSV fragment.
 **********************************************/
public class RsvpActivity extends AppCompatActivity {

    RelativeLayout parent_view;
    ViewPager2 viewPager2;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_rsvp_intro);
    }

    private void init() {
        viewPager2 = (ViewPager2)findViewById(R.id.view_pager);
        tabLayout = (TabLayout)findViewById(R.id.tabDots);
        parent_view = (RelativeLayout)findViewById(R.id.parent_view);

        //set data
        viewPager2.setAdapter(new RsvpIntroPagerAdapter());
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
        }).attach();

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Snackbar.make(parent_view,"You have selected page: "+(position+1),Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
