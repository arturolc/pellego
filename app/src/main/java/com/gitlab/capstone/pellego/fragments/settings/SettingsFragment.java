package com.gitlab.capstone.pellego.fragments.settings;

import android.os.Bundle;

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

}
