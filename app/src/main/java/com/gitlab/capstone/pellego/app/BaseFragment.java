package com.gitlab.capstone.pellego.app;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.github.axet.androidlibrary.widgets.InvalidateOptionsMenuCompat;
import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.fragments.library.LibraryFragment;

public class BaseFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LibraryFragment.FragmentHolder holder = new LibraryFragment.FragmentHolder(getContext());
        setHasOptionsMenu(true);

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_menu, null);
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, Color.WHITE);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(drawable);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        Runnable invalidateOptionsMenu = InvalidateOptionsMenuCompat.onCreateOptionsMenu(this, menu, inflater);
        MenuItem homeMenu = menu.findItem(R.id.action_home);
        MenuItem tocMenu = menu.findItem(R.id.action_toc);
        MenuItem bookmarksMenu = menu.findItem(R.id.action_bm);
        MenuItem settings = menu.findItem(R.id.action_settings);
        MenuItem fontsize = menu.findItem(R.id.action_fontsize);
        MenuItem rtl = menu.findItem(R.id.action_rtl);
        MenuItem mode = menu.findItem(R.id.action_mode);
        MenuItem sort = menu.findItem(R.id.action_sort);
        MenuItem toggleGrid = menu.findItem(R.id.action_grid);
        MenuItem tts = menu.findItem(R.id.action_tts);

        homeMenu.setVisible(false);
        tocMenu.setVisible(false);
        fontsize.setVisible(false);
        sort.setVisible(false);
        rtl.setVisible(false);
        settings.setVisible(false);
        bookmarksMenu.setVisible(false);
        mode.setVisible(false);
        tts.setVisible(false);
        toggleGrid.setVisible(false);
    }

}
