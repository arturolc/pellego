package com.gitlab.capstone.pellego.fragments.defaultPager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * Eli Hebdon
 * Adapter that handles pages being added as they're read (strings can be long)
 *
 * based on AndroidReader here: https://github.com/koros/AndroidReader
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

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