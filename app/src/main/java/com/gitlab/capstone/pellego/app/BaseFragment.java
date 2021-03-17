package com.gitlab.capstone.pellego.app;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
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
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setupHeader(View root) {
        TypedValue typedValue = new TypedValue();
        getActivity().getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        int color = typedValue.data;
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.light_blue_solid));
        toolbar.setTitle(null);
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.light_blue_solid));
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
