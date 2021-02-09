package com.example.pellego.ui.settings;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsSeekBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceFragmentCompat;

import com.example.pellego.R;

/**********************************************
    Chris Bordoy

    The Settings Component
 **********************************************/
public class SettingsFragment extends PreferenceFragmentCompat {

    private SettingsViewModel settingsViewModel;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        getActivity().setTheme(R.style.PreferenceStyle);
        setPreferencesFromResource(R.xml.preferences, rootKey);

        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);


    }

}
