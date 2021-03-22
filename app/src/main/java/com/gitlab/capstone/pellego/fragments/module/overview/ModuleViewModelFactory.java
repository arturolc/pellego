package com.gitlab.capstone.pellego.fragments.module.overview;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ModuleViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private String mParam;

    public ModuleViewModelFactory(Application application, String param) {
        mApplication = application;
        mParam = param;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new ModuleViewModel(mApplication, mParam);
    }
}