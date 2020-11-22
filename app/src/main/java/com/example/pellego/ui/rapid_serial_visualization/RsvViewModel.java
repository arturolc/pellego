package com.example.pellego.ui.rapid_serial_visualization;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RsvViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RsvViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Rapid Serial Visualization");
    }

    public LiveData<String> getText() {
        return mText;
    }
}