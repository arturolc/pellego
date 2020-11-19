package com.example.pellego.ui.progress;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**********************************************
    Chris Bordoy

    The Progress View Model
 **********************************************/
public class ProgressViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ProgressViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the Progress fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}