package com.example.pellego.ui.defaultPager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class PagerAdapter extends FragmentPagerAdapter {

    int pages;

    public PagerAdapter(FragmentManager fragmentManager, int totalPages) {
        super(fragmentManager);
        this.pages = totalPages;
    }

    @Override
    public Fragment getItem(int position) {
        return PageContentsFragmentBase.create(position);
    }

    @Override
    public int getCount() {
        return pages;
    }

    public void incrementPageCount(){
        pages+=1;
        notifyDataSetChanged();
    }
}