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
        mText.setValue("Learning Modules");
    }

    public LiveData<String> getText() {
        return mText;
    }
}