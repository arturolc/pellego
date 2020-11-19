package com.example.pellego.ui.learn;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**********************************************
    Chris Bordoy

    The Learn Modules view model
 **********************************************/
public class LearnViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LearnViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the Learn fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}