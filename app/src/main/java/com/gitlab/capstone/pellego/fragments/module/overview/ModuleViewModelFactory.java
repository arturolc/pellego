package com.gitlab.capstone.pellego.fragments.module.overview;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

/**********************************************
 Chris Bordoy

 Class that creates a viewmodel with specific
 parameters
 **********************************************/

public class ModuleViewModelFactory implements ViewModelProvider.Factory {
    private final Application mApplication;
    private final String mParam;

    public ModuleViewModelFactory(Application application, String param) {
        mApplication = application;
        mParam = param;
    }

    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
        return (T) new ModuleViewModel(mApplication, mParam);
    }
}