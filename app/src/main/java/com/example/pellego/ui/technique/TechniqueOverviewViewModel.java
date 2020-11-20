package com.example.pellego.ui.technique;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TechniqueOverviewViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TechniqueOverviewViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the Technique Overview fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}