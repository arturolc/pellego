package com.gitlab.capstone.pellego.fragments.metaguiding.defaultPager;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gitlab.capstone.pellego.R;

/**
 * Eli Hebdon
 * Page contents fragment child that handles reader initializations
 *
 * based on AndroidReader here: https://github.com/koros/AndroidReader
 */
public class PageContentsFragment extends PageContentsFragmentBase {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_pager_container, container, false);
        TextView contentTextView = (TextView) rootView.findViewById(R.id.mText);
        contentTextView.setTextSize(DefaultPagerFragment.getTextSize());
        String contents = (DefaultPagerFragment.getContents(mPageNumber));
        contentTextView.setText(contents);
        contentTextView.setMovementMethod(new ScrollingMovementMethod());
        return rootView;
    }

}