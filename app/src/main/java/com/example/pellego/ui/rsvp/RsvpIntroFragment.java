package com.example.pellego.ui.rsvp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.pellego.R;

import com.example.pellego.ui.learn.ModuleViewModel;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;


/***************************************************
 *  Chris Bordoy
 *
 *  The RSVP Introduction Fragment
 **************************************************/
public class RsvpIntroFragment extends Fragment {
    private ModuleViewModel moduleViewModel;
    RelativeLayout parent_view;
    ViewPager2 viewPager2;
    DotsIndicator dotsIndicator;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        moduleViewModel =
                new ViewModelProvider(this).get(ModuleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rsvp_intro, container, false);

        dotsIndicator = root.findViewById(R.id.dots_indicator);
        viewPager2 = root.findViewById(R.id.view_pager);
        parent_view = root.findViewById(R.id.parent_view);

        //set data
        viewPager2.setAdapter(new RsvpIntroPagerAdapter());
        dotsIndicator.setViewPager2(viewPager2);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        //Can potentially use this code for setting text more dynamically if
        //we decide to use a TabLayout within this fragment.
//        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
//        }).attach();

//        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Snackbar.make(parent_view,"You have selected page: "+(position+1),Snackbar.LENGTH_SHORT).show();
//            }
//        });
        return root;
    }
}