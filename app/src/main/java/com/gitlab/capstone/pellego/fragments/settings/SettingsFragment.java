package com.gitlab.capstone.pellego.fragments.settings;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceFragmentCompat;

import com.gitlab.capstone.pellego.R;


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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.setBackgroundResource(R.color.light_blue);
        super.onViewCreated(view, savedInstanceState);
    }
}
