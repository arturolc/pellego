package com.gitlab.capstone.pellego.fragments.metaguiding.defaultPager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

/**
 * Eli Hebdon
 * Base class for page contents that keeps track of page numbers
 *
 * based on AndroidReader here: https://github.com/koros/AndroidReader
 */
public class PageContentsFragmentBase extends Fragment {

    public static final String ARG_PAGE = "page";
    protected int mPageNumber;

    public PageContentsFragmentBase() {
    }

    public static PageContentsFragmentBase create(int pageNumber) {
        PageContentsFragmentBase fragment = null;

        fragment = new PageContentsFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }

}