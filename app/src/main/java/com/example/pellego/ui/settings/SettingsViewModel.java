package com.example.pellego.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**********************************************
    Chris Bordoy

    The Settings View Model
 **********************************************/
public class SettingsViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public SettingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the Settings fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
