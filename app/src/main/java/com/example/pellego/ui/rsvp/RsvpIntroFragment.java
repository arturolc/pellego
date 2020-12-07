package com.example.pellego.ui.rsvp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.pellego.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class RsvpIntroFragment extends Fragment {
    private RsvpViewModel rsvpViewModel;
    RelativeLayout parent_view;
    ViewPager2 viewPager2;
    TabLayout tabLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rsvpViewModel =
                new ViewModelProvider(this).get(RsvpViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rsvp_intro, container, false);

        viewPager2 = root.findViewById(R.id.view_pager);
        tabLayout = root.findViewById(R.id.tabDots);
        parent_view = root.findViewById(R.id.parent_view);

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
        return root;
    }
}